package aula.Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Modelo para registro de Histórico de Transações Bancárias.
 * Regras:
 * - ID: Identificador único da transação
 * - Tipo: Tipo de transação (DEPÓSITO, SAQUE, TRANSFERÊNCIA)
 * - Valor: Valor da transação
 * - ContaID: Referência à conta de origem envolvida na transação
 * - ContaDestinoID: Referência à conta de destino (apenas para transferências)
 */
public class Historico {
    private String id;
    private String tipo;
    private double valor;
    private String contaId;
    private String contaDestinoId;
    private LocalDateTime dataHora;

    public Historico(String id, String tipo, double valor, String contaId, String contaDestinoId) {
        this.id = id;
        this.tipo = tipo;
        this.valor = valor;
        this.contaId = contaId;
        this.contaDestinoId = contaDestinoId;
        this.dataHora = LocalDateTime.now();
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

    public LocalDateTime getDataHora() {
        return dataHora;
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String destino = (contaDestinoId == null || contaDestinoId.isEmpty()) ? "-" : contaDestinoId;
        return String.format("[%s] ID: %s | Tipo: %-12s | Valor: R$ %-8.2f | Conta: %s | Destino: %s",
                dataHora.format(fmt), id, tipo, valor, contaId, destino);
    }
}