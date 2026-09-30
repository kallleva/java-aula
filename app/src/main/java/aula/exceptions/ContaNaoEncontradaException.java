package aula.exceptions;

/**
 * Exceção lançada quando uma conta bancária não é encontrada.
 */
public class ContaNaoEncontradaException extends BancoException {
    public ContaNaoEncontradaException(int id) {
        super("Conta com ID " + id + " não foi encontrada.");
    }
}
