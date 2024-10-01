package br.uefs.ecomp.vendasingressos.model;

import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import java.util.Calendar;
import java.util.Date;
import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;

public class IngressoTest {

    @Test
    public void testCriarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertNotNull(ingresso);
        assertEquals(evento, ingresso.getEvento());
         assertEquals(100.0, ingresso.getPreco(), 0.0001);
        assertEquals("A1", ingresso.getAssento());
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testCancelarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30); // Foi necessário alterar a data
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertTrue(ingresso.cancelarIngresso());
        assertFalse(ingresso.isAtivo());
    }

    @Test
    public void testCancelarIngressoEventoPassado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2023, Calendar.JANUARY, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        assertFalse(ingresso.cancelarIngresso());
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testReativarIngresso() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.NOVEMBER, 30); // / Foi necessário alterar a data
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso = new Ingresso(evento, 100.0, "A1");

        ingresso.cancelarIngresso();
        assertFalse(ingresso.isAtivo());

        ingresso.reativar();
        assertTrue(ingresso.isAtivo());
    }

    @Test
    public void testIngressoDuplicado() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2024, Calendar.SEPTEMBER, 10);
        Date data = calendar.getTime();

        Evento evento = new Evento("Show de Rock", "Banda XYZ", data);
        Ingresso ingresso1 = new Ingresso(evento, 100.0, "A1");
        Ingresso ingresso2 = new Ingresso(evento, 100.0, "A1");

        assertEquals(ingresso1, ingresso2);
        assertEquals(ingresso1.hashCode(), ingresso2.hashCode());
    }
}
