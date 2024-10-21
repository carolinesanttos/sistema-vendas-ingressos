package br.uefs.ecomp.vendaingressos.model;

import java.util.Date;

import br.uefs.ecomp.vendaingressos.model.excecao.CompraJaCanceladaException;
import br.uefs.ecomp.vendaingressos.model.excecao.CompraNaoAutorizadaException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;


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

    public Compra(Usuario usuario, Ingresso ingresso) {
        this.usuario = usuario;
        this.ingresso = ingresso;
        this.valor = 100.0; // Valor padrão
        this.status = "Pendente";
    }

    public Compra(Ingresso ingresso) {
        this.ingresso = ingresso;
        this.valor = 100.0; // Valor padrão
        this.status = "Pendente";
    }

    public boolean processarCompra (Pagamento pagamento) {
        this.pagamento = pagamento;
        boolean resultadoCompra = pagamento.processarPagamento(valor);

        if (resultadoCompra) {
            setStatus("Aprovado");
            return resultadoCompra;
        }
        setStatus("Cancelado");
        throw new CompraNaoAutorizadaException("Compra não autorizada.");

    }

    public String mensagemConfirmaCompra(Usuario usuario, Pagamento pagamento) {
        return "Destinatário: " + usuario.getEmail() + "\nAssunto: Confirmação de Compra\n\n" +
                "Olá, " + usuario.getNome() + ",\n\n" +
                "Obrigado por sua compra! Aqui estão os detalhes da sua compra:\n\n" +
                "Produto: " + ingresso.getEvento().getNome() + " - Assento: " + ingresso.getAssento() + "\n" +
                "Valor: R$ " + valor + "\n" +
                "Método de pagamento: " + pagamento.getFormaDePagamento() + "\n\n" +
                "Sua compra foi processada com sucesso. Caso tenha dúvidas, entre em contato com nosso suporte.\n\n" +
                "Atenciosamente,\nEquipe de Vendas";
    }

    public String confirmarCompra(Usuario usuario, Pagamento pagamento) {
        // Gera o arquivo JSON simulando o "e-mail de confirmação"
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(mensagemConfirmaCompra(usuario, pagamento));

        // Salva o JSON em um arquivo
        try (FileWriter writer = new FileWriter("confirmacao_compra.json")) {
            writer.write(json);
            return mensagemConfirmaCompra(usuario, pagamento);
        } catch (IOException e) {
            return "Erro ao gerar arquivo de confirmação: " + e.getMessage();
        }
    }

    public void cancelarCompra (Compra compra, Pagamento pagamento) {
        if (!(status.equals("Cancelado"))) {
            setStatus("Cancelado");  // Marca a compra como cancelada
        } else {
            throw new CompraJaCanceladaException("A compra já foi cancelada.");
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

    public void setStatus(String status) {
        this.status = status;
    }
}
