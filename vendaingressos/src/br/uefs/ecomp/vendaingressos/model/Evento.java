/**
 * <p>
 * Sistema Operacional: Windows 10 - 64 Bits<br>
 * IDE: IntelliJ<br>
 * Versão Da Linguagem: Java JDK 22<br>
 * Autor: Caroline Santos de Jesus<br>
 * Componente Curricular: Algoritmos II<br>
 * Concluído em: 21/10/2024<br>
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 * </p>
 */

package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.excecao.CompraNaoAutorizadaException;
import br.uefs.ecomp.vendaingressos.model.excecao.EventoForaDoPrazoException;
import br.uefs.ecomp.vendaingressos.model.excecao.JaCadastradoException;
import br.uefs.ecomp.vendaingressos.model.excecao.NaoEncontradoException;

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
    private List<Feedback> feedbacks = new ArrayList<>();


    public Evento(String nome, String descricao, Date data) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.status = true;

    }

    public Evento(Usuario usuario, String nome, String descricao, Date data) {
        this.usuario = usuario;
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.status = true;
    }

    /**
     * Cadastra evento no sistema
     * Somente usuários administradores podem cadastrar.
     *
     * @param evento evento que será cadastrado.
     * @throws SecurityException se o usuário não for um administrador.
     */
    public void cadastroDeEventos(Evento evento) {
        if (!evento.getUsuario().isAdmin()) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        adicionaEvento(evento);
    }

    /**
     * Adiciona evento à lista de eventos cadastrados.
     *
     * @param evento evento que será adicionado.
     * @throws JaCadastradoException se o evento já estiver cadastrado.
     */
    public void adicionaEvento(Evento evento) {
        boolean contemEvento = eventosCadastrados.contains(evento);
        if (!contemEvento) {
            eventosCadastrados.add(evento);
        } else {
            throw new JaCadastradoException("Evento já cadastrado.");
        }
    }

    /**
     * Adiciona assento à lista de assentos disponíveis.
     *
     * @param assento assento que será adicionado.
     * @throws JaCadastradoException se o assento já estiver na lista.
     */
    public void adicionarAssento(String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (!contemAssento) {
            assentosDisponiveis.add(assento);
        } else {
            throw new JaCadastradoException("Assento já adicionado.");
        }
    }

    /**
     * Remove assento da lista de assentos disponíveis.
     *
     * @param assento assento a ser removido.
     */
    public void removerAssentoDisponivel(String assento)     {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (contemAssento) {
            assentosDisponiveis.remove(assento);
        } else {
            System.out.println("Esse assento já foi removido.");
        }
    }

    /**
     * Verifica se assento está disponível.
     *
     * @param assento assento buscado.
     * @return true se o assento estiver disponível, false caso contrário.
     */
    public boolean buscaAssento (String assento) {
        boolean contemAssento = assentosDisponiveis.contains(assento);
        if (contemAssento) {
            return true;
        }
        return false;
    }

    /**
     * Adiciona ingresso à lista de ingressos disponíveis.
     *
     * @param ingresso ingresso a ser adicionado.
     * @throws JaCadastradoException se o ingresso já estiver cadastrado.
     */
    public void adicionarIngresso(Ingresso ingresso) {
        // Primeiro, verificar se o ingresso já está cadastrado
        for (Ingresso ing : ingressosDisponiveis) {
            if (ing.getEvento().getNome().equals(ingresso.getEvento().getNome()) &&
                    ing.getAssento().equals(ingresso.getAssento())) {
                throw new JaCadastradoException("Ingresso já adicionado.");
            }
        }

        // Se não encontrou nenhum ingresso duplicado, adiciona
        ingressosDisponiveis.add(ingresso);
    }

    /**
     * Remove ingresso da lista de ingressos disponíveis.
     *
     * @param ingresso ingresso a ser removido.
     */
    public void removerIngressoDisponivel(Ingresso ingresso) {
        boolean contemIngresso = ingressosDisponiveis.contains(ingresso);
        if (contemIngresso) {
            ingressosDisponiveis.remove(ingresso);
        } else {
            System.out.println("Esse ingresso já foi removido.");
        }
    }

    /**
     * Verifica se o evento está ativo.
     *
     * @return true se o evento estiver ativo, false caso contrário.
     */
    public boolean isAtivo() {
        Calendar atualData = Calendar.getInstance(); // Pega data atual.
        Calendar dataEvento = Calendar.getInstance();
        dataEvento.setTime(getData()); // Data do evento.
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

    /**
     * Busca evento pelo seu nome.
     *
     * @param name nome do evento a ser buscado.
     * @return evento correspondente ao nome passado.
     * @throws NaoEncontradoException se o evento não for encontrado.
     */
    public Evento buscarEventoPorNome(String name) {
        for (Evento evento : getEventosCadastrados()) {
            if (evento.getNome().equalsIgnoreCase(name)) {
                return evento;
            }
        }
        throw new NaoEncontradoException("Evento não encontrado.");
    }

    /**
     * Vende um ingresso para o usuário, associando-o ao evento e ao pagamento.
     *
     * @param usuario   usuário que está comprando o ingresso.
     * @param pagamento método de pagamento utilizado.
     * @param evento    evento para o ingresso que está sendo vendido.
     * @param assento   assento do ingresso a ser vendido.
     * @return ingresso vendido.
     * @throws EventoForaDoPrazoException se o evento não estiver ativo.
     * @throws NaoEncontradoException      se o assento não estiver disponível.
     * @throws CompraNaoAutorizadaException se o pagamento não puder ser processado.
     */
    public Ingresso comprarIngresso(Usuario usuario, Pagamento pagamento, Evento evento, String assento) {
        // Verifica se o evento está ativo
        if (!isAtivo()) {
            throw new EventoForaDoPrazoException(evento.getNome());
        }
        // Verifica se o assento está disponível
        if (!buscaAssento(assento)) {
            throw new NaoEncontradoException("O assento " + assento + " não está disponível.");
        }

        Ingresso ingresso = buscarPorIngresso(evento, assento);

        Compra compra = new Compra(usuario, ingresso);

        boolean resultado = compra.processarCompra(pagamento);

        if (resultado) {
            ingressosComprados.add(ingresso); // Adiciona a lista de ingresso comprados do evento
            usuario.adicionarCompras(compra); // E também adiciona a lista de compras do usuário
            assentosReservados.add(assento); // Adiciona assento à lista de assentos reservados

            removerIngressoDisponivel(ingresso); // Remove ingresso da lista de disponíveis
            removerAssentoDisponivel(assento); // Remove assento de disponíveis

            return ingresso; // Retorna o ingresso vendido
        }

        throw new CompraNaoAutorizadaException("Não foi possível processar o pagamento.");
    }

    /**
     * Cancela ingresso comprado pelo usuário.
     *
     * @param ingresso ingresso a ser cancelado.
     */
    public void cancelarIngressoComprado(Ingresso ingresso) {
        Iterator<Ingresso> iterator = ingressosComprados.iterator();

        while (iterator.hasNext()) {
            Ingresso ing = iterator.next();

            if (ing.equals(ingresso)) {
                boolean cancelar = ingresso.cancelarIngresso();

                if (cancelar) {
                    iterator.remove();
                }
            }
        }

    }

    /**
     * Busca ingresso na lista de ingressos disponíveis pelo evento e assento.
     *
     * @param evento  evento que ingresso pertence.
     * @param assento assento do ingresso a ser buscado.
     * @return ingresso correspondente ao evento e assento informados.
     * @throws NaoEncontradoException se o ingresso não for encontrado.
     */
    private Ingresso buscarPorIngresso(Evento evento, String assento) {
        for (Ingresso ingresso: ingressosDisponiveis) {
            if (ingresso.getEvento().equals(evento) && ingresso.getAssento().equals(assento)) {
                return ingresso;
            }
        }
        throw new NaoEncontradoException("Ingresso não encontrado.");
    }

    /**
     * Adiciona feedback à lista de feedbacks do evento.
     *
     * @param feedback feedback a ser adicionado.
     */
    public void adicionarFeedbacks (Feedback feedback) {
        feedbacks.add(feedback);
    }

    /**
     * Limpa a lista de eventos cadastrados.
     */
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

    public List<Ingresso> getIngressosDisponiveis() {
        return ingressosDisponiveis;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
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
