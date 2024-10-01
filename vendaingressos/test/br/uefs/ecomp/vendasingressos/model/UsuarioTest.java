package br.uefs.ecomp.vendasingressos.model;

import br.uefs.ecomp.vendaingressos.model.UserAdministrador;
import br.uefs.ecomp.vendaingressos.model.UserComum;
import br.uefs.ecomp.vendaingressos.model.Usuario;
import br.uefs.ecomp.vendaingressos.model.Ingresso;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;


public class UsuarioTest {

    @Test
    public void testCadastrarUsuario() {
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertNotNull(usuario);
        assertEquals("johndoe", usuario.getLogin());
        assertEquals("John Doe", usuario.getNome());
        assertEquals("12345678901", usuario.getCpf());
        assertEquals("john.doe@example.com", usuario.getEmail());
        assertFalse(usuario.isAdmin());
    }

    @Test
    public void testCadastrarUsuarioAdmin() {
        UserAdministrador admin = new UserAdministrador("admin", "senha123", "Admin User", "00000000000", "admin@example.com", true);

        assertNotNull(admin);
        assertEquals("admin", admin.getLogin());
        assertEquals("Admin User", admin.getNome());
        assertEquals("00000000000", admin.getCpf());
        assertEquals("admin@example.com", admin.getEmail());
        assertTrue(admin.isAdmin());
    }

    @Test
    public void testLogin() {
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        assertTrue(usuario.login("johndoe", "senha123"));
        assertFalse(usuario.login("johndoe", "senhaErrada"));
    }

    @Test
    public void testAtualizarSenha() {
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setSenha("novaSenha123");
        assertTrue(usuario.login("johndoe", "novaSenha123"));
        assertFalse(usuario.login("johndoe", "senha123"));
    }

    @Test
    public void testDadosUsuario() {
        UserComum usuario = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);

        usuario.setNome("Jonathan Doe");
        usuario.setCpf("10987654321");
        usuario.setEmail("jon.doe@example.com");

        assertEquals("Jonathan Doe", usuario.getNome());
        assertEquals("10987654321", usuario.getCpf());
        assertEquals("jon.doe@example.com", usuario.getEmail());
    }

    @Test
    public void testUsuarioDuplicado() {
        UserComum usuario1 = new UserComum("johndoe", "senha123", "John Doe", "12345678901", "john.doe@example.com", false);
        UserComum usuario2 = new UserComum("johndoe", "senha456", "John Doe", "12345678901", "john.doe@example.com", false);

        assertEquals(usuario1, usuario2);
    }

    // A partir daqui testamos o problema 2.

    @Test
    public void testAlterarDadosPessoais () {
        UserComum usuario = new UserComum("carolsan", "animehime", "Carol Santos", "09875978902", "ca.sant@example.com", false);

        usuario.setNome("Caroline Santos");
        usuario.setEmail("caroline.sants@example.com");
        usuario.setSenha("kawaiidesu");

        assertEquals("Caroline Santos", usuario.getNome());
        assertEquals("caroline.sants@example.com", usuario.getEmail());
        assertEquals("kawaiidesu", usuario.getSenha());
    }

}
