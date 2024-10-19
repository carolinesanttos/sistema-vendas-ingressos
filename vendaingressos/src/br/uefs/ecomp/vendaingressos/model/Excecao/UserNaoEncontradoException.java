package br.uefs.ecomp.vendaingressos.model.Excecao;

public class UserNaoEncontradoException extends RuntimeException{

    public String atributo;

    public UserNaoEncontradoException(String atributo) {
        this.atributo = atributo;
    }

    @Override
    public String getMessage() {
        return atributo + " incorretos.";
    }
}
