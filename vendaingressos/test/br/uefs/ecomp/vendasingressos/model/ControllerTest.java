package br.uefs.ecomp.vendasingressos.model;

import java.util.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import br.uefs.ecomp.vendaingressos.model.*;
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

    @Before
    public void setUp() {
        controller = new Controller();
        Usuario.limparUsuariosCadastrados(); // Limpar usuários cadastrados usando método estático
        Evento.limparEventosCadastrados(); // Limpar eventos cadastrados usando método estático
    }

    // Modificado
    @Test
    public void testCadastrarEventoPorAdmin() {

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

    // Modificado
    @Test
    public void testCadastrarEventoPorUsuarioComum() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.DECEMBER, 30);
        Date data = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

    // Modificado
    @Test
    public void testComprarIngresso() {

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

        assertNotNull(pagamentoEscolhido);  // Verifica se a forma de pagamento foi encontrada

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamentoEscolhido, "Show de Rock", "A1");

        assertNotNull(ingresso);
        assertEquals("Show de Rock", ingresso.getEvento().getNome());
        assertEquals("A1", ingresso.getAssento());
        assertTrue(usuario.getIngressos().contains(ingresso));
    }

    // Modificaddo
    @Test
    public void testCancelarCompra() {

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

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamentoEscolhido, "Show de Rock", "A1");

        boolean cancelado = controller.cancelarCompra(usuario, ingresso);

        assertTrue(cancelado);
        assertFalse(ingresso.isAtivo());
        assertFalse(usuario.getIngressos().contains(ingresso));
    }

    // Modificado
    @Test
    public void testListarEventosDisponiveis() {

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.DECEMBER, 25);
        Date data1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2025, Calendar.JANUARY, 1);
        Date data2 = calendar2.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data1);
        Evento evento2 = controller.cadastrarEvento(admin, "Peça de Teatro", "Grupo ABC", data2);

        List<Evento> eventos = List.of(new Evento[]{evento, evento2});
        controller.listarEventosDisponiveis();

        assertEquals(2, eventos.size());
    }

    // Modificado
    @Test
    public void testListarIngressosComprados() {

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

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(usuario, pagamento);

        controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        List<Ingresso> ingressos = controller.listarIngressosComprados();

        assertEquals(1, ingressos.size());
    }

    // NOVOS TESTES

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
        List<Evento> eventosAtivos = controller.getEventosCadastrados();  // Supondo que há um método que retorna os eventos cadastrados

        // Salvando os eventos em um arquivo JSON
        persistencia.salvarDados(eventosAtivos);

        // Carregando os dados de volta do arquivo
        List<Evento> eventosCarregados = persistencia.carregarDados();

        // Verificando se os eventos foram salvos e carregados corretamente
        assertNotNull(eventosCarregados);
        assertEquals(1, eventosCarregados.size());
        assertEquals("Festival de Música", eventosCarregados.getFirst().getNome());
        assertEquals("Bandas Diversas", eventosCarregados.getFirst().getDescricao());
    }

    @Test
    public void testPersistenciaCarregarEventos() {
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");
        List<Evento> detalheEventos = persistencia.carregarDados();

        assertEquals(1, detalheEventos.size());  // Verifica se dois eventos foram carregados

        Evento evento = detalheEventos.getFirst();
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("Bandas Diversas", evento.getDescricao());
    }

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

    @Test
    public void testPersistenciaIngressosDisponivelReservado() {
        PersistenciaEventos persistencia = new PersistenciaEventos("detalhes-do-evento.json");

        List<Evento> detalheEventos = persistencia.carregarDados();

        // Verificando se o evento "Festival de Música" foi carregado corretamente
        Evento evento = detalheEventos.getFirst();
        assertEquals("Festival de Música", evento.getNome());
        assertEquals(2, evento.getIngressosDisponiveis().size()); // Deve ter 2 ingressos disponíveis

        // Verificando os ingressos disponíveis para o festival de música
        Ingresso ingresso = evento.getIngressosDisponiveis().get(0);
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("A2", ingresso.getAssento());
        assertEquals(100.0, ingresso.getPreco(), 0.01);

        Ingresso ingresso2 = evento.getIngressosDisponiveis().get(1);
        assertEquals("Festival de Música", evento.getNome());
        assertEquals("A3", ingresso2.getAssento());
        assertEquals(100.0, ingresso.getPreco(), 0.01);

        // Verificando se o evento "Teatro Clássico" não está presente
        // Para isso, devemos assegurar que a lista de eventos só contém o evento de música.
        assertEquals(1, detalheEventos.size()); // Deve ter apenas 1 evento
        assertEquals("Festival de Música", detalheEventos.getFirst().getNome());

    }

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

        assertNotNull(pagamentoEscolhido);  // Verifica se a forma de pagamento foi encontrada
        assertNotNull(pagamentoEscolhido2);

        controller.comprarIngresso(usuario, pagamentoEscolhido, "Festival de Música", "A1");
        controller.comprarIngresso(usuario2, pagamentoEscolhido2, "Festival de Música", "A2");

        // Obtendo a lista de eventos para salvar
        List<Evento> eventosAtivos = controller.getEventosCadastrados();

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

        // Verificações do usuário "mariazinha"
        Usuario u2 = usuarios.get(1);
        assertEquals("mariazinha", u2.getLogin());
        assertEquals("Cartão", u2.getFormasDePagamento().getFirst().getFormaDePagamento());
        assertEquals(1, u2.getIngressosComprados().size());

        // Verificações do usuário "carolsan"
        Usuario u3 = usuarios.get(2);
        assertEquals("carolsan", u3.getLogin());
        assertEquals("Cartão", u3.getFormasDePagamento().getFirst().getFormaDePagamento());
        assertEquals(1, u3.getIngressosComprados().size());
    }


}
