package br.uefs.ecomp.vendaingressos.model.Excecao;

public class UserNaoLogadoException extends RuntimeException{

    public UserNaoLogadoException(String mensagem) {
        super(mensagem);
    }
}
