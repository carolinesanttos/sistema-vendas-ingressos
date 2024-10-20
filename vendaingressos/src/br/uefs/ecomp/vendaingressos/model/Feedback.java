package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.Excecao.UserNaoLogadoException;

public class Feedback {
    private Usuario usuario;
    private Evento evento;
    private int nota; // Avaliação de 1 a 5
    private String comentario;

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


    @Override
    public String toString() {
        return "Usuário(a): " + usuario.getNome() + "\nEvento: " + evento.getNome() +
                "\nNota: " + nota + "\nComentário: " + comentario;
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

    public void setNota(int nota) {
        if (nota >= 1 && nota <= 5) {
            this.nota = nota;
        } else {
            throw new IllegalArgumentException("A nota deve estar entre 1 e 5.");
        }
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
