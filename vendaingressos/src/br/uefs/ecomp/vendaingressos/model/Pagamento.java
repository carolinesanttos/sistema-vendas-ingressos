package br.uefs.ecomp.vendaingressos.model;

public class Pagamento {
    private String formaDePagamento;  // Cartão ou boleto
    private String nomeTitular;
    private String numeroDoCartao;
    private String validadeDoCartao;
    private String codigoDeSeguranca;
    private String codigoDeBarras;   // Para pagamento com boleto

    // Forma de pagamento: Cartão
    public Pagamento(String nomeTitular, String numeroDoCartao, String validadeDoCartao, String codigoDeSeguranca) {
        this.formaDePagamento = "Cartão";
        this.nomeTitular = nomeTitular;
        this.numeroDoCartao = numeroDoCartao;
        this.validadeDoCartao = validadeDoCartao;
        this.codigoDeSeguranca = codigoDeSeguranca;
    }

    // Forma de pagamento: Boleto
    public Pagamento(String codigoDeBarras) {
        this.formaDePagamento = "Boleto bancário";
        this.codigoDeBarras = codigoDeBarras;
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

//    public double reembolsarPagamento() {
//
//    }


    public String getFormaDePagamento() {
        return formaDePagamento;
    }
}
