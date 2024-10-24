/**
 * <p>
 * Sistema Operacional: Windows 10 - 64 Bits<br>
 * IDE: IntelliJ<br>
 * Versão Da Linguagem: Java JDK 22<br>
 * Autor: Caroline Santos de Jesus<br>
 * Componente Curricular: Algoritmos II<br>
 * Concluído em: 21/10/2024<br>
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 * </p>
 */

package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.excecao.UserNaoLogadoException;

public class Feedback {
    private Usuario usuario;
    private Evento evento;
    private int nota; // Avaliação de 1 a 5
    private String comentario;

    /**
     * Construtor da classe Feedback.
     * Inicializa um novo feedback apenas se o usuário estiver logado.
     *
     * @param usuario   O usuário que fornece o feedback.
     * @param evento    O evento que está sendo avaliado.
     * @param nota      A nota atribuída ao evento (de 1 a 5).
     * @param comentario Um comentário sobre o evento.
     * @throws UserNaoLogadoException Se o usuário não estiver logado.
     */
    public Feedback(Usuario usuario, Evento evento, int nota, String comentario) {
        if (usuario.isLogado()) {
            this.usuario = usuario;
            this.evento = evento;
            setNota(nota);
            this.comentario = comentario;
        } else {
            throw new UserNaoLogadoException("É necessário estar logado para avaliar o evento.");
        }
    }

    /**
     * Retorna uma representação em String do feedback, incluindo nome do usuário, nome do evento,
     * nota e comentário.
     *
     * @return Uma string representando o feedback.
     */
    @Override
    public String toString() {
        return "Usuário(a): " + usuario.getNome() + "\nEvento: " + evento.getNome() +
                "\nNota: " + nota + "\nComentário: " + comentario;
    }

    /**
     * Define a nota do feedback, que deve estar entre 1 e 5.
     *
     * @param nota A nova nota a ser atribuída.
     * @throws IllegalArgumentException Se a nota não estiver entre 1 e 5.
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
