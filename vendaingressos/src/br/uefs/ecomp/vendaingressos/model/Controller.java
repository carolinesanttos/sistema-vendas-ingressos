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

import br.uefs.ecomp.vendaingressos.model.excecao.NaoEncontradoException;

import java.util.Date;
import java.util.List;

public class Controller {

    Usuario usuario;
    Evento evento;
    Ingresso ingresso;
    Compra compra;

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @param nome O nome completo do usuário.
     * @param cpf O CPF do usuário.
     * @param email O email do usuário.
     * @param isAdm Indica se o usuário é um administrador.
     * @return O usuário criado.
     */
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdm) {
        usuario = new Usuario(login, senha, nome, cpf, email, isAdm);
        usuario.cadastroDeUsuarios(usuario); // Cadastra usuário na lista de usuários.
        return usuario; // Retorna usuário criado.
    }

    /**
     * Realiza o login do usuário.
     *
     * @param login O login do usuário.
     * @param senha A senha do usuário.
     * @return O usuário logado.
     * @throws NaoEncontradoException Se o login ou a senha estiverem incorretos.
     */
    public Usuario login (String login, String senha) {
        boolean logado = usuario.login(login, senha);  // Chama o método que pode lançar a exceção
        if (logado) {
            return usuario;  // Retorna o usuário se o login for bem-sucedido
        } else {
            throw new NaoEncontradoException("Usuário ou senha");  // Lança exceção se o login falhar
        }
    }

    /**
     * Altera o nome do usuário.
     *
     * @param nome O novo nome do usuário.
     */
    public void alterarNome (String nome) {
        usuario.setNome(nome);
    }

    /**
     * Altera o email do usuário.
     *
     * @param email O novo email do usuário.
     */
    public void alterarEmail (String email) {
        usuario.setEmail(email);
    }

    /**
     * Altera a senha do usuário.
     *
     * @param senha A nova senha do usuário.
     */
    public void alterarSenha (String senha) {
        usuario.setSenha(senha);
    }

    /**
     * Cadastra um novo evento no sistema.
     *
     * @param usuario O usuário que está cadastrando o evento.
     * @param nomeDoEvento O nome do evento a ser cadastrado.
     * @param descricao A descrição do evento.
     * @param data A data do evento.
     * @return O evento criado.
     * @throws SecurityException Se o usuário não estiver logado ou não for administrador.
     */
    public Evento cadastrarEvento(Usuario usuario, String nomeDoEvento, String descricao, Date data) {
        if (!usuario.isLogado()) { // Verifica se usuário está logado
            throw new SecurityException("Precisa estar logado para cadastrar um evento.");
        }
        if (!(usuario.isAdmin())) { // Verifica se usuário é um administrador
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        // Se as condições forem atendidas, o evento é cadastrado
        evento = new Evento(usuario, nomeDoEvento, descricao, data); // Cria um novo evento com as informações dadas.
        evento.cadastroDeEventos(evento); // Supondo que esse método já trata de cadastro de eventos.

        return evento; // Retorna o evento criado.

    }

    /**
     * Adiciona um assento disponível a um evento específico.
     *
     * @param nomeDoEvento O nome do evento.
     * @param assento O assento a ser adicionado.
     */
    public void adicionarAssentoEvento(String nomeDoEvento, String assento) {
        evento = evento.buscarEventoPorNome(nomeDoEvento); // Encontra o evento pelo nome.
        if (evento!= null) { // Verifica se o evento foi encontrado.
            evento.adicionarAssento(assento); // Adiciona o assento ao evento.
        } else {
            System.out.println("Esse evento não existe!");
        }
    }

    /**
     * Adiciona um ingresso a um evento.
     *
     * @param ingresso O ingresso a ser adicionado.
     */
    public void adicionarIngresso(Ingresso ingresso) {
        evento.adicionarIngresso(ingresso);
    }

    /**
     * Compra um ingresso para um evento.
     *
     * @param usuario O usuário que está comprando o ingresso.
     * @param pagamento O método de pagamento utilizado.
     * @param nomeDoEvento O nome do evento.
     * @param assento O assento do ingresso.
     * @return O ingresso comprado.
     */
    public Ingresso comprarIngresso(Usuario usuario, Pagamento pagamento, String nomeDoEvento, String assento) {
        evento = evento.buscarEventoPorNome(nomeDoEvento);
        ingresso = evento.venderIngresso(usuario, pagamento, evento, assento); // Cria um novo ingresso.
        return ingresso;
    }

    /**
     * Escolhe a forma de pagamento.
     *
     * @param pagamento O método de pagamento a ser escolhido.
     * @return A forma de pagamento escolhida.
     */
    public Pagamento escolheFormaPagamento(Pagamento pagamento) {
        return usuario.escolheFormaPagamento(pagamento);
    }

    /**
     * Processa o pagamento.
     *
     * @param formaPagamento A forma de pagamento a ser processada.
     * @return true se o pagamento for bem-sucedido, false caso contrário.
     */
    public boolean processarPagamento(Pagamento formaPagamento) {
        return formaPagamento.processarPagamento();
    }

    /**
     * Confirma a compra de um ingresso.
     *
     * @param usuario O usuário que fez a compra.
     * @param pagamento O método de pagamento utilizado.
     * @return A confirmação da compra.
     */
    public String confirmacaoDeCompra(Usuario usuario, Pagamento pagamento) {
        return usuario.getCompra().confirmarCompra(usuario, pagamento);
    }

    /**
     * Adiciona uma forma de pagamento ao usuário.
     *
     * @param pagamento A forma de pagamento a ser adicionada.
     */
    public void adicionarFormaPagamento (Pagamento pagamento) {
        usuario.adicionaFormaDePagamento(pagamento);
    }

    /**
     * Cancela a compra de um ingresso.
     *
     * @param usuario O usuário que deseja cancelar a compra.
     * @param ingresso O ingresso a ser cancelado.
     * @param pagamento O método de pagamento utilizado.
     * @return true se o cancelamento for bem-sucedido, false caso contrário.
     */
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso, Pagamento pagamento) {
        return usuario.cancelarIngressoComprado(ingresso, pagamento);
    }

    /**
     * Reembolsa o valor de um ingresso.
     *
     * @param usuario O usuário que solicitou o reembolso.
     * @param ingresso O ingresso a ser reembolsado.
     * @param pagamento O método de pagamento utilizado.
     * @return A confirmação do reembolso.
     */
    public String reembolsarValor(Usuario usuario, Ingresso ingresso, Pagamento pagamento) {
        compra = new Compra(ingresso);
        return pagamento.reembolsarPagamento(usuario, compra);
    }

    /**
     * Adiciona uma forma de pagamento ao usuário.
     *
     * @param usuario O usuário que receberá a forma de pagamento.
     * @param pagamento A forma de pagamento a ser adicionada.
     */
    public void adicionaFormaPagamento (Usuario usuario, Pagamento pagamento) {
        usuario.adicionaFormaDePagamento(pagamento);
    }

    /**
     * Lista as formas de pagamento disponíveis para um usuário.
     *
     * @param usuario O usuário cujas formas de pagamento serão listadas.
     * @return A lista de formas de pagamento do usuário.
     */
    public List<Pagamento> listarFormasDePagamento(Usuario usuario) {
        return usuario.getFormasDePagamento();
    }

    /**
     * Lista os eventos disponíveis no sistema.
     *
     * @return A lista de eventos cadastrados.
     */
    public List<Evento> listarEventosDisponiveis() {
        return evento.getEventosCadastrados();
    }

    /**
     * Lista os ingressos comprados do evento.
     *
     * @return A lista de ingressos comprados do evento.
     */
    public List<Ingresso> listarIngressosComprados() {
        return ingresso.getEvento().getIngressosComprados();
    }

    /**
     * Retorna os eventos cadastrados no sistema.
     *
     * @return A lista de eventos cadastrados.
     */
    public List<Evento> getEventosCadastrados() {
        return evento.getEventosCadastrados();
    }

    /**
     * Retorna os usuários cadastrados no sistema.
     *
     * @return A lista de usuários cadastrados.
     */
    public List<Usuario> getUsuariosCadastrados() {
        return Usuario.getUsuariosCadastrados();
    }
}
