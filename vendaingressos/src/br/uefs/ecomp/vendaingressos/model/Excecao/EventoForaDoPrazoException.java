package br.uefs.ecomp.vendaingressos.model.Excecao;

public class EventoForaDoPrazoException extends RuntimeException{

    public EventoForaDoPrazoException (String nomeEvento) {
        super("Não é possível comprar ingresso para o evento " + nomeEvento + " porque o prazo para compra já expirou.");

    }
}
