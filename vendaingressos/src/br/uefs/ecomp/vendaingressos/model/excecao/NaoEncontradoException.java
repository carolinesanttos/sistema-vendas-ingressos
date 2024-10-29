/**
 * Sistema Operacional: Windows 10 - 64 Bits
 * IDE: IntelliJ
 * Versão Da Linguagem: Java JDK 22
 * Autor: Caroline Santos de Jesus
 * Componente Curricular: Algoritmos II
 * Concluído em: 28/10/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 */

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
