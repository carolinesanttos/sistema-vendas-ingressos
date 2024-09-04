import java.util.Date;
import java.util.Calendar;
import java.util.List;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThrows;
import br.uefs.ecomp.vendaingressos.model.Controller;
import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;
import br.uefs.ecomp.vendaingressos.model.Usuario;


public class ControllerTest {

//    @Test
//    public void testCadastrarEventoPorAdmin() {
//        Controller controller = new Controller();
//        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.SEPTEMBER, 10);
//        Date data = calendar.getTime();
//
//        Evento evento = controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
//
//        assertNotNull(evento);
//        assertEquals("Show de Rock", evento.getNome());
//        assertEquals("Banda XYZ", evento.getDescricao());
//        assertEquals(data, evento.getData());
//    }
//
//    @Test
//    public void testCadastrarEventoPorUsuarioComum() {
//        Controller controller = new Controller();
//        Usuario usuario = controller.cadastrarUsuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.SEPTEMBER, 10);
//        Date data = calendar.getTime();
//
//        Exception exception = assertThrows(SecurityException.class, () -> {
//            controller.cadastrarEvento(usuario, "Peça de Teatro", "Grupo ABC", data);
//        });
//
//        assertEquals("Somente administradores podem cadastrar eventos.", exception.getMessage());
//    }
//
//    @Test
//    public void testComprarIngresso() {
//        Controller controller = new Controller();
//        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.SEPTEMBER, 10);
//        Date data = calendar.getTime();
//
//        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
//        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
//        controller.adicionarAssentoEvento("Show de Rock", "A1");
//
//        Ingresso ingresso = controller.comprarIngresso(usuario, "Show de Rock", "A1");
//
//        assertNotNull(ingresso);
//        assertEquals("Show de Rock", ingresso.getEvento().getNome());
//        assertEquals("A1", ingresso.getAssento());
//        assertTrue(usuario.getIngressos().contains(ingresso));
//    }
//
//    @Test
//    public void testCancelarCompra() {
//        Controller controller = new Controller();
//        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.SEPTEMBER, 10);
//        Date data = calendar.getTime();
//
//        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
//        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
//        controller.adicionarAssentoEvento("Show de Rock", "A1");
//        Ingresso ingresso = controller.comprarIngresso(usuario, "Show de Rock", "A1");
//
//        boolean cancelado = controller.cancelarCompra(usuario, ingresso);
//        assertTrue(cancelado);
//        assertFalse(ingresso.isAtivo());
//        assertFalse(usuario.getIngressos().contains(ingresso));
//    }
//
//    @Test
//    public void testListarEventosDisponiveis() {
//        Controller controller = new Controller();
//        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
//
//        Calendar calendar1 = Calendar.getInstance();
//        calendar1.set(2024, Calendar.SEPTEMBER, 10);
//        Date data1 = calendar1.getTime();
//
//        Calendar calendar2 = Calendar.getInstance();
//        calendar2.set(2024, Calendar.SEPTEMBER, 15);
//        Date data2 = calendar2.getTime();
//
//        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data1);
//        controller.cadastrarEvento(admin, "Peça de Teatro", "Grupo ABC", data2);
//
//        List<Evento> eventos = controller.listarEventosDisponiveis();
//
//        assertEquals(2, eventos.size());
//    }
//
//    @Test
//    public void testListarIngressosComprados() {
//        Controller controller = new Controller();
//        Usuario usuario = new Usuario("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
//
//        Calendar calendar = Calendar.getInstance();
//        calendar.set(2024, Calendar.SEPTEMBER, 10);
//        Date data = calendar.getTime();
//
//        Usuario admin = controller.cadastrarUsuario("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);
//        controller.cadastrarEvento(admin, "Show de Rock", "Banda XYZ", data);
//        controller.adicionarAssentoEvento("Show de Rock", "A1");
//        controller.comprarIngresso(usuario, "Show de Rock", "A1");
//
//        List<Ingresso> ingressos = controller.listarIngressosComprados(usuario);
//
//        assertEquals(1, ingressos.size());
//    }
}
