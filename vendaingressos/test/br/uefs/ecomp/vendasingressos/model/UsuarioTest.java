package br.uefs.ecomp.vendasingressos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.uefs.ecomp.vendaingressos.model.Usuario;
import br.uefs.ecomp.vendaingressos.model.Ingresso;

import java.util.List;

public class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    public void setUp() {
        usuario = new Usuario("login", "senha", "Nome", "12345678901", "email@example.com");
    }

    @Test
    public void testAdicionarIngresso() {
        Ingresso ingresso = new Ingresso("1", null);
        usuario.adicionarIngressoComprado(ingresso);
        List<Ingresso> ingressos = usuario.getIngressos();
        assertEquals(1, ingressos.size());
        assertEquals(ingresso, ingressos.get(0));
    }

    @Test
    public void testCancelarIngresso() {
        Ingresso ingresso = new Ingresso("1", null);
        usuario.adicionarIngressoComprado(ingresso);
        usuario.cancelarIngressoComprado(ingresso.getId());
        assertTrue(usuario.getIngressos().isEmpty());
    }
}
