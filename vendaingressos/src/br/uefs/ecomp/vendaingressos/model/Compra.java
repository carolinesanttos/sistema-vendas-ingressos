/**
 * <p>
 * Sistema Operacional: Windows 10 - 64 Bits<br>
 * IDE: IntelliJ<br>
 * Versão Da Linguagem: Java JDK 22<br>
 * Autor: Caroline Santos de Jesus<br>
 * Componente Curricular: Algoritmos II<br>
 * Concluído em: 21/10/2024<br>
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 * </p>
 */

package br.uefs.ecomp.vendaingressos.model;

import java.util.Date;

import br.uefs.ecomp.vendaingressos.model.excecao.CompraJaCanceladaException;
import br.uefs.ecomp.vendaingressos.model.excecao.CompraNaoAutorizadaException;
import br.uefs.ecomp.vendaingressos.model.excecao.FormaDePagamentoInvalidaException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;


public class Compra {
    private transient  Usuario usuario;
    private Ingresso ingresso;
    private Date data;
    private transient double valor;
    private String status; // "Pendente", "Aprovado", "Cancelada"
    private Pagamento  pagamento;

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

    /**
     * Processa a compra utilizando o pagamento passado.
     * Atualiza o status da compra de acordo com o resultado do pagamento.
     *
     * @param pagamento objeto de pagamento que será processado.
     * @return true se o pagamento foi aprovado, false caso contrário.
     * @throws FormaDePagamentoInvalidaException se o pagamento não for válido.
     */
    public boolean processarCompra (Pagamento pagamento) {
        this.pagamento = pagamento;
        boolean resultadoCompra = pagamento.processarPagamento();

        if (resultadoCompra) {
            setStatus("Aprovado");
            return resultadoCompra;
        } else {
            setStatus("Cancelado");
            throw new FormaDePagamentoInvalidaException("Forma de pagamento inválida.");
        }
    }

    /**
     * Confirma a compra e gera um arquivo JSON simulando um "e-mail de confirmação".
     *
     * @param usuario usuário que realizou a compra.
     * @param pagamento pagamento relacionado à compra.
     * @return A mensagem de confirmação da compra.
     */
    public String confirmarCompra(Usuario usuario, Pagamento pagamento) {
        if (status.equals("Aprovado")) {
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
        } else {
            throw new CompraNaoAutorizadaException( "Compra não pode ser confirmada, status: " + status);
        }

    }

    /**
     * Mensagem de confirmação de compra para o usuário.
     *
     * @param usuario usuário que realizou a compra.
     * @param pagamento agamento relacionado à compra.
     * @return mensagem de confirmação.
     */
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

    /**
     * Cancela a compra se ainda não estiver cancelada.
     *
     * @throws CompraJaCanceladaException caso a compra já foi cancelada anteriormente.
     */
    public void cancelarCompra () {
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
