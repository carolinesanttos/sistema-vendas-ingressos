package br.uefs.ecomp.vendaingressos.model;

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
}
