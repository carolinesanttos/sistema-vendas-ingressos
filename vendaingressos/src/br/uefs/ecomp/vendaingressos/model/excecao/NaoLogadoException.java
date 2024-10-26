package br.uefs.ecomp.vendaingressos.model.excecao;

public class NaoLogadoException extends RuntimeException{

    public NaoLogadoException(String mensagem) {
        super(mensagem);
    }
}
