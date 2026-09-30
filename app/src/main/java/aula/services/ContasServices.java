package aula.services;

import aula.Models.Cliente;
import aula.Models.Conta;
import aula.Models.Historico;
import aula.exceptions.ClienteNaoEncontradoException;
import aula.exceptions.ContaNaoEncontradaException;
import aula.exceptions.RegraNegocioException;
import aula.repository.ClienteRepository;
import aula.repository.ContaRepository;
import aula.repository.HistoricoRepository;
import aula.utils.Utils;

import java.util.List;
import java.util.Optional;

/**
 * ============================================================================
 * CAMADA DE SERVIÇOS (SERVICE LAYER)
 * ============================================================================
 * A classe ContasServices é responsável por centralizar toda a REGRA DE NEGÓCIO
 * do banco. Ela orquestra os repositórios (Repositories), valida dados,
 * executa as transações e lança exceções quando algo invalida a operação.
 *
 * Aqui aplicamos fortemente os conceitos aprendidos:
 * 1. GENERICS: Usamos repositórios genéricos fortemente tipados.
 * 2. OPTIONAL: Manipulamos buscas seguras sem risco de NullPointerException.
 * 3. LIST: Retornamos e percorremos coleções de objetos.
 * 4. EXCEPTIONS: Lançamos exceções personalizadas para erros de negócio.
 */
public class ContasServices {

    // Repositórios especializados
    private final ContaRepository contaRepository = new ContaRepository();
    private final ClienteRepository clienteRepository = new ClienteRepository();
    private final HistoricoRepository historicoRepository = new HistoricoRepository();

    /**
     * Criar uma nova conta para um cliente.
     *
     * DEMONSTRAÇÃO DE OPTIONAL E EXCEÇÃO:
     * - clienteRepository.buscarPorCpf(cpf) retorna Optional<Cliente>.
     * - Se o cliente já existir no banco, reaproveitamos. Se não, criamos um novo.
     */
    public Conta criarConta(String nome, String email, String cpf) {
        if (nome == null || nome.isBlank() || cpf == null || cpf.isBlank()) {
            throw new RegraNegocioException("Nome e CPF são obrigatórios para criar uma conta.");
        }

        String cpfLimpo = cpf.trim();

        // 1. Procura o cliente usando Optional
        // Se o cliente existir na caixa do Optional, usamos. Se estiver vazio, cadastramos.
        Cliente cliente = clienteRepository.buscarPorCpf(cpfLimpo)
                .orElseGet(() -> {
                    Cliente novoCliente = new Cliente(nome.trim(), email != null ? email.trim() : "", cpfLimpo);
                    clienteRepository.adicionar(novoCliente);
                    return novoCliente;
                });

        // 2. Verifica se este cliente já possui uma conta no banco
        Optional<Conta> contaExistente = contaRepository.buscarPorCpf(cpfLimpo);
        if (contaExistente.isPresent()) {
            // Lança exceção de regra de negócio
            throw new RegraNegocioException("O cliente com CPF " + cpfLimpo + " já possui uma conta (ID: "
                    + contaExistente.get().getId() + ").");
        }

        // 3. Gera próximo ID para a nova conta
        int proximoId = contaRepository.listar().stream()
                .mapToInt(Conta::getId)
                .max()
                .orElse(0) + 1;

        // 4. Cria e salva a nova conta
        Conta novaConta = new Conta(proximoId, 0.0, cliente);
        contaRepository.adicionar(novaConta);

        return novaConta;
    }

    /**
     * Realiza o depósito em uma conta.
     *
     * USANDO OPTIONAL COM .orElseThrow():
     * Em vez de fazer if (conta == null), o .orElseThrow() extrai a conta do Optional
     * ou lança a exceção ContaNaoEncontradaException se o Optional estiver vazio!
     */
    public void depositar(String cpf, int idConta, double valor) {
        // Busca a conta pelo ID. Se não encontrar, lança ContaNaoEncontradaException
        Conta conta = contaRepository.buscarPorId(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException(idConta));

        // Valida se o CPF pertence ao dono da conta
        validarTitularidade(conta, cpf);

        // Deposita na conta (pode lançar ValorInvalidoException se valor <= 0)
        conta.depositar(valor);

        // Registra a transação no Histórico
        registrarHistorico("DEPÓSITO", valor, String.valueOf(conta.getId()), null);
    }

    /**
     * Realiza o saque de uma conta.
     */
    public void sacar(String cpf, int idConta, double valor) {
        // Extrai a conta ou lança exceção se não existir
        Conta conta = contaRepository.buscarPorId(idConta)
                .orElseThrow(() -> new ContaNaoEncontradaException(idConta));

        // Valida titularidade
        validarTitularidade(conta, cpf);

        // Efetua o saque (pode lançar SaldoInsuficienteException ou ValorInvalidoException)
        conta.sacar(valor);

        // Registra a transação no Histórico
        registrarHistorico("SAQUE", valor, String.valueOf(conta.getId()), null);
    }

    /**
     * Realiza a transferência entre duas contas.
     */
    public void transferir(String cpfOrigem, int idContaOrigem, int idContaDestino, double valor) {
        if (idContaOrigem == idContaDestino) {
            throw new RegraNegocioException("A conta de origem e destino não podem ser a mesma.");
        }

        // Busca conta de origem (ou lança exceção se não achar)
        Conta contaOrigem = contaRepository.buscarPorId(idContaOrigem)
                .orElseThrow(() -> new ContaNaoEncontradaException(idContaOrigem));

        // Busca conta de destino (ou lança exceção se não achar)
        Conta contaDestino = contaRepository.buscarPorId(idContaDestino)
                .orElseThrow(() -> new ContaNaoEncontradaException(idContaDestino));

        // Valida se a conta de origem pertence ao CPF informado
        validarTitularidade(contaOrigem, cpfOrigem);

        // Realiza transferência (valida saldo e lança exceções se necessário)
        contaOrigem.transferir(contaDestino, valor);

        // Registra transação no histórico
        registrarHistorico("TRANSFERÊNCIA", valor, String.valueOf(contaOrigem.getId()), String.valueOf(contaDestino.getId()));
    }

    /**
     * Retorna a Lista (List<Conta>) de todas as contas.
     */
    public List<Conta> listarContas() {
        return contaRepository.listar();
    }

    /**
     * Retorna a Lista (List<Historico>) de todas as transações efetuadas.
     */
    public List<Historico> listarHistorico() {
        return historicoRepository.listar();
    }

    /**
     * Método auxiliar privado para validar se o CPF do cliente confere com a conta.
     */
    private void validarTitularidade(Conta conta, String cpf) {
        if (cpf == null || !conta.getCliente().getCpf().equalsIgnoreCase(cpf.trim())) {
            throw new RegraNegocioException("O CPF informado não corresponde ao titular da conta ID " + conta.getId() + ".");
        }
    }

    /**
     * Registra uma transação no histórico.
     */
    private void registrarHistorico(String tipo, double valor, String contaId, String contaDestinoId) {
        String idHistorico = Utils.generateId();
        Historico historico = new Historico(idHistorico, tipo, valor, contaId, contaDestinoId);
        historicoRepository.adicionar(historico);
    }
}
