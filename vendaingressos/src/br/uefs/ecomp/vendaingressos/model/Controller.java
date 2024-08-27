package br.uefs.ecomp.vendaingressos.model;

import java.util.Iterator;

public class Controller {
    Usuario usuario;
    Evento evento;

    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email) {
        usuario = new Usuario(login, senha, nome, cpf, email);
        usuario.cadastroDeUsuarios(usuario);
        return usuario;
    }

    public Usuario loginUsuario(String login, String senha) {
        usuario = usuario.logarUsuario(login, senha);
        return usuario;
    }

    public Evento cadastrarEvento(String nome, String descricao, String data) {
        evento = new Evento(nome, descricao, data);
        evento.cadastroDeEventos(evento);
        return evento;
    }
}
