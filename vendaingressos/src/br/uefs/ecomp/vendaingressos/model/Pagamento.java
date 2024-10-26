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

import br.uefs.ecomp.vendaingressos.model.excecao.CompraNaoAutorizadaException;
import br.uefs.ecomp.vendaingressos.model.excecao.FormaDePagamentoInvalidaException;
import br.uefs.ecomp.vendaingressos.model.excecao.ReembolsoException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Objects;

public class Pagamento {
    private String formaDePagamento;  // Cartão ou boleto
    private transient String nomeTitular;
    private transient String numeroDoCartao;
    private transient String validadeDoCartao;
    private transient String codigoDeSeguranca;
    private transient String codigoDeBarras;   // Para pagamento com boleto
    private boolean reembolso;
    private Compra compra;

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

    /**
     * Processa o pagamento de acordo com a forma de pagamento escolhida.
     *
     * @return true se pagamento foi processado com sucesso, false caso contrário.
     */
    public boolean processarPagamento() {
        // Simula o processamento do pagamento no cartão e simula o processamento do pagamento no boleto
        if (this.formaDePagamento.equals("Cartão") || this.formaDePagamento.equals("Boleto bancário")) {
            return true; // Pagamento processado com sucesso
        } else {
            return false;
        }
    }

    /**
     * Realiza o reembolso do pagamento associado a uma compra, se o reembolso ainda não tiver sido processado.
     * Marca o pagamento como reembolsado e gera um arquivo GSON simulando o envio de um e-mail de reembolso
     * para o usuário.
     *
     * @param usuario usuário que solicita o reembolso.
     * @param compra compra associada ao pagamento.
     * @return uma mensagem de confirmação do reembolso ou uma mensagem de erro se houver falha ao gerar o arquivo GSON
     *
     * @throws ReembolsoException se o pagamento já tiver sido reembolsado anteriormente
     */
    public String reembolsarPagamento(Usuario usuario, Compra compra) {
        this.compra = compra;

        if (!reembolso) {
            reembolso = true;  // Marca o pagamento como reembolsado

            // Gera arquivo GSON simulando o e-mail de reembolso
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            String json = gson.toJson(mensagemDeReembolso(usuario, formaDePagamento));

            // Salva GSON em um arquivo
            try (FileWriter writer = new FileWriter("reembolso_compra.json")) {
                writer.write(json);
                return mensagemDeReembolso(usuario, formaDePagamento);
            } catch (IOException e) {
                return "Erro ao gerar arquivo de confirmação: " + e.getMessage();
            }
        } else {
            throw new ReembolsoException("O pagamento já foi reembolsado.");
        }
    }

    /**
     * Gera a mensagem de reembolso para o usuário.
     *
     * @param usuario usuário que receberá a mensagem.
     * @param formaPagamento forma de pagamento utilizada.
     * @return mensagem de reembolso.
     */
    public String mensagemDeReembolso(Usuario usuario, String formaPagamento) {
        return "Destinatário: " + usuario.getEmail() + "\nAssunto: Reembolso da Compra\n\n" +
                "Olá, " + usuario.getNome() + ",\n\n" +
                "O pagamento no valor de R$" + compra.getValor() + " será em reembolsado em até 15 dias via " +
                formaDePagamento + "." + " Caso tenha dúvidas, entre em contato com nosso suporte.\n\n" +
                "Atenciosamente,\nEquipe de Vendas";
    }

    /**
     * Compara este objeto com outro para verificar se são iguais.
     * A comparação é baseada na forma de pagamento e nos dados.
     *
     * @param o objeto que será comparado.
     * @return true se os objetos forem iguais, false caso contrário.
     */
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

    /**
     * Define a forma de pagamento. Se a forma de pagamento for inválida, lança uma exceção.
     *
     * @param formaDePagamento nova forma de pagamento a ser definida.
     * @throws FormaDePagamentoInvalidaException se a forma de pagamento não for válida.
     */
    public void setFormaDePagamento(String formaDePagamento) {
        if (formaDePagamento.equals("Boleto bancário") || formaDePagamento.equals("Cartão")) {
            this.formaDePagamento = formaDePagamento;
        } else {
            throw new FormaDePagamentoInvalidaException("Forma de pagamento inválida.");
        }
    }

    public String getFormaDePagamento() {
        return formaDePagamento;
    }
}
