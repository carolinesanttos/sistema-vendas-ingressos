package br.uefs.ecomp.vendaingressos.model.Excecao;

public class CompraNaoAutorizadaException extends RuntimeException{
    public CompraNaoAutorizadaException (String mensagem) {
        super(mensagem);
    }
}
