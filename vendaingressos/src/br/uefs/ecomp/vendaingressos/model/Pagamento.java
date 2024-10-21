package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.excecao.FormaDePagamentoInvalidaException;

import java.util.Objects;

public class Pagamento {
    private String formaDePagamento;  // Cartão ou boleto
    private String nomeTitular;
    private String numeroDoCartao;
    private String validadeDoCartao;
    private String codigoDeSeguranca;
    private String codigoDeBarras;   // Para pagamento com boleto
    private boolean reembolso;
    Compra compra;

    // Forma de pagamento: Cartão
    public Pagamento(String nomeTitular, String numeroDoCartao, String validadeDoCartao, String codigoDeSeguranca) {
        this.formaDePagamento = "Cartão";
        this.nomeTitular = nomeTitular;
        this.numeroDoCartao = numeroDoCartao;
        this.validadeDoCartao = validadeDoCartao;
        this.codigoDeSeguranca = codigoDeSeguranca;
        reembolso = false;
    }

    // Forma de pagamento: Boleto
    public Pagamento(String codigoDeBarras) {
        this.formaDePagamento = "Boleto bancário";
        this.codigoDeBarras = codigoDeBarras;
        reembolso = false;
    }

    public boolean processarPagamento(double valor) {
        if (this.formaDePagamento.equals("Cartão")) {
            // Simula o processamento do pagamento no cartão
            return true; // Pagamento processado com sucesso
        } else if (this.formaDePagamento.equals("Boleto bancário")) {
            // Simula o processamento do pagamento no boleto
            return true; // Pagamento processado com sucesso
        } else {
            return false; // Forma de pagamento inválida
        }
    }

    public String reembolsarPagamento(Compra compra) {
        this.compra = compra;
        if (!reembolso) {
            reembolso = true;  // Marca o pagamento como reembolsado
            return "O pagamento no valor de R$" + compra.getValor() + " será em reembolsado em até 15 dias via " + formaDePagamento + ".";
        } else {
            return "O pagamento já foi reembolsado.";
        }
    }




    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pagamento pagamento = (Pagamento) o;
        // Se a forma de pagamento for cartão, compara o número do cartão
        if (this.formaDePagamento.equals("Cartão")) {
            return Objects.equals(numeroDoCartao, pagamento.numeroDoCartao);
        }
        // Se a forma de pagamento for boleto, compara o código de barras
        if (this.formaDePagamento.equals("Boleto bancário")) {
            return Objects.equals(codigoDeBarras, pagamento.codigoDeBarras);
        }
        return false;
    }

    @Override
    public int hashCode() {
        // Gera o hashCode com base no número do cartão ou código de barras
        return Objects.hash(formaDePagamento.equals("Cartão") ? numeroDoCartao : codigoDeBarras);
    }

    public void setFormaDePagamento(String formaDePagamento) {
        if (formaDePagamento.equals("Boleto bancário") || formaDePagamento.equals("Cartão")) {
            this.formaDePagamento = formaDePagamento;
        } else {
            throw new FormaDePagamentoInvalidaException("Forma de pagamento inválida");
        }
    }

    public String getNomeTitular() {
        return nomeTitular;
    }

    public void setNomeTitular(String nomeTitular) {
        this.nomeTitular = nomeTitular;
    }

    public String getNumeroDoCartao() {
        return numeroDoCartao;
    }

    public void setNumeroDoCartao(String numeroDoCartao) {
        this.numeroDoCartao = numeroDoCartao;
    }

    public String getValidadeDoCartao() {
        return validadeDoCartao;
    }

    public void setValidadeDoCartao(String validadeDoCartao) {
        this.validadeDoCartao = validadeDoCartao;
    }

    public String getCodigoDeSeguranca() {
        return codigoDeSeguranca;
    }

    public void setCodigoDeSeguranca(String codigoDeSeguranca) {
        this.codigoDeSeguranca = codigoDeSeguranca;
    }

    public String getCodigoDeBarras() {
        return codigoDeBarras;
    }

    public void setCodigoDeBarras(String codigoDeBarras) {
        this.codigoDeBarras = codigoDeBarras;
    }

    public boolean isReembolso() {
        return reembolso;
    }

    public void setReembolso(boolean reembolso) {
        this.reembolso = reembolso;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }
}
