package aula.repository;

import aula.Models.Conta;
import java.util.Optional;

/**
 * ContaRepository herda de Repository<Conta>.
 * Gerencia o armazenamento e consultas das contas bancárias.
 */
public class ContaRepository extends Repository<Conta> {

    /**
     * Busca uma conta pertencente a um cliente pelo CPF do titular.
     *
     * @param cpf CPF do cliente
     * @return Optional<Conta> contendo a conta se encontrada
     */
    public Optional<Conta> buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return Optional.empty();
        }

        return buscar(conta -> conta.getCliente() != null &&
                conta.getCliente().getCpf().equalsIgnoreCase(cpf.trim()));
    }

    /**
     * Busca uma conta pelo seu número de ID único.
     *
     * @param id Identificador da conta
     * @return Optional<Conta> contendo a conta se encontrada
     */
    public Optional<Conta> buscarPorId(int id) {
        return buscar(conta -> conta.getId() == id);
    }
}
