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

import com.google.gson.annotations.Expose;

import java.beans.Transient;
import java.util.*;

public class Ingresso {
    private double preco;
    private transient boolean status;
    private transient Evento evento;
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
        return getStatus(); // Retorna o status do ingresso.
    }

    /**
     * Cancela o ingresso, seguindo as regras:
     * 1) Não permite cancelamento no dia do evento.
     * 2) Permite cancelamento antes do evento.
     * 3) Não permite cancelamento após a data do evento.
     *
     * @return true se o cancelamento foi bem-sucedido, false caso contrário.
     */
    public boolean cancelarIngresso() {
        Calendar atualData = Calendar.getInstance(); // Pega data atual.
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getEvento().getData()); // Define a data do evento.
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
     * @param o O objeto a ser comparado.
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
