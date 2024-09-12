// Sistema Operacional: Windows 10 - 64 Bits
// Versão Da Linguagem: Java
// Autor: Caroline Santos de Jesus
// Componente Curricular: Algoritmos II
// Concluido em: 12/09/2024
// Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
// colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
// Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
// código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

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
