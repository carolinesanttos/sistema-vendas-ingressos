package br.uefs.ecomp.vendasingressos.model;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import br.uefs.ecomp.vendaingressos.model.*;
import br.uefs.ecomp.vendaingressos.model.Excecao.CredencialInvalidaException;
import br.uefs.ecomp.vendaingressos.model.Excecao.JaCadastradoException;
import br.uefs.ecomp.vendaingressos.model.Excecao.NaoEncontradoException;
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
        Evento.limparEventosCadastrados();
    }


    // Modificado
    @Test
    public void testCadastrarEventoPorAdmin() {

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
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
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

//    // Modificado
    @Test
    public void testComprarIngresso() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");

        Ingresso ingresso = new Ingresso(usuario, evento, "A1");
        controller.adicionarIngresso(ingresso);

        Pagamento pagamento = new Pagamento("7891234567890");
        controller.adicionarFormaPagamento(pagamento);

        Pagamento pagamentoEscolhido = controller.escolheFormaPagamento(pagamento);
        assertNotNull(pagamentoEscolhido);  // Verifica se a forma de pagamento foi encontrada

        ingresso = controller.comprarIngresso(usuario, pagamentoEscolhido, "Show de Rock", "A1");

        assertNotNull(ingresso);
        assertEquals("Show de Rock", ingresso.getEvento().getNome());
        assertEquals("A1", ingresso.getAssento());
        assertTrue(usuario.getIngressos().contains(ingresso));
    }

    // Modificaddo
    @Test
    public void testCancelarCompra() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.adicionarIngresso(new Ingresso(usuario,evento, "A1"));

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(pagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        boolean cancelado = controller.cancelarCompra(usuario, ingresso, pagamento);

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
        calendar1.set(2024, Calendar.NOVEMBER, 30);
        Date data1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2024, Calendar.SEPTEMBER, 15);
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

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.adicionarIngresso(new Ingresso(usuario,evento, "A1"));

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(pagamento);

        controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);

        assertEquals(1, ingressos.size());
    }

    // NOVOS TESTES
    @Test
    public void testUsuarioNaoEncontrado () {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        NaoEncontradoException exception = assertThrows(NaoEncontradoException.class, () -> {
            controller.login("johndo", "senha123");
        });

        assertEquals("Usuário não encontrado.", exception.getMessage());
    }

    @Test
    public void testLoginIncorreto () {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        CredencialInvalidaException exception = assertThrows(CredencialInvalidaException.class, () -> {
            controller.login("johndoe", "john123");
        });

        assertEquals("Login ou senha inválidos.", exception.getMessage());
    }

    @Test
    public void testUserJaCadastrado () {

        controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        JaCadastradoException exception = assertThrows(JaCadastradoException.class, () -> {
            controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        });

        assertEquals("Usuário já cadastrado.", exception.getMessage());
    }

    @Test
    public void testAlterarDadosPessoais () {

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        controller.alterarNome("Caroline de Jesus");
        controller.alterarEmail("ca.dejesus@example.com");
        controller.alterarSenha("carol12345");

        assertEquals("Caroline de Jesus", usuario.getNome());
        assertEquals("ca.dejesus@example.com", usuario.getEmail());
        assertEquals("carol12345", usuario.getSenha());
    }

    @Test
    public void testAdicionarFormasPagamento () {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Pagamento formaPagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        Pagamento formaPagamento2 = new Pagamento("12345678901234567890");

        controller.adicionaFormaPagamento(usuario, formaPagamento);
        controller.adicionaFormaPagamento(usuario, formaPagamento2);

        List<Pagamento> formasDePagamento = controller.listarFormasDePagamento(usuario);

        assertEquals(2, formasDePagamento.size());
    }

    @Test
    public void testProcessarPagamentoBoleto() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.adicionarIngresso(new Ingresso(usuario,evento, "A1"));

        Pagamento formaPagamento = new Pagamento("78945612390142536789");
        controller.adicionarFormaPagamento(formaPagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, formaPagamento, "Show de Rock", "A1");

        String resultado = controller.processarPagamento(formaPagamento, ingresso.getPreco());

        assertEquals("Pagamento no valor de 100.0 processado com sucesso no boleto.", resultado);

    }

    @Test
    public void testProcessarPagamentoCartao() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.adicionarIngresso(new Ingresso(usuario,evento, "A1"));

        Pagamento formaPagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(formaPagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, formaPagamento, "Show de Rock", "A1");

        String resultado = controller.processarPagamento(formaPagamento, ingresso.getPreco());

        assertEquals("Pagamento no valor de 100.0 processado com sucesso no cartão.", resultado);
    }

    @Test
    public void testConfirmacaoDeCompra () {

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        controller.login("carolsan", "animehime");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");

        Ingresso ingresso = new Ingresso(usuario, evento, "A1");
        controller.adicionarIngresso(ingresso);

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "Carol Santos", "10/31", "927");
        usuario.adicionaFormaDePagamento(pagamento);

        ingresso = controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        String resultado = controller.processarPagamento(pagamento, ingresso.getPreco());

        String mensagemEnviada = controller.confirmacaoDeCompra(usuario, pagamento);

        assertEquals("Pagamento no valor de 100.0 processado com sucesso no cartão.", resultado);
        assertEquals("Destinatário: ca.sant@example.com\nAssunto: Confirmação de Compra\n\n" +
                "Olá, Carol Santos,\n\n" +  "Obrigado por sua compra! Aqui estão os detalhes da sua compra:\n\n" +
                "Produto: Show de Rock - Assento: A1\n" + "Valor: R$ 100.0\n" +
                "Método de pagamento: Cartão\n\n" + "Sua compra foi processada com sucesso. Caso tenha dúvidas, " +
                "entre em contato com nosso suporte.\n\n" + "Atenciosamente,\nEquipe de Vendas", mensagemEnviada);
    }

    @Test
    public void testReembolsoDeCompra() {

        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        controller.login("johndoe", "senha123");

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.login("admin", "senha123");

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.adicionarIngresso(new Ingresso(usuario,evento, "A1"));

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        controller.adicionarFormaPagamento(pagamento);

        Ingresso ingresso = controller.comprarIngresso(usuario, pagamento, "Show de Rock", "A1");

        boolean cancelado = controller.cancelarCompra(usuario, ingresso, pagamento);
        String resultado = controller.reembolsarValor(ingresso, pagamento);

        assertTrue(cancelado);
        assertEquals("O pagamento no valor de R$100.0 será em reembolsado em até 15 dias via Cartão.", resultado);
    }

    @Test
    public void testAvaliacao () {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario usuario = controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        Usuario usuario2 = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        controller.login("carolsan", "animehime");
        controller.login("johndoe", "senha123");

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);

        Feedback feedback = new Feedback(usuario, evento, 1, "Infelizmente, o evento foi uma grande decepção.");
        Feedback feedback2 = new Feedback(usuario2, evento, 5, "O evento foi excelente!");

        assertEquals(usuario, feedback.getUsuario());
        assertEquals(usuario2, feedback2.getUsuario());

        assertEquals(evento, feedback.getEvento());
        assertEquals(evento, feedback2.getEvento());

        assertEquals(1, feedback.getNota());
        assertEquals(5, feedback2.getNota());

        assertEquals("Infelizmente, o evento foi uma grande decepção.", feedback.getComentario());
        assertEquals("O evento foi excelente!", feedback2.getComentario());
    }

}
