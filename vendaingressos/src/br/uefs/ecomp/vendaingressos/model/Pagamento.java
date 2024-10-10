package br.uefs.ecomp.vendaingressos.model;

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

    public String processarPagamento(double valor) {
        if (this.formaDePagamento.equals("Cartão")) {
            return "Pagamento no valor de " + valor + " processado com sucesso no cartão.";
        } else if (this.formaDePagamento.equals("Boleto bancário")) {
            return "Pagamento no valor de " + valor + " processado com sucesso no boleto.";
        } else {
            return "Por gentileza, adicionar forma de pagamento válido.";
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


    public String getFormaDePagamento() {
        return formaDePagamento;
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
}
