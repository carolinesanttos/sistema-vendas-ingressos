package br.uefs.ecomp.vendaingressos.model.excecao;

public class CompraNaoAutorizadaException extends RuntimeException{
    public CompraNaoAutorizadaException (String mensagem) {
        super(mensagem);
    }
}
