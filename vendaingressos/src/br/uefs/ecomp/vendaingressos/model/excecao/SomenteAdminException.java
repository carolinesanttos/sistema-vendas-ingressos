package br.uefs.ecomp.vendaingressos.model.excecao;

/**
 * Exceção que indica que uma ação só pode ser realizada por um administrador.
 * Essa exceção é lançada quando um usuário comum tenta executar uma operação que é restrita a administradores.
 */
public class SomenteAdminException extends RuntimeException{
    public SomenteAdminException (String mensagem) {
            super(mensagem);
        }
}
