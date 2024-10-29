package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que o ingresso não pode ser comprado porque o prazo para compra do evento expirou.
 * Essa exceção é lançada quando o usuário tenta comprar um ingresso para um evento que já passou da data.
 */
public class EventoForaDoPrazoException extends RuntimeException{

    public EventoForaDoPrazoException (String nomeEvento) {
        super("Não é possível comprar ingresso para o evento " + nomeEvento + " porque o prazo para compra já expirou.");

    }
}
