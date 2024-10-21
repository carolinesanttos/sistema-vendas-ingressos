package br.uefs.ecomp.vendaingressos.model.excecao;

public class CompraJaCanceladaException extends RuntimeException{
    public CompraJaCanceladaException (String mensagem) {
        super(mensagem);
    }
}
