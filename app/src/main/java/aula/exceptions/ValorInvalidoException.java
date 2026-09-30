package aula.exceptions;

/**
 * Exceção lançada para valores monetários inválidos (ex: menor ou igual a zero).
 */
public class ValorInvalidoException extends BancoException {
    public ValorInvalidoException(String mensagem) {
        super(mensagem);
    }
}
