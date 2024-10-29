package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica um erro durante o processo de reembolso.
 * Essa exceção é lançada quando ocorre um problema ao tentar reembolsar um pagamento,
 * como quando o pagamento já foi reembolsado anteriormente.
 */
public class ReembolsoException extends RuntimeException{
    public ReembolsoException (String mensagem) {
        super(mensagem);
    }
}
