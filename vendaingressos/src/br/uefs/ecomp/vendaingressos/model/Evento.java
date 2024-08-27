package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Evento {
    private String nome;
    private String descricao;
    private String data;
    private List<Ingresso> ingressos;
    private String status;
    private static List<Evento> eventosCadastrados;

    public Evento(String nome, String descricao, String data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        ingressos = new ArrayList<>();
        eventosCadastrados = new ArrayList<>();
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

    public static List<Evento> getEventosCadastrados() {
        return eventosCadastrados;
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
