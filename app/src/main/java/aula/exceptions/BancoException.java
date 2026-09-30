package aula.exceptions;

/**
 * ============================================================================
 * CONCEITO DE EXCEÇÕES (EXCEPTIONS):
 * ============================================================================
 * Em Java, uma Exceção representa uma condição de erro ou evento anormal
 * que interrompe o fluxo normal de execução do programa.
 *
 * Herdar de RuntimeException cria uma "Unchecked Exception" (Exceção não checada).
 * - Exceções Unchecked: Não exigem cláusula 'throws' obrigatória no método.
 * - Servem perfeitamente para regras de negócio do nosso banco (ex: saldo insuficiente).
 */
public class BancoException extends RuntimeException {
    public BancoException(String mensagem) {
        super(mensagem);
    }
}
