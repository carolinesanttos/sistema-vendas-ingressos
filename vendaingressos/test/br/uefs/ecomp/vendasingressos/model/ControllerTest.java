package br.uefs.ecomp.vendasingressos.model;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import br.uefs.ecomp.vendaingressos.model.*;
import br.uefs.ecomp.vendaingressos.model.excecao.*;
import br.uefs.ecomp.vendaingressos.model.persistencia.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


public class ControllerTest {

    Controller controller;

    /**
     * Método executado antes de cada teste.
     * Inicializa um novo Controller e limpa dados cadastrados.
     */
    @Before
    public void setUp() {
        controller = new Controller(); // Inicializa um novo Controller para os testes
        Usuario.limparUsuariosCadastrados(); // Limpa os usuários cadastrados antes de cada teste
        Evento.limparEventosCadastrados(); // Limpa os eventos cadastrados antes de cada teste
    }


    /**
     * Testa o cadastro de um evento por um administrador.
     * Verifica se o evento é criado corretamente com os dados fornecidos.
     */
    @Test
    public void testCadastrarEventoPorAdmin() {
        // Cria um usuário administrador e faz login
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        // Define a data do evento
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        // Cadastra um novo evento
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

    /**
     * Testa a tentativa de um usuário comum cadastrar um evento.
     * Deve lançar uma exceção informando que apenas administradores podem cadastrar eventos.
     */
    @Test
    public void testCadastrarEventoPorUsuarioComum() {
        // Cria um usuário comum e faz login
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        // Define a data do evento
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        // Verifica se uma exceção é lançada ao tentar cadastrar um evento
        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

    /**
     * Testa a compra de um ingresso.
     * Verifica se a compra é realizada corretamente e se o ingresso é associado ao usuário.
     */
    @Test
    public void testComprarIngresso() {
        // Cria um usuário administrador e faz login
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        // Define a data do evento
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        // Cadastra um novo evento
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1"); // Adiciona um assento ao evento
        controller.gerarIngresso(admin, evento, assento); // Gera um ingresso para o evento

        // Cria um usuário comum e faz login
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        // Cria uma forma de pagamento e a adiciona ao usuário
        Pagamento pagamento = new Pagamento("7891234567890");
        controller.adicionarFormaPagamento( usuario, pagamento);

        // O usuário escolhe uma forma de pagamento
        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(usuario, pagamento);

        assertNotNull(pagamentoEscolhido);  // Verifica se a forma de pagamento foi encontrada

        // O usuário compra um ingresso
        Ingresso ingresso = controller.comprarIngresso(usuario, pagamentoEscolhido, "Show de Rock", "A1");

        assertNotNull(ingresso);
        assertEquals("Show de Rock", ingresso.getEvento().getNome());
        assertEquals("A1", ingresso.getAssento());
        assertTrue(usuario.getIngressos().contains(ingresso));
    }

    /**
     * Testa o cancelamento de uma compra.
     * Verifica se a compra é cancelada corretamente e se o ingresso é removido da lista do usuário.
     */
    @Test
    public void testCancelarCompra() {
        // Cria um usuário administrador e faz login
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        // Define a data do evento
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        // Cadastra um novo evento
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1"); // Adiciona um assento ao evento
        controller.gerarIngresso(admin, evento, assento); // Gera um ingresso para o evento

        // Cria um usuário comum e faz login
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        // Cria uma forma de pagamento e a adiciona ao usuário
        Pagamento pagamento = new Pagamento("7891234567890");
        controller.adicionarFormaPagamento( usuario, pagamento);

        // O usuário escolhe uma forma de pagamento
        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(usuario, pagamento);

        // O usuário compra um ingresso
        Ingresso ingresso = controller.comprarIngresso(usuario, pagamentoEscolhido, "Show de Rock", "A1");

        // Cancela a compra do ingresso
        boolean cancelado = controller.cancelarCompra(usuario, ingresso);

        assertTrue(cancelado);
        assertFalse(ingresso.isAtivo());
        assertFalse(usuario.getIngressos().contains(ingresso));
    }

    /**
     * Testa a listagem de eventos disponíveis.
     * Verifica se a lista de eventos cadastrados está correta.
     */
    @Test
    public void testListarEventosDisponiveis() {
        // Cria um usuário administrador e faz login
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        // Define duas datas para os eventos
        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.DECEMBER, 25);
        Date data1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2025, Calendar.JANUARY, 1);
        Date data2 = calendar2.getTime();

        // Cadastra dois eventos
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data1);
        Evento evento2 = controller.cadastrarEvento(admin, "Peça de Teatro", "Grupo ABC", data2);

