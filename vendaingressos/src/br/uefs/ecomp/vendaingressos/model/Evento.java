// Sistema Operacional: Windows 10 - 64 Bits
// Versão Da Linguagem: Java
// Autor: Caroline Santos de Jesus
// Componente Curricular: Algoritmos II
// Concluido em: 12/09/2024
// Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
// colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
// Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
// código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

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
        if (!evento.getUsuario().isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        eventosCadastrados.add(evento);
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

    /*
    Sobrecarga de métodos:
    - O primeiro método venderIngresso() é utilizado pela classe `EventoTest`.
    - O segundo método venderIngresso(String name) é chamado pela classe `Controller`.

    A sobrecarga foi necessária porque o segundo método precisa do parâmetro (`name`),
    enquanto o primeiro não. Afim de evitar duplicação de código, o segundo método reaproveita
    a lógica do primeiro.
    */
    public Ingresso venderIngresso(Usuario usuario, String nomeDoEvento, String assento) {
        Evento evento = encontrarEventoPorNome(nomeDoEvento);
        Ingresso ingresso = new Ingresso(usuario, evento, assento);
        ingresso.getUsuario().adicionarIngressoComprado(ingresso);
        return ingresso;
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
}
