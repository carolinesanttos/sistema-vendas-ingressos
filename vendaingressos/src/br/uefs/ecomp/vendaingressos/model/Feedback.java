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

package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.excecao.NaoLogadoException;

/**
 * Classe que representa um feedback dado por um usuário sobre um evento.
 * Contém informações sobre o usuário, evento, nota de avaliação (de 1 a 5) e um comentário.
 * O feedback só pode ser criado se o usuário estiver logado.
 */
public class Feedback {
    private Usuario usuario;
    private Evento evento;
    private int nota; // Avaliação de 1 a 5
    private String comentario;

    /**
     * Construtor da classe Feedback.
     * Inicializa um novo feedback apenas se o usuário estiver logado.
     *
     * @param usuario   usuário que fornece o feedback.
     * @param evento    evento que está sendo avaliado.
     * @param nota      nota atribuída ao evento (de 1 a 5).
     * @param comentario comentário sobre o evento.
     * @throws NaoLogadoException se o usuário não estiver logado.
     */
    public Feedback(Usuario usuario, Evento evento, int nota, String comentario) {
        if (usuario.isLogado()) {
            this.usuario = usuario;
            this.evento = evento;
            setNota(nota);
            this.comentario = comentario;
        } else {
            throw new NaoLogadoException("É necessário estar logado para avaliar o evento.");
        }
    }

    /**
     * Retorna String do feedback, com nome do usuário, nome do evento,
     * nota e comentário.
     *
     * @return string representando o feedback.
     */
    @Override
    public String toString() {
        return "Usuário(a): " + usuario.getNome() + "\nEvento: " + evento.getNome() +
                "\nNota: " + nota + "\nComentário: " + comentario;
    }

    /**
     * Define a nota do feedback, que deve estar entre 1 e 5.
     *
     * @param nota nova nota a ser passada.
     * @throws IllegalArgumentException se a nota não estiver entre 1 e 5.
     */
    public void setNota(int nota) {
        if (nota >= 1 && nota <= 5) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public Evento getEvento() {
        return evento;
    }

    public int getNota() {
        return nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