        // Obtém a lista de eventos disponíveis
        List<Evento> eventos = List.of(new Evento[]{evento, evento2});
        controller.listarEventosDisponiveis();

        assertEquals(2, eventos.size());
    }

    /**
     * Testa a listagem de ingressos comprados.
     * Verifica se a lista de ingressos do usuário está correta.
     */
    @Test
    public void testListarIngressosComprados() {
        // Cria um usuário administrador e faz login
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        // Define a data do evento
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        // Cadastra um novo evento
        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1");
        controller.gerarIngresso(admin, evento, assento);

        // Cria um usuário comum e faz login
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        // Cria uma forma de pagamento e a adiciona ao usuário
        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        // O usuário compra um ingresso
        controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        // Obtém a lista de ingressos comprados
        List<Ingresso> ingressos = controller.listarIngressosComprados();

        assertEquals(1, ingressos.size());
    }

    // NOVOS TESTES

    /**
     * Testa a edição dos dados pessoais de um usuário.
     * Este método realiza o cadastro de um usuário, efetua o login e altera o nome,
     * e-mail e senha do usuário. Em seguida, verifica se os dados foram atualizados
     * corretamente.
     */
    @Test
    public void testEditarDadosPessoais() {

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        controller.alterarNome(usuario, "Caroline de Jesus");
        controller.alterarEmail(usuario, "ca.dejesus@example.com");
        controller.alterarSenha(usuario, "carol12345");

        assertEquals("Caroline de Jesus", usuario.getNome());
        assertEquals("ca.dejesus@example.com", usuario.getEmail());
        assertEquals("carol12345", usuario.getSenha());
    }

    /**
     * Testa a adição de formas de pagamento para um usuário.
     * Este método cadastra um novo usuário, faz login e adiciona duas formas de pagamento.
     * Em seguida, verifica se as formas de pagamento foram adicionadas corretamente,
     * confirmando que o número de formas de pagamento na lista é igual a 2.
     */
    @Test
    public void testAdicionarFormasPagamento() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Pagamento formaPagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        Pagamento formaPagamento2 = new Pagamento("12345678901234567890");

        controller.adicionarFormaPagamento(usuario, formaPagamento);
        controller.adicionarFormaPagamento(usuario, formaPagamento2);

        List<Pagamento> formasDePagamento = controller.listarFormasDePagamento(usuario);

        assertEquals(2, formasDePagamento.size());
    }

    /**
     * Testa a confirmação de compra de um ingresso.
     * Este método cadastra um evento e um usuário, e simula a compra de um ingresso.
     * Após a compra, verifica se o ingresso não é nulo e se a mensagem de confirmação
     * da compra está correta, de acordo com os detalhes da compra.
     */
    @Test
    public void testConfirmacaoDeCompra() {

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1");
        controller.gerarIngresso(admin, evento, assento);

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "Carol Santos", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        assertNotNull(ingresso);

        String mensagemEsperada = controller.confirmacaoDeCompra(usuario, pagamento);

        assertEquals("Destinatário: ca.sant@example.com\nAssunto: Confirmação de Compra\n\n" +
                "Olá, Carol Santos,\n\n" + "Obrigado por sua compra! Aqui estão os detalhes da sua compra:\n\n" +
                "Produto: Show de Rock - Assento: A1\n" + "Valor: R$ 100.0\n" +
                "Método de pagamento: Cartão\n\n" + "Sua compra foi processada com sucesso. Caso tenha dúvidas, " +
                "entre em contato com nosso suporte.\n\n" + "Atenciosamente,\nEquipe de Vendas", mensagemEsperada);
    }

    /**
     * Testa a avaliação de um evento por usuários.
     * Este método cadastra um evento e dois usuários. Cada usuário fornece feedback
     * sobre o evento, que inclui uma nota e um comentário. O teste verifica se os
     * dados do feedback estão corretos, incluindo o usuário, evento, nota e comentário.
     */
    @Test
    public void testAvaliacao() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 17);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        Usuario usuario2 = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");


        Feedback feedback = controller.darFeedback(usuario, evento, 1, "Infelizmente, o evento foi uma grande decepção.");
        Feedback feedback2 = controller.darFeedback(usuario2, evento, 5, "O evento foi excelente!");

        assertEquals(usuario, feedback.getUsuario());
        assertEquals(usuario2, feedback2.getUsuario());

        assertEquals(evento, feedback.getEvento());
        assertEquals(evento, feedback2.getEvento());

        assertEquals(1, feedback.getNota());
        assertEquals(5, feedback2.getNota());

        assertEquals("Infelizmente, o evento foi uma grande decepção.", feedback.getComentario());
        assertEquals("O evento foi excelente!", feedback2.getComentario());
    }

    // Testes em relação a persistência de dados

    /**
     * Testa a persistência de dados ao salvar e carregar eventos.
     *
     * Neste teste, um administrador é cadastrado e um evento é criado.
     * Vários assentos são adicionados e ingressos são gerados.
     * Um usuário comum é cadastrado e um método de pagamento é adicionado.
     * Em seguida, os dados dos eventos são salvos em um arquivo JSON e
     * carregados de volta, verificando se os eventos foram salvos e
     * carregados corretamente.
     */
    @Test
    public void testPersistenciaSalvarDados() {

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Festival de Música", "Bandas Diversas", data);

        String assento = controller.adicionarAssento(admin,"Festival de Música", "A1");
        controller.adicionarAssento(admin,"Festival de Música", "A2");
        controller.adicionarAssento(admin,"Festival de Música", "A3");

        controller.gerarIngresso(admin, evento, assento);
        controller.gerarIngresso(admin, evento, "A2");
        controller.gerarIngresso(admin, evento, "A3");

        Usuario usuario = controller.cadastrarUsuario("mariazinha", "segura123", "Maria Costa", "98765432100", "maria.costa@example.com", false);
        controller.login("mariazinha", "segura123");

        Pagamento pagamento = new Pagamento("Maria Costa","7589 7418 8529 9637", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(usuario, pagamento);

        controller.comprarIngresso(usuario, pagamentoEscolhido, "Festival de Música", "A1");

        // Instanciando a classe de persistência com o caminho do arquivo
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");

        // Obtendo a lista de eventos para salvar
        List<Evento> eventosAtivos = controller.getEventosCadastrados();

        // Salvando os eventos em um arquivo JSON
        persistencia.salvarDados(eventosAtivos);

        // Carregando os dados de volta do arquivo
        List<Evento> eventosCarregados = persistencia.carregarDados();

        assertNotNull(eventosCarregados);
        assertEquals(1, eventosCarregados.size());
        assertEquals("Festival de Música", eventosCarregados.getFirst().getNome());
        assertEquals("Bandas Diversas", eventosCarregados.getFirst().getDescricao());
    }

    /**
     * Testa a carga de eventos a partir do arquivo JSON.
     *
     * Este teste verifica se os eventos carregados do arquivo JSON
     * estão corretos em relação ao que foi salvo anteriormente.
     */
    @Test
    public void testPersistenciaCarregarEventos() {
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");
        List<Evento> detalheEventos = persistencia.carregarDados();

        assertEquals(1, detalheEventos.size());  // Verifica se dois eventos foram carregados

        Evento evento = detalheEventos.getFirst();
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("Bandas Diversas", evento.getDescricao());
    }

    /**
     * Testa a disponibilidade e reserva de assentos.
     *
     * Este teste verifica se os assentos disponíveis e reservados estão
     * corretos após a carga dos eventos do arquivo JSON.
     */
    @Test
    public void testPersistenciaAssentoDisponivelEReservado() {
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");

        List<Evento> detalheEventos = persistencia.carregarDados();

        Evento evento = detalheEventos.getFirst();
        assertEquals(2, evento.getAssentosDisponiveis().size());
        assertTrue(evento.getAssentosDisponiveis().contains("A2"));
        assertTrue(evento.getAssentosDisponiveis().contains("A3"));

        assertEquals(1, evento.getAssentosReservados().size());
        assertTrue(evento.getAssentosReservados().contains("A1"));
    }

    /**
     * Testa a persistência de ingressos disponíveis e reservados.
     *
     * Este teste verifica se os ingressos disponíveis para o evento
     * Festival de Música foram carregados corretamente a partir
     * do arquivo JSON. Ele valida se o evento foi carregado com o
     * nome correto, a quantidade de ingressos disponíveis e as
     * informações dos ingressos, como assento e preço.
     */
    @Test
    public void testPersistenciaIngressosDisponivelReservado() {
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");

        List<Evento> detalheEventos = persistencia.carregarDados();

        // Verificando se o evento Festival de Música foi carregado corretamente
        Evento evento = detalheEventos.getFirst();
        assertEquals("Festival de Música", evento.getNome());
        assertEquals(2, evento.getIngressosDisponiveis().size());

        // Verificando os ingressos disponíveis para o Festival de Música
        Ingresso ingresso = evento.getIngressosDisponiveis().get(0);
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("A2", ingresso.getAssento());
        assertEquals(100.0, ingresso.getPreco(), 0.01);

        Ingresso ingresso2 = evento.getIngressosDisponiveis().get(1);
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("A3", ingresso2.getAssento());
        assertEquals(100.0, ingresso.getPreco(), 0.01);

        assertEquals(1, detalheEventos.size()); // Deve ter apenas 1 evento
        assertEquals("Festival de Música", detalheEventos.getFirst().getNome());

    }

    /**
     * Testa a persistência de dados dos usuários.
     *
     * Neste teste, usuários são cadastrados e métodos de pagamento
     * são adicionados. Os dados dos usuários são salvos em um arquivo JSON
     * e verificados se foram carregados corretamente.
     */
    @Test
    public void testPersistenciaUsuarios() {
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Festival de Música", "Bandas Diversas", data);
        String assento = controller.adicionarAssento(admin,"Festival de Música", "A1");
        String assento2 = controller.adicionarAssento(admin,"Festival de Música", "A2");
        controller.gerarIngresso(admin, evento, assento);
        controller.gerarIngresso(admin, evento, assento2);

        Usuario usuario = controller.cadastrarUsuario("mariazinha", "segura123", "Maria Costa", "98765432100", "maria.costa@example.com", false);
        controller.login("mariazinha", "segura123");

        Pagamento pagamento = new Pagamento("Maria Costa","7589 7418 8529 9637", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(usuario, pagamento);

        Usuario usuario2 = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        Pagamento pagamento2 = new Pagamento("Carol Santos","8529 7418 9634 4568", "05/35", "356");
        controller.adicionarFormaPagamento(usuario2, pagamento2);

        Pagamento pagamentoEscolhido2 = controller.escolheFormaPagamento(usuario2, pagamento2);

        assertNotNull(pagamentoEscolhido);
        assertNotNull(pagamentoEscolhido2);

        controller.comprarIngresso(usuario, pagamentoEscolhido, "Festival de Música", "A1");
        controller.comprarIngresso(usuario2, pagamentoEscolhido2, "Festival de Música", "A2");

        // Persistindo os usuários em JSON
        PersistenciaUsuarios persistenciaUsuarios = new PersistenciaUsuarios("usuarios.json");

        List<Usuario> usuariosCadastrados = controller.getUsuariosCadastrados();

        persistenciaUsuarios.salvarDados(usuariosCadastrados);  // Salvando os dados dos usuários

        // Carregando os dados de volta do arquivo
        List<Usuario> usuarios = persistenciaUsuarios.carregarDados();

        assertEquals(3, usuarios.size());

        // Verificações do usuário administrador
        Usuario u1 = usuarios.getFirst();
        assertEquals("admin", u1.getLogin());
        assertTrue(u1.isAdmin());

        // Verificações do usuário 1
        Usuario u2 = usuarios.get(1);
        assertEquals("mariazinha", u2.getLogin());
        assertEquals("Cartão", u2.getFormasDePagamento().getFirst().getFormaDePagamento());
        assertEquals(1, u2.getIngressosComprados().size());

        // Verificações do usuário 2
        Usuario u3 = usuarios.get(2);
        assertEquals("carolsan", u3.getLogin());
        assertEquals("Cartão", u3.getFormasDePagamento().getFirst().getFormaDePagamento());
        assertEquals(1, u3.getIngressosComprados().size());
    }

    // Testando algumas das exceções

    /**
     * Testa a exceção para quando um usuário tenta realizar uma ação sem estar logado.
     *
     * Este teste verifica se a exceção correta é lançada quando um
     * usuário não autenticado tenta adicionar uma forma de pagamento.
     */
    @Test
    public void testNaoLogadoException () {
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Festival de Música", "Bandas Diversas", data);
        String assento = controller.adicionarAssento(admin,"Festival de Música", "A1");
        controller.gerarIngresso(admin, evento, assento);


        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);

        Pagamento pagamento = new Pagamento("Carol Santos","8529 7418 9634 4568", "05/35", "356");

        NaoLogadoException exception = assertThrows(NaoLogadoException.class, () -> {
            controller.adicionarFormaPagamento(usuario, pagamento);
        });

        assertEquals("É necessário estar logado para realizar essa ação.", exception.getMessage());
    }

    /**
     * Testa a exceção para quando um evento não é encontrado.
     *
     * Este teste verifica se a exceção correta é lançada quando um
     * usuário tenta comprar um ingresso para um evento que não existe.
     */
    @Test
    public void testEventoNaoEncontradoException () {
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1");
        controller.gerarIngresso(admin, evento, assento);

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Pagamento pagamento = new Pagamento("7891234567890");
        controller.adicionarFormaPagamento( usuario, pagamento);
        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(usuario, pagamento);

        assertNotNull(pagamentoEscolhido);

        NaoEncontradoException exception = assertThrows(NaoEncontradoException.class, () -> {
            controller.comprarIngresso(usuario, pagamentoEscolhido, "Festival de Música", "A1");
        });

        assertEquals("Evento não encontrado.", exception.getMessage());
    }

    /**
     * Testa a exceção para quando um assento já está cadastrado.
     *
     * Este teste verifica se a exceção correta é lançada quando um
     * administrador tenta adicionar um assento que já foi cadastrado.
     */
    @Test
    public void testAssentoJaCadastradoException () {
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();


        controller.cadastrarEvento(admin, "Festival de Música", "Bandas Diversas", data);
        controller.adicionarAssento(admin,"Festival de Música", "A1");

        CadastroException exception = assertThrows(CadastroException.class, () -> {
            controller.adicionarAssento(admin,"Festival de Música", "A1");
        });

        assertEquals("Assento já adicionado.", exception.getMessage());
    }

    /**
     * Testa a exceção para quando uma forma de pagamento é inválida.
     *
     * Este teste verifica se a exceção correta é lançada quando um
     * usuário tenta alterar uma forma de pagamento para uma opção inválida.
     */
    @Test
    public void testFormaDePagamentoInvalidaException () {
        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        Pagamento pagamento = new Pagamento("852974157485952474");
        controller.adicionarFormaPagamento( usuario, pagamento);

        FormaDePagamentoInvalidaException exception = assertThrows(FormaDePagamentoInvalidaException.class, () -> {
            controller.alterarFormaDePagamento(pagamento, "Pix");
        });

        assertEquals("Forma de pagamento inválida.", exception.getMessage());
    }

    /**
     * Testa a exceção para feedback antes da realização do evento.
     *
     * Este teste verifica se a exceção correta é lançada quando um
     * usuário tenta avaliar um evento que ainda não ocorreu.
     */
    @Test
    public void testFeedbackException () {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        EventoAtivoException exception = assertThrows(EventoAtivoException.class, () -> {
            controller.darFeedback(usuario, evento, 5, "O evento foi excelente!");
        });

        assertEquals("Só é possível avaliar após o evento.", exception.getMessage());
    }

    /**
     * Testa a exceção para quando uma compra é cancelada.
     *
     * Este teste verifica se a exceção correta é lançada ao cancelar
     * uma compra após a realização.
     */
    @Test
    public void testCompraCanceladaException () {
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        String assento = controller.adicionarAssento(admin,"Show de Rock", "A1");
        controller.gerarIngresso(admin, evento, assento);

        Usuario usuario = controller.cadastrarUsuario("mariazinha", "segura123", "Maria Costa", "98765432100", "maria.costa@example.com", false);
        controller.login("mariazinha", "segura123");

        Pagamento pagamento = new Pagamento("Maria Costa","7589 7418 8529 9637", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        assertNotNull(pagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        controller.cancelarCompra(usuario, ingresso);
        
        ReembolsoException exception = assertThrows(ReembolsoException.class, () -> {
            controller.cancelarCompra(usuario, ingresso);
        });

        assertEquals("A compra já foi cancelada anteriormente, e o reembolso já foi processado.", exception.getMessage());

    }


}
