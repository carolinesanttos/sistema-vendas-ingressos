package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    Usuario usuario;
    Evento evento;
    Ingresso ingresso;
    private List<Evento> eventosCadastrados = new ArrayList<>();
    private List<Ingresso> ingressosComprados = new ArrayList<>();

    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdm) {
        usuario = new Usuario(login, senha, nome, cpf, email, isAdm);
        usuario.cadastroDeUsuarios(usuario);
        return usuario;
    }

    public Evento cadastrarEvento(Usuario usuario, String nomeDoEvento, String descricao, Date data) throws SecurityException{
        evento = new Evento(usuario, nomeDoEvento, descricao, data);
        try {
            evento.cadastroDeEventos(evento);
            eventosCadastrados.add(evento);
        } catch (SecurityException e) {
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        return evento;

    }

    public void adicionarAssentoEvento(String nomeDoEvento, String assento) {
        evento = evento.encontrarEventoPorNome(nomeDoEvento);
        if (evento!= null) {
            evento.adicionarAssento(assento);
        }
    }

    public Ingresso comprarIngresso(Usuario usuario, String nomeDoEvento, String assento) {
        ingresso = evento.venderIngresso(usuario, nomeDoEvento, assento);
        ingressosComprados.add(ingresso);
        return ingresso;
    }

    public boolean cancelarCompra(Usuario usuario, Ingresso ingresso) {
        return usuario.cancelarIngressoComprado(ingresso);
    }

    public List<Evento> listarEventosDisponiveis() {
        return getEventosCadastrados();
    }

    public List<Ingresso> listarIngressosComprados(Usuario usuario) {
        return getIngressosComprados();
    }

    public List<Evento> getEventosCadastrados() {
        return eventosCadastrados;
    }

    public List<Ingresso> getIngressosComprados() {
        return ingressosComprados;
    }


}
