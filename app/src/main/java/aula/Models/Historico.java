package aula.Models;
// histórico-transações
//     - ID: Identificador único da transação
//     - Tipo: Tipo de transação (depósito, saque, transferência)
//     - Valor: Valor da transação
//     - ContaID: Referência à conta envolvida na transação
//     - contaDestinoID: Referência à conta de destino (apenas para transferências)

public class Historico {
    private String id;
    private String tipo;
    private double valor;
    private String contaId;
    private String contaDestinoId;

    public Historico(String id, String tipo, double valor, String contaId, String contaDestinoId) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.contaId = contaId;
        this.contaDestinoId = contaDestinoId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getContaId() {
        return contaId;
    }

    public void setContaId(String contaId) {
        this.contaId = contaId;
    }

    public String getContaDestinoId() {
        return contaDestinoId;
    }

    public void setContaDestinoId(String contaDestinoId) {
        this.contaDestinoId = contaDestinoId;
    }
}