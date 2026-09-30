package aula.repository;

import aula.Models.Cliente;
import java.util.Optional;

/**
 * ClienteRepository herda de Repository<Cliente>.
 * Aqui fixamos o tipo genérico T como 'Cliente'.
 */
public class ClienteRepository extends Repository<Cliente> {

    /**
     * Busca um cliente pelo seu CPF.
     *
     * Retorna Optional<Cliente>:
     * - Se o cliente for encontrado -> Optional.of(cliente)
     * - Se não for encontrado -> Optional.empty()
     *
     * @param cpf CPF a ser pesquisado
     * @return Optional contendo o cliente ou vazio se não encontrado.
     */
    public Optional<Cliente> buscarPorCpf(String cpf) {
        if (cpf == null || cpf.isBlank()) {
            return Optional.empty();
        }

        // Usamos o método buscar genérico da classe pai (Repository<T>)
        return buscar(cliente -> cliente.getCpf().equalsIgnoreCase(cpf.trim()));
    }
}