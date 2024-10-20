package br.uefs.ecomp.vendaingressos.model.Excecao;

public class CredencialInvalidaException extends RuntimeException{
    public CredencialInvalidaException (String mensagem) {
        super(mensagem);
    }
}
