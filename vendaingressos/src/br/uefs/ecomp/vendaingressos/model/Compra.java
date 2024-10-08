package br.uefs.ecomp.vendaingressos.model;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.IOException;
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

    public String detalhesDaCompra (Usuario usuario, Pagamento pagamento) {
        return "Destinatário: " + usuario.getEmail() + "\nAssunto: Confirmação de Compra\n\n" +
                "Olá, " + usuario.getNome() + ",\n\n" +
                "Obrigado por sua compra! Aqui estão os detalhes da sua compra:\n\n" +
                "Produto: " + ingresso.getEvento().getNome() + " - Assento: " + ingresso.getAssento() + "\n" +
                "Valor: R$ " + valor + "\n" +
                "Método de pagamento: " + pagamento.getFormaDePagamento() + "\n\n" +
                "Sua compra foi processada com sucesso. Caso tenha dúvidas, entre em contato com nosso suporte.\n\n" +
                "Atenciosamente,\nEquipe de Vendas";
    }

    public void confirmarCompra(Usuario usuario, Pagamento pagamento) {
        // Gera o arquivo JSON simulando o "e-mail de confirmação"
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(detalhesDaCompra(usuario, pagamento));

        // Salva o JSON em um arquivo
        try (FileWriter writer = new FileWriter("confirmacao_compra.json")) {
            writer.write(json);
            System.out.println("Arquivo de confirmação gerado com sucesso.");
        } catch (IOException e) {
            System.out.println("Erro ao gerar arquivo de confirmação: " + e.getMessage());
        }
    }

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
