package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que uma ação não pode ser realizada porque o usuário não está logado no sistema.
 * Essa exceção é lançada quando uma operação requer autenticação do usuário.
 */
public class NaoLogadoException extends RuntimeException{

    public NaoLogadoException(String mensagem) {
        super(mensagem);
    }
}
