package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class Evento {
    private String nome;
    private String descricao;
    private String data;
    private static List<Ingresso> ingressosComprados;
    private String status;
    private static List<Evento> eventosCadastrados = new ArrayList<>();

    public Evento() {
    }

    public Evento(String nome, String descricao, String data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        ingressosComprados = new ArrayList<>();
    }

    public Ingresso venderIngresso() {
        Ingresso ingresso = new Ingresso(this);
        return ingresso;
    }

    public boolean isAtivo() {
        return true;
    }

    public void cadastroDeEventos(Evento evento) {
        eventosCadastrados.add(evento);
    }

    public Ingresso compraDeIngresso (String name) {
        Evento ev = encontrarEventoPorNome(name);
        Ingresso venda = ev.venderIngresso();
        ingressosComprados.add(venda);
        return venda;
    }

    private Evento encontrarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            }
        }
        return null;
    }

    public static void limparEventos() {
        eventosCadastrados.clear();
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getStatus() {
        return status;
    }

    public List<Evento> getEventosCadastrados() {
        return eventosCadastrados;
    }

    public List<Ingresso> getIngressosComprados() {
        return ingressosComprados;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
