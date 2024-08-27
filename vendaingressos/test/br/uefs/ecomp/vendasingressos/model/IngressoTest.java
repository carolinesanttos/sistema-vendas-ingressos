package br.uefs.ecomp.vendasingressos.model;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;

public class IngressoTest {

    private Ingresso ingresso;
    private Evento evento;

    @BeforeEach
    public void setUp() {
        evento = new Evento("Show", "Descrição do Show", "2024-08-20");
        ingresso = new Ingresso("1", evento);
    }

    @Test
    public void testGetId() {
        assertEquals("1", ingresso.getId());
    }

    @Test
    public void testGetEvento() {
        assertEquals(evento, ingresso.getEvento());
    }
}
