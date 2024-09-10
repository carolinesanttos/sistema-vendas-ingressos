package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Evento {
    private String nome;
    private String descricao;
    private Date data;
    private boolean status;
    private Usuario usuario;
    private List<String> assentosDisponiveis = new ArrayList<>();
    private List<Evento> eventosCadastrados = new ArrayList<>();
    private List<Ingresso> ingressosComprados  = new ArrayList<>();


    public Evento() {
    }

    public Evento(String nome, String descricao, Date data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }

    public Evento(Usuario usuario, String nome, String descricao, Date data) {
        this.usuario = usuario;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
    }


    // Adiciona o evento desejado à lista de "eventosCadastrados".
    public void cadastroDeEventos(Evento evento) throws SecurityException {
        try {
            evento.getUsuario().verificaUsuario();
            eventosCadastrados.add(evento);
        } catch (SecurityException e) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
    }

    public void adicionarAssento(String assento) {
        assentosDisponiveis.add(assento);
    }

    public void removerAssento(String assento) {
        assentosDisponiveis.remove(assento);
    }

    public boolean isAtivo() {
        Calendar atualData = Calendar.getInstance();
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getData());
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

//    Este método busca um evento pelo seu nome. Se encontrar o evento com nome desejado, o método retorna esse evento,
//    caso contrário, retorna null se nenhum evento for encontrado.
    public Evento encontrarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            }
        }
        return null;
    }


    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public Date getData() {
        return data;
    }

    public boolean getStatus() {
        return status;
    }

    public List<String> getAssentosDisponiveis() {
        return assentosDisponiveis;
    }

    public List<Evento> getEventosCadastrados() {
        return eventosCadastrados;
    }

    public List<Ingresso> getIngressosComprados() {
        return ingressosComprados;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void adicionaIngressoComprado (Ingresso ingresso) {
        ingressosComprados.add(ingresso);
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
//    public Ingresso venderIngresso(String name) {
//        Evento evento = encontrarEventoPorNome(name);
//        return evento.venderIngresso();
//    }




//    /*
//    Este método limpa a lista de eventos cadastrados, removendo todos os eventos da lista. Foi criado para garantir
//    que a cada teste realizado a lista de eventos seja reinicializada e não contenha eventos de testes anteriores.
//    */
//    public static void limparEventos() {
//        eventosCadastrados.clear();
//    }


}
