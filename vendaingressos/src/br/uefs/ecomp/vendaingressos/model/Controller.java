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

import br.uefs.ecomp.vendaingressos.model.excecao.*;

import java.util.Date;
import java.util.HashMap;
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
    public void alterarNome (Usuario usuario, String nome) {
        if (usuario.isLogado()) {
            usuario.setNome(nome);
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }

    }

    /**
     * Altera o email do usuário.
     *
     * @param email O novo email do usuário.
     */
    public void alterarEmail (Usuario usuario, String email) {
        if (usuario.isLogado()) {
            usuario.setEmail(email);
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Altera a senha do usuário.
     *
     * @param senha A nova senha do usuário.
     */
    public void alterarSenha (Usuario usuario, String senha) {
        if (usuario.isLogado()) {
            usuario.setSenha(senha);
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
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
    public String adicionarAssento(Usuario user, String nomeDoEvento, String assento) {
        evento = evento.buscarEventoPorNome(nomeDoEvento); // Encontra o evento pelo nome.
        if (evento!= null && user.isAdmin()) { // Verifica se o evento foi encontrado.
            evento.adicionarAssento(assento); // Adiciona o assento ao evento.
            return assento;
        } else {
            throw new NaoEncontradoException("Esse evento não existe!");
        }
    }

    /**
     * Adiciona um ingresso a um evento.
     *
     * @param evento O ingresso a ser adicionado.
     * @param assento O assento a ser adicionado.
     */
    public void gerarIngresso(Usuario user, Evento evento, String assento) {
        if (user.isAdmin()) {
            evento.adicionarIngresso(new Ingresso(evento, assento));
        } else {
            throw new SomenteAdminException("Somente administradores podem gerar ingressos para eventos.");
        }
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
        if (pagamento == null) {
            throw new FormaDePagamentoInvalidaException("É necessário adicionar uma forma de pagamento.");
        }
        if (!usuario.isLogado()) {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
        evento = evento.buscarEventoPorNome(nomeDoEvento);
        ingresso = evento.comprarIngresso(usuario, pagamento, evento, assento); // Cria um novo ingresso.
        return ingresso;
    }

    /**
     * Cancela a compra de um ingresso.
     *
     * @param usuario O usuário que deseja cancelar a compra.
     * @param ingresso O ingresso a ser cancelado.
     * @return true se o cancelamento for bem-sucedido, false caso contrário.
     */
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        if (usuario.isLogado()) {
            return usuario.cancelarIngressoComprado(usuario, ingresso);
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Escolhe a forma de pagamento.
     *
     * @param pagamento O método de pagamento a ser escolhido.
     * @return A forma de pagamento escolhida.
     */
    public Pagamento escolheFormaPagamento(Usuario usuario, Pagamento pagamento) {

        if (usuario.isLogado()) {
            return usuario.escolheFormaPagamento(pagamento);
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Adiciona uma forma de pagamento ao usuário.
     *
     * @param pagamento A forma de pagamento a ser adicionada.
     */
    public void adicionarFormaPagamento (Usuario usuario, Pagamento pagamento) {
        if (usuario.isLogado()) {
            usuario.adicionaFormaDePagamento(pagamento);;  // Adiciona o pagamento à lista

        } else {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
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

    public Feedback darFeedback(Usuario usuario, Evento evento, int nota, String mensagem) {
        if (!usuario.isLogado()) {
            throw new UserNaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
        if (!evento.isAtivo()) {
            Feedback feedback = new Feedback(usuario, evento, nota, mensagem);
            evento.adicionarFeedbacks(feedback);
            return feedback;
        }
        throw new EventoAtivoException("Só é possível avaliar após o evento.");
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
