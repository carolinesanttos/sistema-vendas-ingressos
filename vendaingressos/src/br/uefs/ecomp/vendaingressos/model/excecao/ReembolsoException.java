package br.uefs.ecomp.vendaingressos.model.excecao;

public class ReembolsoException extends RuntimeException{
    public ReembolsoException (String mensagem) {
        super(mensagem);
    }
}
