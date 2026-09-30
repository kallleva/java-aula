package aula.exceptions;

/**
 * Exceção lançada para violações genéricas de regras de negócio.
 */
public class RegraNegocioException extends BancoException {
    public RegraNegocioException(String mensagem) {
        super(mensagem);
    }
}
