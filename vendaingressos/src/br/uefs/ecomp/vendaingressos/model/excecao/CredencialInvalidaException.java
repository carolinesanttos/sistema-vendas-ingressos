package br.uefs.ecomp.vendaingressos.model.excecao;

public class CredencialInvalidaException extends RuntimeException{
    public CredencialInvalidaException (String mensagem) {
        super(mensagem);
    }
}
