package br.uefs.ecomp.vendaingressos.model.Excecao;

public class NaoEncontradoException extends RuntimeException{

    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
