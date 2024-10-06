package br.uefs.ecomp.vendasingressos.model;

import java.util.Date;
import java.util.Calendar;
import java.util.List;

import br.uefs.ecomp.vendaingressos.model.*;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;


public class ControllerTest {

    @Test
    public void testCadastrarEventoPorAdmin() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User",
                "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);

        assertNotNull(evento);
        assertEquals("Show de Rock", evento.getNome());
        assertEquals("Banda XYZ", evento.getDescricao());
        assertEquals(data, evento.getData());
    }

    @Test
    public void testCadastrarEventoPorUsuarioComum() {
        Controller controller = new Controller();
        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Exception exception = assertThrows(SecurityException.class, () -> {
            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data);
        });

        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
    }

    @Test
    public void testComprarIngresso() {
        Controller controller = new Controller();
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");

        Ingresso ingresso = controller.comprarIngresso(usuario, "Show de Rock", "A1");

        assertNotNull(ingresso);
        assertEquals("Show de Rock", ingresso.getEvento().getNome());
        assertEquals("A1", ingresso.getAssento());
        assertTrue(usuario.getIngressos().contains(ingresso));
    }

    @Test
    public void testCancelarCompra() {
        Controller controller = new Controller();
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30); // Foi necessário alterar a data.
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        Ingresso ingresso = controller.comprarIngresso(usuario, "Show de Rock", "A1");

        boolean cancelado = controller.cancelarCompra(usuario, ingresso);
        assertTrue(cancelado);
        assertFalse(ingresso.isAtivo());
        assertFalse(usuario.getIngressos().contains(ingresso));
    }

    @Test
    public void testListarEventosDisponiveis() {
        Controller controller = new Controller();
        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2024, Calendar.NOVEMBER, 30); // Foi necessário alterar a data
        Date data1 = calendar1.getTime();

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2024, Calendar.SEPTEMBER, 15);
        Date data2 = calendar2.getTime();

        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data1);
        controller.cadastrarEvento(admin, "Peça de Teatro", "Grupo ABC", data2);

        List<Evento> eventos = controller.listarEventosDisponiveis();

        assertEquals(2, eventos.size());
    }

    @Test
    public void testListarIngressosComprados() {
        Controller controller = new Controller();
        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        controller.adicionarAssentoEvento("Show de Rock", "A1");
        controller.comprarIngresso(usuario, "Show de Rock", "A1");

        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);

        assertEquals(1, ingressos.size());
    }

    @Test
    public void testAlterarDadosPessoais () {
        Controller controller = new Controller();

        controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        Usuario usuario = controller.login("carolsan", "animehime");

        controller.alterarNome("Caroline de Jesus");
        controller.alterarEmail("ca.dejesus@example.com");
        controller.alterarSenha("carol12345");

        assertEquals("Caroline de Jesus", usuario.getNome());
        assertEquals("ca.dejesus@example.com", usuario.getEmail());
        assertEquals("carol12345", usuario.getSenha());
    }

    @Test
    public void testAdicionarFormasPagamento () {
        Controller controller = new Controller();

        controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        Usuario usuario = controller.login("johndoe", "senha123");

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");
        Pagamento pagamento2 = new Pagamento("12345678901234567890");

        controller.adicionaFormaPagamento(usuario, pagamento);
        controller.adicionaFormaPagamento(usuario, pagamento2);

        List<Pagamento> formasDePagamento = controller.listarFormasDePagamento(usuario);

        assertEquals(2, formasDePagamento.size());
    }

    @Test
    public void testProcessarPagamentoBoleto() {
        Controller controller = new Controller();
        Pagamento formaPagamento = new Pagamento("78945612390142536789");

        // adicionar forma de pagamento, pegar forma de pagamento na lista e processar o pagamento


        String resultado = controller.processarPagamento(formaPagamento, 350.0);

        assertEquals("Pagamento no valor de 350.0 processado com sucesso no boleto.", resultado);
    }

    @Test
    public void testProcessarPagamentoCartao() {
        Controller controller = new Controller();
        Pagamento formaPagamento = new Pagamento("7589 7418 8529 9637", "John Doe", "10/31", "927");

        String resultado = controller.processarPagamento(formaPagamento, 150.0);

        assertEquals("Pagamento no valor de 150.0 processado com sucesso no cartão.", resultado);
    }

    @Test
    public void testProcessarCompra () {
        Controller controller = new Controller();

        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User",
                "00000000000", "admin@example.com", true);

        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30);
        Date data = calendar.getTime();

        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = controller.gerarIngressos(evento, 150.0, "A1");

        controller.cadastrarUsuario("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);
        Usuario usuario = controller.login("carolsan", "animehime");

        assertNotNull(usuario);

        Pagamento pagamento = new Pagamento("7589 7418 8529 9637", "Carol Santos", "10/31", "927");
        usuario.adicionaFormaDePagamento(pagamento);

        assertEquals(1, usuario.getFormaDePagamento().size());

        String resultado = controller.processsarCompra(usuario, ingresso, pagamento, data, 150.0);

        assertEquals("Pagamento no valor de 150.0 processado com sucesso no cartão.", resultado);

        Compra compra = controller.mostrarDetalheCompra(usuario);

        assertEquals("Aprovado", compra.getStatus());
        assertEquals(150.0, compra.getValor(), 0.001);
    }


}
