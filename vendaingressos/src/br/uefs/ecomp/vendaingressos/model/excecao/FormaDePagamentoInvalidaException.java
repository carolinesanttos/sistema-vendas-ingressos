package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que a forma de pagamento fornecida é inválida ou não suportada pelo sistema.
 * Essa exceção é lançada quando uma forma de pagamento incorreta é selecionada para uma transação.
 */
public class FormaDePagamentoInvalidaException extends RuntimeException{
    public FormaDePagamentoInvalidaException (String mensagem) {
        super(mensagem);
    }
}
