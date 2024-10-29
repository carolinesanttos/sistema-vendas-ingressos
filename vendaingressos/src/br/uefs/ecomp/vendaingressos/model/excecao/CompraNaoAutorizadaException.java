package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que a compra não foi autorizada, impedindo o processamento do pagamento.
 * Essa exceção é lançada quando ocorre uma falha ao tentar processar uma compra ou pagamento.
 */
public class CompraNaoAutorizadaException extends RuntimeException{
    public CompraNaoAutorizadaException (String mensagem) {
        super(mensagem);
    }
}
