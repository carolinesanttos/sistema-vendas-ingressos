package br.uefs.ecomp.vendasingressos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.uefs.ecomp.vendaingressos.model.Controller;
import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;
import br.uefs.ecomp.vendaingressos.model.Usuario;
import java.util.List;

public class ControllerTest {

    private Controller controller;

    @BeforeEach
    public void setUp() {
        controller = new Controller();
    }

    @Test
    public void testCadastrarUsuario() {
        Usuario usuario = controller.cadastrarUsuario("user1", "senha123", "Nome 1", "11111111111", "user1@example.com");
        assertNotNull(usuario);
        assertEquals("user1", usuario.getLogin());
    }

    @Test
    public void testLoginUsuario() {
        controller.cadastrarUsuario("user2", "senha456", "Nome 2", "22222222222", "user2@example.com");
        Usuario usuario = controller.loginUsuario("user2", "senha456");
        assertNotNull(usuario);
        assertEquals("user2", usuario.getLogin());
    }

    @Test
    public void testCadastrarEvento() {
        controller.cadastrarUsuario("admin", "adminpass", "Admin", "00000000000", "admin@example.com");
        controller.loginUsuario("admin", "adminpass");
        Evento evento = controller.cadastrarEvento("Show", "Descrição do Show", "2024-08-20");
        assertNotNull(evento);
        assertEquals("Show", evento.getNome());
    }

//    @Test
//    public void testListarEventosDisponiveis() {
//        controller.cadastrarUsuario("admin", "adminpass", "Admin", "00000000000", "admin@example.com");
//        controller.loginUsuario("admin", "adminpass");
//        controller.cadastrarEvento("Show 1", "Descrição do Show 1", "2024-08-21");
//        controller.cadastrarEvento("Show 2", "Descrição do Show 2", "2024-08-22");
//
//        List<Evento> eventos = controller.listarEventosDisponiveis();
//        assertEquals(2, eventos.size());
//    }
//
//    @Test
//    public void testComprarIngresso() {
//        controller.cadastrarUsuario("user3", "senha789", "Nome 3", "33333333333", "user3@example.com");
//        controller.loginUsuario("user3", "senha789");
//        controller.cadastrarEvento("Show 3", "Descrição do Show 3", "2024-08-23");
//
//        Ingresso ingresso = controller.comprarIngresso("Show 3");
//        assertNotNull(ingresso);
//        assertEquals("Show 3", ingresso.getEvento().getNome());
//    }
//
//    @Test
//    public void testCancelarCompraIngresso() {
//        controller.cadastrarUsuario("user4", "senha000", "Nome 4", "44444444444", "user4@example.com");
//        controller.loginUsuario("user4", "senha000");
//        controller.cadastrarEvento("Show 4", "Descrição do Show 4", "2024-08-24");
//
//        Ingresso ingresso = controller.comprarIngresso("Show 4");
//        controller.cancelarCompraIngresso(ingresso.getId());
//        assertFalse(controller.listarIngressosComprados().contains(ingresso));
//    }
}

