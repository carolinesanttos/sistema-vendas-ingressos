package br.uefs.ecomp.vendaingressos.model;

public class Ingresso {
    private String id;
    private float valor;
    private String status;
    private Evento evento;

    public Ingresso(String id, Evento evento) {
        this.id = id;
        this.evento = evento;
    }

    public String getId() {
        return id;
    }

    public float getValor() {
        return valor;
    }

    public String getStatus() {
        return status;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }
}
