package aula;

import aula.Models.Conta;
import aula.Models.Historico;
import aula.exceptions.BancoException;
import aula.services.ContasServices;
import aula.utils.Utils;

import java.util.List;
import java.util.Scanner;

/**
 * ============================================================================
 * CLASSE PRINCIPAL (INTERFACE COM O USUÁRIO / CLI)
 * ============================================================================
 * A classe Aula serve como ponto de entrada do programa. Ela exibe o menu
 * interativo para o usuário e delega todas as operações bancárias para o
 * ContasServices.
 *
 * CONCEITO DE TRATAMENTO DE EXCEÇÃO (TRY-CATCH):
 * - Bloco 'try': Envolve o código que PODE lançar uma exceção.
 * - Bloco 'catch': Captura a exceção caso ela aconteça e executa um tratamento
 *   amigável (exibe mensagem de erro sem derrubar o programa).
 */
public class Aula {

    private static final Scanner input = new Scanner(System.in);
    private static final ContasServices service = new ContasServices();

    public static void main(String[] args) {
        operacoesBancarias();
    }

    public static void operacoesBancarias() {
        boolean rodando = true;

        while (rodando) {
            System.out.println("\n-------------------------------------------------------------------");
            System.out.println("------------- Bem vindos à nossa Agência Kalleb Bank ---------------");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("       ***** Selecione uma operação que deseja realizar *****");
            System.out.println("-------------------------------------------------------------------");
            System.out.println("|   Opção 1 - Criar conta   |");
            System.out.println("|   Opção 2 - Depositar     |");
            System.out.println("|   Opção 3 - Sacar         |");
            System.out.println("|   Opção 4 - Transferir    |");
            System.out.println("|   Opção 5 - Listar contas |");
            System.out.println("|   Opção 6 - Histórico     |");
            System.out.println("|   Opção 7 - Sair          |");
            System.out.print("Escolha uma opção: ");

            int operacao;
            try {
                operacao = Integer.parseInt(input.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida! Digite apenas números inteiros.");
                continue;
            }

            switch (operacao) {
                case 1 -> criarConta();
                case 2 -> depositar();
                case 3 -> sacar();
                case 4 -> transferir();
                case 5 -> listarContas();
                case 6 -> listarHistorico();
                case 7 -> {
                    System.out.println("Obrigado por utilizar o Kalleb Bank!");
                    rodando = false;
                    continue;
                }
                default -> {
                    System.out.println("Opção inválida. Tente novamente.");
                    continue;
                }
            }

            // Pergunta se o usuário quer realizar outra operação após cada opção (1 a 6)
            if (!desejaContinuar()) {
                System.out.println("Obrigado por utilizar o Kalleb Bank!");
                rodando = false;
            }
        }
    }

    /**
     * Pergunta ao usuário se deseja continuar realizando operações.
     * 
     * CORREÇÃO DOS ERROS:
     * 1. String vs char: 'S' e 'N' (com aspas simples) são 'char'. resposta é String. Deve-se usar "S" (aspas duplas) e .equalsIgnoreCase().
     * 2. Operador lógico: O operador '&' é bitwise. Para comparações lógicas usamos '&&' (E) e '||' (OU).
     * 3. Repetição: Usa um laço while para continuar perguntando até o usuário digitar 'S' ou 'N'.
     *
     * @return true se o usuário digitar 'S', false se digitar 'N'
     */
    private static boolean desejaContinuar() {
        while (true) {
            System.out.print("\nDeseja realizar outra operação? (S para SIM / N para NÃO): ");
            String resposta = input.nextLine().trim();

            if (resposta.equalsIgnoreCase("S")) {
                return true;
            } else if (resposta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("⚠️ Opção inválida! Digite 'S' para SIM ou 'N' para NÃO.");
            }
        }
    }

    /**
     * OPÇÃO 1: CRIAR CONTA
     * Captura exceções com try-catch para exibir mensagens de erro personalizadas.
     */
    private static void criarConta() {
        System.out.println("\n--- CRIAR NOVA CONTA ---");
        System.out.print("Nome: ");
        String nome = input.nextLine();

        System.out.print("E-mail: ");
        String email = input.nextLine();

        System.out.print("CPF: ");
        String cpf = input.nextLine();

        try {
            // Chama o serviço para criar a conta
            Conta conta = service.criarConta(nome, email, cpf);
            System.out.println("\n✅ Conta criada com sucesso!");
            System.out.println("ID da Conta: " + conta.getId());
            System.out.println("Titular: " + conta.getCliente().getNome());
            System.out.println("CPF: " + conta.getCliente().getCpf());
        } catch (BancoException e) {
            // Captura qualquer exceção do nosso banco (ex: cliente já possui conta, campos nulos)
            System.out.println("\n❌ Erro ao criar conta: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("\n❌ Ocorreu um erro inesperado: " + e.getMessage());
        }
    }

    /**
     * OPÇÃO 2: DEPOSITAR
     */
    private static void depositar() {
        System.out.println("\n--- REALIZAR DEPÓSITO ---");
        System.out.print("CPF do titular: ");
        String cpf = input.nextLine();

        System.out.print("ID da conta: ");
        int idConta;
        try {
            idConta = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID de conta inválido.");
            return;
        }

        System.out.print("Valor do depósito: ");
        double valor;
        try {
            valor = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor digitado inválido.");
            return;
        }

        try {
            service.depositar(cpf, idConta, valor);
            System.out.println("✅ Depósito de R$ " + Utils.formatarNumero(valor) + " realizado com sucesso!");
        } catch (BancoException e) {
            System.out.println("❌ Falha no depósito: " + e.getMessage());
        }
    }

    /**
     * OPÇÃO 3: SACAR
     */
    private static void sacar() {
        System.out.println("\n--- REALIZAR SAQUE ---");
        System.out.print("CPF do titular: ");
        String cpf = input.nextLine();

        System.out.print("ID da conta: ");
        int idConta;
        try {
            idConta = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID de conta inválido.");
            return;
        }

        System.out.print("Valor do saque: ");
        double valor;
        try {
            valor = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor digitado inválido.");
            return;
        }

        try {
            service.sacar(cpf, idConta, valor);
            System.out.println("✅ Saque de R$ " + Utils.formatarNumero(valor) + " realizado com sucesso!");
        } catch (BancoException e) {
            System.out.println("❌ Falha no saque: " + e.getMessage());
        }
    }

    /**
     * OPÇÃO 4: TRANSFERIR
     */
    private static void transferir() {
        System.out.println("\n--- REALIZAR TRANSFERÊNCIA ---");
        System.out.print("CPF do titular da conta de origem: ");
        String cpf = input.nextLine();

        System.out.print("ID da sua conta (Origem): ");
        int idOrigem;
        try {
            idOrigem = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID de origem inválido.");
            return;
        }

        System.out.print("ID da conta de destino: ");
        int idDestino;
        try {
            idDestino = Integer.parseInt(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ ID de destino inválido.");
            return;
        }

        System.out.print("Valor da transferência: ");
        double valor;
        try {
            valor = Double.parseDouble(input.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("❌ Valor digitado inválido.");
            return;
        }

        try {
            service.transferir(cpf, idOrigem, idDestino, valor);
            System.out.println("✅ Transferência de R$ " + Utils.formatarNumero(valor) + " realizada com sucesso!");
        } catch (BancoException e) {
            System.out.println("❌ Falha na transferência: " + e.getMessage());
        }
    }

    /**
     * OPÇÃO 5: LISTAR CONTAS
     * Exemplo de percorrimento de List<Conta>.
     */
    private static void listarContas() {
        List<Conta> contas = service.listarContas();

        if (contas.isEmpty()) {
            System.out.println("\n⚠️ Nenhuma conta cadastrada até o momento.");
            return;
        }

        System.out.println("\n---------------- CONTAS BANCÁRIAS CADASTRADAS ----------------");
        for (Conta conta : contas) {
            System.out.printf("ID: %-4d | Titular: %-20s | CPF: %-14s | Saldo: R$ %s%n",
                    conta.getId(),
                    conta.getCliente().getNome(),
                    conta.getCliente().getCpf(),
                    Utils.formatarNumero(conta.getSaldo()));
        }
    }


    /**
     * OPÇÃO 6: LISTAR HISTÓRICO
     * Exemplo de percorrimento de List<Historico>.
     */
    private static void listarHistorico() {
        List<Historico> historicos = service.listarHistorico();

        if (historicos.isEmpty()) {
            System.out.println("\n⚠️ Nenhuma transação realizada até o momento.");
            return;
        }

        System.out.println("\n---------------- HISTÓRICO DE TRANSAÇÕES ----------------");
        for (Historico item : historicos) {
            System.out.println(item);
        }
    }
}
