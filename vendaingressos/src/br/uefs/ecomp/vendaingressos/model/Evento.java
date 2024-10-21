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

import br.uefs.ecomp.vendaingressos.model.excecao.CompraNaoAutorizadaException;
import br.uefs.ecomp.vendaingressos.model.excecao.EventoForaDoPrazoException;
import br.uefs.ecomp.vendaingressos.model.excecao.JaCadastradoException;
import br.uefs.ecomp.vendaingressos.model.excecao.NaoEncontradoException;
import com.google.gson.annotations.Expose;

import java.beans.Transient;
import java.util.*;

public class Evento {
    private String nome;
    private String descricao;
    private Date data;
    private boolean status;
    private Usuario usuario;
    private static List<Evento> eventosCadastrados = new ArrayList<>();
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
    public void cadastroDeEventos(Evento evento) {
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
            throw new JaCadastradoException("Evento já cadastrado.");
        }

    }

    // Adiciona um assento à lista de assentos disponíveis.
    public void adicionarAssento(String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (!contemAssento) {
            assentosDisponiveis.add(assento);
        } else {
            throw new JaCadastradoException("Assento já adicionado.");
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

    public boolean buscaAssento (String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (contemAssento) {
            return true;
        }
        //throw new NaoEncontradoException("Assento não encontrado.");
        return false;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        // Primeiro, verificar se o ingresso já está cadastrado
        for (Ingresso ing : ingressosDisponiveis) {
            if (ing.getEvento().getNome().equals(ingresso.getEvento().getNome()) &&
                    ing.getAssento().equals(ingresso.getAssento())) {
                throw new JaCadastradoException("Ingresso já adicionado.");
            }
        }
        // Se não encontrou nenhum ingresso duplicado, adicione
        ingressosDisponiveis.add(ingresso);
    }

    public void removerIngresso(Ingresso ingresso) {
        boolean contemIngresso = ingressosDisponiveis.contains(ingresso);
        if (contemIngresso) {
            ingressosDisponiveis.remove(ingresso);
        } else {
            System.out.println("Esse ingresso já foi removido.");
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
    public Evento buscarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            }
        }
        throw new NaoEncontradoException("Evento não encontrado.");
    }

    // Vende um ingresso. Cria um ingresso para evento e associa ao usuário.
    public Ingresso venderIngresso(Usuario usuario, Pagamento pagamento, Evento evento, String assento) {
        // Verifica se o evento está ativo
        if (!isAtivo()) {
            throw new EventoForaDoPrazoException(evento.getNome());
        }
        // Verifica se o assento está disponível
        if (!buscaAssento(assento)) {
            throw new NaoEncontradoException("O assento " + assento + " não está disponível.");
        }

        Ingresso ingresso = new Ingresso(usuario, evento, assento); // Cria um ingresso.
        Compra compra = new Compra(usuario, ingresso);

        boolean resultado = compra.processarCompra(pagamento);

        if (resultado) {
            ingressosComprados.add(ingresso); // Adiciona a lista de ingresso comprados do evento
            ingresso.getUsuario().adicionarCompras(new Compra(usuario, ingresso)); // E também adiciona a lista de compras do usuário

            removerIngresso(ingresso); // Remove ingresso da lista de disponíveis
            removerAssento(assento); // Remove assento de disponíveis
            assentosReservados.add(assento); // Adiciona assento à lista de assentos reservados,

            return ingresso; // Retorna o ingresso vendido
        }
        throw new CompraNaoAutorizadaException("Não foi possível processar o pagamento.");
    }

    public static void limparEventosCadastrados() {
        eventosCadastrados.clear();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Evento evento = (Evento) o;
        return Objects.equals(nome, evento.nome) && Objects.equals(descricao, evento.descricao) && Objects.equals(data, evento.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, descricao, data);
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

    public List<String> getAssentosReservados() {
        return assentosReservados;
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
}
