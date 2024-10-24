package br.uefs.ecomp.vendaingressos.model.excecao;

public class EventoAtivoException extends RuntimeException{
    public EventoAtivoException (String mensagem) {
        super(mensagem);
    }
}
