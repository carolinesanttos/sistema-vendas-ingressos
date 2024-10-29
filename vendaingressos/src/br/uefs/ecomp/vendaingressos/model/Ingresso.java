/**
 * Sistema Operacional: Windows 10 - 64 Bits
 * IDE: IntelliJ
 * Versão Da Linguagem: Java JDK 22
 * Autor: Caroline Santos de Jesus
 * Componente Curricular: Algoritmos II
 * Concluído em: 28/10/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 */

package br.uefs.ecomp.vendaingressos.model;

import java.util.*;

/**
 * Classe que representa ingresso no sistema de venda de ingressos.
 *
 * Gerencia as informações do ingresso, como preço, status (ativo ou cancelado),
 * evento associado e assento. Permite verificar se o ingresso está ativo,
 * cancelar o ingresso com base em regras específicas e reativar ingressos cancelados.
 *
 * A comparação de ingressos é feita com base em preço, evento e assento.
 */
public class Ingresso {
    private double preco;
    private transient boolean status;
    private transient Evento evento;
    private String nomeEvento;
    private String assento;
    private transient Usuario usuario;
    private Compra compra;

    public Ingresso(Evento evento, double valor, String assento) {
        this.evento = evento;
        this.preco = valor;
        this.assento = assento;
        this.status = true; // Ingressos inicialmente estão ativos.
    }

    public Ingresso(Evento evento, String assento) {
        this.evento = evento;
        this.nomeEvento = evento.getNome();
        this.assento = assento;
        this.preco = 100.0; // Preço padrão do ingresso.
        this.status = true; // Ingressos inicialmente estão ativos.
    }


    /**
     * Verifica se o ingresso está ativo.
     *
     * @return true se o ingresso estiver ativo, false caso contrário.
     */
    public boolean isAtivo() {
        return getStatus();
    }

    /**
     * Cancela o ingresso, com as regras:
     * 1) Não permite cancelamento no dia do evento.
     * 2) Permite cancelamento antes do evento.
     * 3) Não permite cancelamento após a data do evento.
     *
     * @return true se o cancelamento foi bem-sucedido, false caso contrário.
     */
    public boolean cancelarIngresso() {
        Calendar atualData = Calendar.getInstance(); // Pega data atual.
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getEvento().getData()); // Data do evento
        int valor = atualData.compareTo(dataEvento); // Compara a data atual com a data do evento.
        if (valor == 0) { // Se o evento ocorrer no mesmo dia, não permite cancelamento.
            return false;
        } else if (valor < 0) { // Se a data atual for antes do evento, permite o cancelamento.
            setStatus(false); // Marca o ingresso como cancelado.
            return true; // Retorna true indicando que o cancelamento foi bem-sucedido.
        } else { // Se a data do evento já passou, não permite cancelamento.
            return false;
        }
    }

    /**
     * Reativa o ingresso, definindo seu status como ativo.
     */
    public void reativar() {
        setStatus(true);
    }

    /**
     * Compara se dois ingressos são iguais com base em preço, evento e assento.
     *
     * @param o objeto comparado.
     * @return true se os ingressos forem iguais, false caso contrário.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingresso ingresso = (Ingresso) o;
        return Double.compare(preco, ingresso.preco) == 0 && Objects.equals(evento, ingresso.evento) && Objects.equals(assento, ingresso.assento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(preco, evento, assento);
    }

    public double getPreco() {
        return preco;
    }

    public boolean getStatus() {
        return status;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getAssento() {
        return assento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }
}
