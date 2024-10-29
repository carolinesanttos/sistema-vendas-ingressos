package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que uma ação não pode ser realizada enquanto o evento ainda está ativo.
 * Essa exceção é lançada, por exemplo, quando o usuário tenta avaliar um evento antes de ele ter terminado.
 */
public class EventoAtivoException extends RuntimeException{
    public EventoAtivoException (String mensagem) {
        super(mensagem);
    }
}
