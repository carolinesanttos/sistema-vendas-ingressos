package br.uefs.ecomp.vendaingressos.model.excecao;

public class UserNaoLogadoException extends RuntimeException{

    public UserNaoLogadoException(String mensagem) {
        super(mensagem);
    }
}
