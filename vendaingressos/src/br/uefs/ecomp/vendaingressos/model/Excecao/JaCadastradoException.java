package br.uefs.ecomp.vendaingressos.model.Excecao;

public class JaCadastradoException extends RuntimeException {

    public String atributo;

    public JaCadastradoException (String atributo) {
        this.atributo = atributo;
    }

    public String getMessage() {
        return atributo + " já cadastrado.";
    }

}
