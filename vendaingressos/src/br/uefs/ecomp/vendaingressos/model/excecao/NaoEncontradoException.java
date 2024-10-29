package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que o recurso solicitado não foi encontrado no sistema.
 * Essa exceção pode ser lançada, por exemplo, quando um ingresso ou assento específico não está disponível.
 */
public class NaoEncontradoException extends RuntimeException{

    public NaoEncontradoException(String mensagem) {
        super(mensagem);
    }

}
