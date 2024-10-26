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
import java.util.List;

public class Controller {

    Usuario usuario;
    Evento evento;
    Ingresso ingresso;
    Compra compra;

    /**
     * Cadastra um novo usuário no sistema.
     *
     * @param login login do usuário.
     * @param senha senha do usuário.
     * @param nome nome completo do usuário.
     * @param cpf CPF do usuário.
     * @param email email do usuário.
     * @param isAdm indica se o usuário é um administrador.
     * @return usuário criado.
     */
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdm) {
        usuario = new Usuario(login, senha, nome, cpf, email, isAdm);
        usuario.cadastroDeUsuarios(usuario); // Cadastra usuário na lista de usuários.
        return usuario; // Retorna usuário criado.
    }

    /**
     * Realiza login do usuário.
     *
     * @param login login do usuário.
     * @param senha senha do usuário.
     * @return usuário logado.
     * @throws NaoEncontradoException se o login ou a senha estiverem incorretos.
     */
    public Usuario login (String login, String senha) {
        boolean cadastrado = usuario.isCasdastrado(login, senha);
        if (cadastrado) {
            usuario.login(login, senha);
            return usuario;
        } else {
            throw new NaoEncontradoException("Usuário não encontrado."); // Usuário não está cadastrado
        }
    }

    /**
     * Altera o nome do usuário.
     *
     * @param nome novo nome do usuário.
     * @throws NaoLogadoException se o usuário não estiver logado.
     */
    public void alterarNome (Usuario usuario, String nome) {
        if (usuario.isLogado()) {
            usuario.setNome(nome);
        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }

    }

    /**
     * Altera o email do usuário.
     *
     * @param email novo email do usuário.
     * @throws NaoLogadoException se o usuário não estiver logado
     */
    public void alterarEmail (Usuario usuario, String email) {
        if (usuario.isLogado()) {
            usuario.setEmail(email);
        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Altera a senha do usuário.
     *
     * @param senha nova senha do usuário.
     * @throws NaoLogadoException se o usuário não estiver logado
     */
    public void alterarSenha (Usuario usuario, String senha) {
        if (usuario.isLogado()) {
            usuario.setSenha(senha);
        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Cadastra novo evento no sistema.
     *
     * @param usuario usuário que está cadastrando o evento.
     * @param nomeDoEvento nome do evento a ser cadastrado.
     * @param descricao descrição do evento.
     * @param data data do evento.
     * @return evento criado.
     * @throws SecurityException se o usuário não estiver logado ou não for administrador.
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
        evento.cadastroDeEventos(evento);

        return evento; // Retorna o evento criado.
    }

    /**
     * Adiciona assento disponível a um evento específico.
     *
     * @param nomeDoEvento nome do evento.
     * @param assento assento a ser adicionado.
     * @throws NaoEncontradoException se evento não for encontrado.
     */
    public String adicionarAssento(Usuario user, String nomeDoEvento, String assento) {
        evento = evento.buscarEventoPorNome(nomeDoEvento); // Encontra o evento pelo nome
        if (evento!= null && user.isAdmin()) { // Verifica se o evento foi encontrado
            evento.adicionarAssento(assento); // Adiciona o assento ao evento
            return assento;
        } else {
            throw new NaoEncontradoException("Esse evento não existe!");
        }
    }

    /**
     * Adiciona ingresso a um evento.
     *
     * @param evento ingresso a ser adicionado.
     * @param assento assento a ser adicionado.
     * @throws SomenteAdminException se o usuário não for um administrador.
     */
    public void gerarIngresso(Usuario user, Evento evento, String assento) {
        if (user.isAdmin()) {
            evento.adicionarIngresso(new Ingresso(evento, assento));
        } else {
            throw new SomenteAdminException("Somente administradores podem gerar ingressos para eventos.");
        }
    }

    /**
     * Compra ingresso para um evento.
     *
     * @param usuario usuário que está comprando o ingresso.
     * @param pagamento método de pagamento utilizado.
     * @param nomeDoEvento nome do evento.
     * @param assento assento do ingresso.
     * @return ingresso comprado.
     * @throws FormaDePagamentoInvalidaException se não houver uma forma de pagamento válida.
     * @throws NaoLogadoException se o usuário não estiver logado.
     */
    public Ingresso comprarIngresso(Usuario usuario, Pagamento pagamento, String nomeDoEvento, String assento) {
        if (pagamento == null) {
            throw new FormaDePagamentoInvalidaException("É necessário adicionar uma forma de pagamento.");
        }
        if (!usuario.isLogado()) {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
        evento = evento.buscarEventoPorNome(nomeDoEvento);
        ingresso = evento.comprarIngresso(usuario, pagamento, evento, assento); // Cria um novo ingresso.
        return ingresso;
    }

    /**
     * Cancela compra de um ingresso.
     *
     * @param usuario usuário que deseja cancelar a compra.
     * @param ingresso ingresso a ser cancelado.
     * @return true se o cancelamento for bem-sucedido, false caso contrário.
     * @throws NaoLogadoException Se o usuário não estiver logado ao tentar cancelar a compra.
     */
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        if (usuario.isLogado()) {
            return usuario.cancelarIngressoComprado(usuario, ingresso);
        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Escolhe forma de pagamento.
     *
     * @param usuario o usuário que está escolhendo a forma de pagamento.
     * @param pagamento método de pagamento a ser escolhido.
     * @return forma de pagamento escolhida.
     * @throws NaoLogadoException Se o usuário não estiver logado ao tentar escolher a forma de pagamento.
     */
    public Pagamento escolheFormaPagamento(Usuario usuario, Pagamento pagamento) {

        if (usuario.isLogado()) {
            return usuario.escolheFormaPagamento(pagamento);
        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Adiciona uma forma de pagamento ao usuário.
     *
     * @param usuario o usuário que está adicionando a forma de pagamento
     * @param pagamento forma de pagamento a ser adicionada.
     * @throws NaoLogadoException Se o usuário não estiver logado ao tentar adicionar uma forma de pagamento.
     */
    public void adicionarFormaPagamento (Usuario usuario, Pagamento pagamento) {
        if (usuario.isLogado()) {
            usuario.adicionaFormaDePagamento(pagamento);;  // Adiciona o pagamento à lista

        } else {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
        }
    }

    /**
     * Confirma a compra de um ingresso.
     *
     * @param usuario usuário que fez a compra.
     * @param pagamento método de pagamento utilizado.
     * @return confirmação da compra.
     */
    public String confirmacaoDeCompra(Usuario usuario, Pagamento pagamento) {
        return usuario.getCompra().confirmarCompra(usuario, pagamento);
    }


    /**
     * Permite usuário fornecer feedback sobre um evento.
     *
     * @param usuario usuário que está dando o feedback.
     * @param evento evento ao qual o feedback se refere.
     * @param nota nota dada pelo usuário ao evento.
     * @param mensagem mensagem adicional do usuário sobre o evento.
     * @return objeto feedback criado com as informações passadas.
     * @throws NaoLogadoException se o usuário não estiver logado ao tentar dar feedback.
     * @throws EventoAtivoException se o evento ainda estiver ativo, impedindo a avaliação.
     */
    public Feedback darFeedback(Usuario usuario, Evento evento, int nota, String mensagem) {
        if (!usuario.isLogado()) {
            throw new NaoLogadoException("É necessário estar logado para realizar essa ação.");
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
     * @param usuario usuário que solicitou o reembolso.
     * @param ingresso ingresso a ser reembolsado.
     * @param pagamento método de pagamento utilizado.
     * @return confirmação do reembolso.
     */
    public String reembolsarValor(Usuario usuario, Ingresso ingresso, Pagamento pagamento) {
        compra = new Compra(ingresso);
        return pagamento.reembolsarPagamento(usuario, compra);
    }

    /**
     * Lista as formas de pagamento disponíveis para usuário.
     *
     * @param usuario formas de pagamento do usuário.
     * @return lista de formas de pagamento do usuário.
     */
    public List<Pagamento> listarFormasDePagamento(Usuario usuario) {
        return usuario.getFormasDePagamento();
    }

    /**
     * Lista os eventos disponíveis no sistema.
     *
     * @return lista de eventos cadastrados.
     */
    public List<Evento> listarEventosDisponiveis() {
        return evento.getEventosCadastrados();
    }

    /**
     * Lista os ingressos comprados do evento.
     *
     * @return lista de ingressos comprados do evento.
     */
    public List<Ingresso> listarIngressosComprados() {
        return ingresso.getEvento().getIngressosComprados();
    }


    /**
     * Retorna os eventos cadastrados no sistema.
     *
     * @return lista de eventos cadastrados.
     */
    public List<Evento> getEventosCadastrados() {
        return evento.getEventosCadastrados();
    }

    /**
     * Retorna os usuários cadastrados no sistema.
     *
     * @return lista de usuários cadastrados.
     */
    public List<Usuario> getUsuariosCadastrados() {
        return Usuario.getUsuariosCadastrados();
    }

    /**
     * Altera a forma de pagamento associada a um pagamento existente.
     *
     * @param pagamento o objeto de pagamento cuja forma de pagamento será alterada
     * @param formaDePagamento a nova forma de pagamento a ser definida
     *
     * @throws IllegalArgumentException se a forma de pagamento fornecida for inválida ou nula
     */
    public void alterarFormaDePagamento(Pagamento pagamento, String formaDePagamento) {
        pagamento.setFormaDePagamento(formaDePagamento);
    }
}
