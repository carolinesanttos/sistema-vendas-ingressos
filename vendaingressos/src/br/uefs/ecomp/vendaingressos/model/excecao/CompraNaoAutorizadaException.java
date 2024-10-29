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
 * Exceção que indica que a compra não foi autorizada, impedindo o processamento do pagamento.
 * Essa exceção é lançada quando ocorre uma falha ao tentar processar uma compra ou pagamento.
 */
public class CompraNaoAutorizadaException extends RuntimeException{
    public CompraNaoAutorizadaException (String mensagem) {
        super(mensagem);
    }
}
