package aula.Models;// conta-bancaria

//     - ID: Identificador único da conta bancária
//     - Saldo: Valor atual disponível na conta
//     - ClienteID: Referência ao cliente proprietário da conta

public class Conta {
    private int id = 0;
    private double saldo = 0.0;
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

    public void depositar(double valor) {
        if (valor > 0) {
            saldo += valor;
        } else {
            throw new IllegalArgumentException("O valor do depósito deve ser positivo.");
        }
    }

    public void sacar(double valor) {
        if (valor > 0 && valor <= saldo) {
            saldo -= valor;
        } else {
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido para saque.");
        }
    }

    public void transferir(Conta destino, double valor) {
        if (valor > 0 && valor <= saldo) {
            this.sacar(valor);
            destino.depositar(valor);
        } else {
            throw new IllegalArgumentException("Saldo insuficiente ou valor inválido para transferência.");
        }
    }

    public String toString() {
        return "Conta{" +
                "id='" + id + '\'' +
                ", saldo=" + saldo +
                ", cliente=" + cliente.getNome() +
                '}';
    }

}