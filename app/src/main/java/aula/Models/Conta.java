package aula.Models;

import aula.exceptions.SaldoInsuficienteException;
import aula.exceptions.ValorInvalidoException;

public class Conta {

    private int id;
    private double saldo;
    private Cliente cliente;

    public Conta(int id, double saldo, Cliente cliente) {
        this.id = id;
        this.saldo = saldo;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    /**
     * CONCEITO DE EXCEÇÃO (EXCEPTION):
     * Em vez de retornar um código de erro ou apenas imprimir no console,
     * o método 'lança' (throw) uma exceção se o parâmetro for inválido.
     * Isso obriga o chamador a lidar com o erro de forma explícita.
     */
    public void depositar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser maior que zero (R$ " + valor + " informado).");
        }
        this.saldo += valor;
    }

    /**
     * Realiza saque verificando saldo e valor válido.
     * Lança exceções específicas em caso de falha.
     */
    public void sacar(double valor) {
        if (valor <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser maior que zero (R$ " + valor + " informado).");
        }
        if (valor > this.saldo) {
            throw new SaldoInsuficienteException(this.saldo, valor);
        }
        this.saldo -= valor;
    }

    /**
     * Transfere o valor para a conta destino.
     */
    public void transferir(Conta destino, double valor) {
        if (destino == null) {
            throw new IllegalArgumentException("Conta destino não pode ser nula.");
        }
        // O próprio método sacar valida o valor e o saldo suficiente
        this.sacar(valor);
        destino.depositar(valor);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", saldo=" + String.format("R$ %.2f", saldo) +
                ", cliente=" + cliente.getNome() +
                '}';
    }
}