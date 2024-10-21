package br.uefs.ecomp.vendaingressos.model.excecao;

public class NaoEncontradoException extends RuntimeException{

    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
