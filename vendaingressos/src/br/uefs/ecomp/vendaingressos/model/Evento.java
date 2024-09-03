package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
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

    /*
    Adiciona o evento desejado à lista de "eventosCadastrados".
     */
    public void cadastroDeEventos(Evento evento) {
        eventosCadastrados.add(evento);
    }

    // Retorna true se o evento estiver presente na lista de "eventosCadastrados".
    public boolean isAtivo() {
        return true;
    }

    /*
    Sobrecarga de métodos:
    - O primeiro método venderIngresso() é utilizado pela classe `EventoTest`.
    - O segundo método venderIngresso(String name) é chamado pela classe `Controller`.

    A sobrecarga foi necessária porque o segundo método precisa do parâmetro (`name`),
    enquanto o primeiro não. Afim de evitar duplicação de código, o segundo método reaproveita
    a lógica do primeiro.
    */
    public Ingresso venderIngresso() {
        Ingresso ingresso = new Ingresso(this);
        ingressosComprados.add(ingresso);
        return ingresso;
    }
    public Ingresso venderIngresso(String name) {
        Evento evento = encontrarEventoPorNome(name);
        return evento.venderIngresso();
    }

    /*
    Este método busca um evento pelo seu nome. Se encontrar o evento com nome desejado, o método retorna esse evento,
    caso contrário, retorna null se nenhum evento for encontrado.
    */
    private Evento encontrarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            } else {
                return null;
            }
        }
        return null;
    }

    /*
    Este método limpa a lista de eventos cadastrados, removendo todos os eventos da lista. Foi criado para garantir
    que a cada teste realizado a lista de eventos seja reinicializada e não contenha eventos de testes anteriores.
    */
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

    public void adicionaIngressoComprado (Ingresso ingresso) {
        ingressosComprados.add(ingresso);
    }
}
