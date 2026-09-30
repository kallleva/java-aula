package aula.exceptions;

/**
 * Exceção lançada quando a conta não possui saldo suficiente para a operação.
 */
public class SaldoInsuficienteException extends BancoException {
    public SaldoInsuficienteException(double saldoAtual, double valorSolicitado) {
        super(String.format("Saldo insuficiente! Saldo atual: R$ %.2f. Valor da operação: R$ %.2f.", saldoAtual, valorSolicitado));
    }
}
