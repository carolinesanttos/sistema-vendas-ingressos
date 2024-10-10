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
    private List<Ingresso> ingressosDisponiveis = new ArrayList<>();
    private List<Ingresso> ingressosComprados = new ArrayList<>();

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
        } else {
            System.out.println("Evento já cadastrado.");
        }
    }

    // Adiciona um assento à lista de assentos disponíveis.
    public void adicionarAssento(String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (!contemAssento) {
            assentosDisponiveis.add(assento);
        } else {
            System.out.println("Assento já foi adicionado.");
        }
    }

    // Remove um assento da lista de assentos disponíveis.
    public void removerAssento(String assento)     {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (contemAssento) {
            assentosDisponiveis.remove(assento);
        } else {
            System.out.println("Esse assento já foi removido.");
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

    public void adicionarIngresso(Ingresso ingresso) {
        boolean contemIngresso = ingressosDisponiveis.contains(ingresso);
        if (!contemIngresso) {
            ingressosDisponiveis.add(ingresso);
        } else {
            System.out.println("Ingresso já cadastrado.");
        }
    }

    public void removerIngresso(Ingresso ingresso) {
        boolean contemIngresso = ingressosDisponiveis.contains(ingresso);
        if (contemIngresso) {
            ingressosDisponiveis.remove(ingresso);
        } else {
            System.out.println("Esse ingresso já foi removido.");
        }
    }

    // Vende um ingresso. Cria um ingresso para evento e associa ao usuário.
    public Ingresso venderIngresso(Usuario usuario, Pagamento pagamento, Evento evento, String assento) {
        Ingresso ingresso = new Ingresso(usuario, evento, assento); // Cria um ingresso.
        Compra compra = new Compra(usuario, ingresso); // Cria uma compra

        ingressosComprados.add(ingresso); // Adiciona a lista de ingresso comprados do evento
        ingresso.getUsuario().adicionarCompras(new Compra(usuario, ingresso)); // E também adiciona a lista de compras do usuário

        removerIngresso(ingresso);
        removerAssento(assento);
        assentosReservados.add(assento); // Adiciona assento à lista de assentos reservados, pois foi reservado por um usuário.

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
