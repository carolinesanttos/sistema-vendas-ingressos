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

import br.uefs.ecomp.vendaingressos.model.Excecao.JaCadastradoException;
import br.uefs.ecomp.vendaingressos.model.Excecao.UserNaoEncontradoException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    Usuario usuario;
    Evento evento;
    Ingresso ingresso;
    Compra compra;
    //private List<Evento> eventosCadastrados = new ArrayList<>();
    //private List<Ingresso> ingressosComprados = new ArrayList<>();

    // Cadastra um novo usuário. Recebe as informações do usuário, cria um objeto do tipo "Usuario" e cadastra.
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdm) {
        usuario = new Usuario(login, senha, nome, cpf, email, isAdm);
        usuario.cadastroDeUsuarios(usuario); // Cadastra usuário na lista de usuários.
        return usuario; // Retorna usuário criado.
    }

    public Usuario login (String login, String senha) {
        boolean logado = usuario.login(login, senha);  // Chama o método que pode lançar a exceção
        if (logado) {
            return usuario;  // Retorna o usuário se o login for bem-sucedido
        } else {
            throw new UserNaoEncontradoException("Usuário ou senha");  // Lança exceção se o login falhar
        }
    }

    public void alterarNome (String nome) {
        usuario.setNome(nome);
    }
    public void alterarEmail (String email) {
        usuario.setEmail(email);
    }

    public void alterarSenha (String senha) {
        usuario.setSenha(senha);
    }

    // Cadastra um novo evento. Verifica se usuário tem permissão de administrador antes de cadastrar.
    public Evento cadastrarEvento(Usuario usuario, String nomeDoEvento, String descricao, Date data) throws SecurityException{
        if (!usuario.isLogado()) { // Verifica se usuário está logado
            throw new SecurityException("Precisa estar logado para cadastrar um evento.");
        }
        if (!(usuario.isAdmin())) { // Verifica se usuário é um administrador
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        // Se as condições forem atendidas, o evento é cadastrado
        evento = new Evento(usuario, nomeDoEvento, descricao, data); // Cria um novo evento com as informações dadas.
        evento.cadastroDeEventos(evento); // Supondo que esse método já trata de cadastro de eventos.
        //eventosCadastrados.add(evento); // Adiciona evento à lista de eventos cadastrados.

        return evento; // Retorna o evento criado.

    }

    // Adiciona assento disponível a um evento específico. Procura o evento pelo nome e adiciona
    // o assento à lista de assentos disponíveis.
    public void adicionarAssentoEvento(String nomeDoEvento, String assento) {
        evento = evento.encontrarEventoPorNome(nomeDoEvento); // Encontra o evento pelo nome.
        if (evento!= null) { // Verifica se o evento foi encontrado.
            evento.adicionarAssento(assento); // Adiciona o assento ao evento.
        } else {
            System.out.println("Esse evento não existe!");
        }
    }

    public void adicionarIngresso(Ingresso ingresso) {
        evento.adicionarIngresso(ingresso);
    }

    // Comprar ingresso para um evento. Cria novo ingresso associando usuário, evento e assento.
    public Ingresso comprarIngresso(Usuario usuario, Pagamento pagamento, String nomeDoEvento, String assento) {
        evento = evento.encontrarEventoPorNome(nomeDoEvento);
        ingresso = evento.venderIngresso(usuario, pagamento, evento, assento); // Cria um novo ingresso.
        ingressosComprados.add(ingresso); // Adiciona ingresso à lista de ingressos comprados.
        return ingresso;
    }

    public Pagamento escolheFormaPagamento(Pagamento pagamento) {
        return usuario.escolheFormaPagamento(pagamento);
    }

    public String processarPagamento(Pagamento formaPagamento, double valor) {
        return formaPagamento.processarPagamento(valor);
    }

    public String confirmacaoDeCompra(Usuario usuario, Pagamento pagamento) {
        return usuario.getCompra().confirmarCompra(usuario, pagamento);
    }

    public void adicionarFormaPagamento (Pagamento pagamento) {
        usuario.adicionaFormaDePagamento(pagamento);
    }

    // Cancela a compra de um ingresso.
    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso, Pagamento pagamento) {
        return usuario.cancelarIngressoComprado(ingresso, pagamento);
    }

    public String reembolsarValor(Ingresso ingresso, Pagamento pagamento) {
        compra = new Compra(ingresso);
        return pagamento.reembolsarPagamento(compra);
    }

    public void adicionaFormaPagamento (Usuario usuario, Pagamento pagamento) {
        usuario.adicionaFormaDePagamento(pagamento);
    }

    public List<Pagamento> listarFormasDePagamento(Usuario usuario) {
        return usuario.getFormasDePagamento();
    }

    public List<Evento> listarEventosDisponiveis() {
        return getEventosCadastrados();
    }

    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return getIngressosComprados();
    }

    public List<Evento> getEventosCadastrados() {
        return eventosCadastrados;
    }

    public List<Ingresso> getIngressosComprados() {
        return ingressosComprados;
    }

    public static void limparUsuariosCadastrados() {
        Usuario.limparUsuariosCadastrados();
    }
}
