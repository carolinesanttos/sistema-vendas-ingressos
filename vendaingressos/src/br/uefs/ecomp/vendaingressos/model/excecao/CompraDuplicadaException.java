package br.uefs.ecomp.vendaingressos.model.excecao;

public class CompraDuplicadaException extends RuntimeException{
    public CompraDuplicadaException (String mensagem) {
        super(mensagem);
    }
}
