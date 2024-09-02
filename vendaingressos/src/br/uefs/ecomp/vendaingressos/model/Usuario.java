package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private List<Ingresso> ingressosComprados;
    private static List<Usuario> usuariosCadastrados;
    private static List<Usuario> usuariosLogados;

    public Usuario() {
    }

    public Usuario(String login, String senha, String nome, String cpf, String email) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        ingressosComprados = new ArrayList<>();
        usuariosCadastrados = new ArrayList<>();
        usuariosLogados = new ArrayList<>();
    }

    public void cadastroDeUsuarios (Usuario usuario) {
        usuariosCadastrados.add(usuario);
    }

    public Usuario logarUsuario (String login, String senha) {
        Iterator<Usuario> cadastroIterator = getUsuariosCadastrados().iterator();
        while (cadastroIterator.hasNext()) {
            Usuario nextUsuario = cadastroIterator.next();
            if (nextUsuario.getLogin().equals(login)) {
                usuariosLogados.add(this);
                return nextUsuario;
            }
        }
        return null;
    }

    public void logoutUsuario () {
        Iterator<Usuario> loginIterator = usuariosLogados.iterator();
        while (loginIterator.hasNext()) {
            Usuario userAtual = loginIterator.next();
            if (userAtual.equals(this)) {
                loginIterator.remove();
                return;
            }
        }
    }

    /*
    Adiciona ingresso comprado pelo usuário na lista de ingressos do usuário e na lista de ingressos comprados
    do evento correspondente, se houver um evento associado ao ingresso.
     */
    public void adicionarIngressoComprado(Ingresso ingresso) {
        Ingresso i = ingresso;
        ingressosComprados.add(ingresso);
        if (i.getEvento() == null) {
            return;
        } else {
            i.getEvento().adicionaIngressoComprado(i);
        }
    }

    // Aqui também
    public void cancelarIngressoComprado(String id) {
        // O erro ConcurrentModificationException ocorre precisamente quando tentamos quebrar uma regra e alterar a
        // lista enquanto iteramos por ela. Em Java, para remover elementos durante a iteração, você precisa usar um objeto
        // especial - um iterador (classe Iterator). A turma Iteratoré responsável por percorrer com segurança uma lista de elementos.

        Iterator<Ingresso> iterator = ingressosComprados.iterator();
        while (iterator.hasNext()) {
            Ingresso ingreso = iterator.next();
            if ((ingreso.getId() == null && ingreso.getEvento() != null) || (ingreso.getId() != null && ingreso.getId().equals(id))) {
                iterator.remove(); // Remove o ingresso sem causar ConcurrentModificationException
            }
        }
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public String getEmail() {
        return email;
    }

    public static List<Usuario> getUsuariosCadastrados() {
        return usuariosCadastrados;
    }

    public List<Ingresso> getIngressosComprados() {
        return ingressosComprados;
    }

    public List<Ingresso> getIngressos() {
        return ingressosComprados;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
