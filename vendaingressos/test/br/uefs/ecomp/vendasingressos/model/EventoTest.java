package br.uefs.ecomp.vendasingressos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;

public class EventoTest {

    private Evento evento;

    @BeforeEach
    public void setUp() {
        evento = new Evento("Show", "Descrição do Show", "2024-08-20");
    }

    @Test
    public void testVenderIngresso() {
        Ingresso ingresso = evento.venderIngresso();
        assertNotNull(ingresso);
        assertEquals(evento, ingresso.getEvento());
    }

    @Test
    public void testEventoAtivo() {
        assertTrue(evento.isAtivo());
    }
}

