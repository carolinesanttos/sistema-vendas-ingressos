package br.uefs.ecomp.vendaingressos.model;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Controller {
    Usuario usuario;
    Evento evento;
    Ingresso ingresso;

    public Controller() {
    }

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

    public List<Evento> listarEventosDisponiveis() {
        return evento.getEventosCadastrados();
    }

    public Ingresso comprarIngresso(String name) {// Nome do show
        Ingresso compra = evento.compraDeIngresso(name);
        usuario.adicionarIngresso(compra);
        return compra;
    }

    public void cancelarCompraIngresso(String id) {
        usuario.cancelarIngresso(id);
    }


    public List<Ingresso> listarIngressosComprados() {
        return usuario.getIngressosCompradosU();
    }
}
