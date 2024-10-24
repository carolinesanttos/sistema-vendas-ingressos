package br.uefs.ecomp.vendaingressos.model.excecao;

public class SomenteAdminException extends RuntimeException{

    private String mensagem;

    public SomenteAdminException (String mensagem) {
            super(mensagem);
        }
}
