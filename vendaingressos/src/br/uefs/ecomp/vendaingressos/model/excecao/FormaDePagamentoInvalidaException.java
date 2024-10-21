package br.uefs.ecomp.vendaingressos.model.excecao;

public class FormaDePagamentoInvalidaException extends RuntimeException{
    public FormaDePagamentoInvalidaException (String mensagem) {
        super(mensagem);
    }
}
