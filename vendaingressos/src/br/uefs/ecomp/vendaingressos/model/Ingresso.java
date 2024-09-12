package br.uefs.ecomp.vendaingressos.model;

import java.util.*;

public class Ingresso {
    private String id;
    private double preco;
    private boolean status;
    private Evento evento;
    private String assento;
    private Usuario usuario;

    public Ingresso() {
    }

    public Ingresso(Evento evento, double valor, String assento) {
        this.evento = evento;
        this.preco = valor;
        this.assento = assento;
        this.status = true;
    }

    public Ingresso(Usuario user, Evento evento, String assento) {
        this.usuario = user;
        this.evento = evento;
        this.assento = assento;
        this.preco = 100.0;
        this.status = true;
    }

    public boolean isAtivo() {
        return getStatus();
    }

    public boolean cancelarIngresso() {
        Calendar atualData = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getEvento().getData());
        int valor = atualData.compareTo(dataEvento);
        if (valor == 0) { // Não pode cancelar no mesmo dia do evento.
            return false;
        } else if (valor < 0) { // Pode cancelar, pois não passou da data do evento.
            setStatus(false);
            return true;
        } else {// Não pode cancelar, data passada.
            return false;
        }
    }

    public void reativar() {
        setStatus(true);
    }

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

    public String getId() {
        return id;
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

    public void setId(String id) {
        this.id = id;
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

}
