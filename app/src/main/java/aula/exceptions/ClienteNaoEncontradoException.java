package aula.exceptions;

/**
 * Exceção lançada quando um cliente não é encontrado no sistema.
 */
public class ClienteNaoEncontradoException extends BancoException {
    public ClienteNaoEncontradoException(String cpf) {
        super("Cliente com CPF " + cpf + " não foi encontrado.");
    }
}
