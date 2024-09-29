// Sistema Operacional: Windows 10 - 64 Bits
// IDE: IntelliJ
// Versão Da Linguagem: Java JDK 22
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
    private List<Evento> eventosCadastrados = new ArrayList<>();
    private List<String> assentosDisponiveis = new ArrayList<>();
    private List<String> assentosReservados = new ArrayList<>();

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

     // Cadastra eventos. O evento só pode ser cadastrado se o usuário for administrador.
     // Caso contrário, lança uma exceção.
    public void cadastroDeEventos(Evento evento) throws SecurityException {
        if (!evento.getUsuario().isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        adicionaEvento(evento);
    }

    public void adicionaEvento(Evento evento) {
        boolean contemEvento = eventosCadastrados.contains(evento);
        if (!contemEvento) {
            eventosCadastrados.add(evento);
        }
    }

    // Adiciona um assento à lista de assentos disponíveis.
    public void adicionarAssento(String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (!contemAssento) {
            assentosDisponiveis.add(assento);
        }
    }

    // Remove um assento da lista de assentos disponíveis.
    public void removerAssento(String assento)     {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (contemAssento) {
            assentosDisponiveis.remove(assento);
        }
    }

    // Verifica se o evento está ativo. O evento é considerado ativo se ainda não passou da data.
    public boolean isAtivo() {
        Calendar atualData = Calendar.getInstance(); // Pega data atual.
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getData()); // Define a data do evento.
        int valor = atualData.compareTo(dataEvento); // Compara a data atual com a data do evento.
        if (valor == 0) { // Se o evento ocorrer no mesmo dia, define como inativo.
            return false;
        } else if (valor < 0) { // Se o evento ainda não aconteceu, define como ativo.
            setStatus(false); // Marca o ingresso como cancelado.
            return true; // Retorna true indicando que o cancelamento foi bem-sucedido.
        } else { // Se a data do evento já passou, não permite cancelamento.
            return false;
        }
    }

    // Busca um evento pelo seu nome. Retorna o evento ou "null" se não existir.
    public Evento encontrarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            }
        }
        return null;
    }

    // Vende um ingresso. Cria um ingresso para evento e associa ao usuário.
    public Ingresso venderIngresso(Usuario usuario, String nomeDoEvento, String assento) {
        Evento evento = encontrarEventoPorNome(nomeDoEvento); // Busca evento pelo nome.
        Ingresso ingresso = new Ingresso(usuario, evento, assento); // Cria um ingresso.
        ingresso.getUsuario().adicionarIngressoComprado(ingresso); // Adiciona ingresso à lista
        // de ingressos comprados pelo usuário.
        assentosDisponiveis.remove(assento); // Remove assento da lista de disponíveis,
        // pois foi reservado por um usuário.
        assentosReservados.add(assento); // Adiciona assento à lista de assentos reservados,
        // pois foi reservado por um usuário.

        return ingresso; // Retorna o ingresso criado.
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
