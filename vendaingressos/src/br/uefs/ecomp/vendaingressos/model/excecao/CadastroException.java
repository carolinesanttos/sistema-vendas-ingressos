package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica um erro durante o processo de cadastro de um elemento no sistema.
 * Essa exceção pode ser lançada, por exemplo, quando se tenta cadastrar um evento ou assento que já foi previamente adicionado.
 */
public class CadastroException extends RuntimeException {
    public CadastroException(String mensagem) {
        super(mensagem);
    }

}
