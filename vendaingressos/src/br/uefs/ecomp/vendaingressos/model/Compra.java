package br.uefs.ecomp.vendaingressos.model;

import java.util.Date;

public class Compra {
    private Usuario usuario;
    private Ingresso ingresso;
    private Date data;
    private double valor;
    private String status; // "Pendente", "Aprovado", "Cancelada"
    Pagamento pagamento;

    public Compra(Usuario usuario, Ingresso ingresso, Date data, double valor) {
        this.usuario = usuario;
        this.ingresso = ingresso;
        this.data = data;
        this.valor = valor;
        this.status = "Pendente";
    }

    public Compra(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public String processarCompra (Pagamento pagamento) {
        this.pagamento = pagamento;
        String resultadoCompra = pagamento.processarPagamento(valor);

        if (resultadoCompra.equals("Pagamento no valor de " + valor + " processado com sucesso no cartão.") || resultadoCompra.equals(
                "Pagamento no valor de " + valor + " processado com sucesso no boleto.")) {
            this.status = "Aprovado";
        }

        return resultadoCompra;
    }

// public void confirmacaoDaCompra ()

    public Usuario getUsuario() {
        return usuario;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public Date getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public double getValor() {
        return valor;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
