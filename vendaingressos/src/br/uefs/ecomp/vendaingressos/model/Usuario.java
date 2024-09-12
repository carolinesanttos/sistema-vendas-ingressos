package br.uefs.ecomp.vendaingressos.model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private boolean adm;
    private List<Ingresso> ingressosComprados = new ArrayList<>();
    private static List<Usuario> usuariosCadastrados = new ArrayList<>();
    private static List<Usuario> usuariosLogados = new ArrayList<>();

    public Usuario(String login, String senha, String nome, String cpf, String email, boolean adm) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.adm = adm;
    }

    // Retorna true se o usuário for administrador.
    public boolean isAdmin() {
        return adm;
    }

    public boolean login(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

    // Adiciona usuário à lista de cadastro.
    public void cadastroDeUsuarios (Usuario usuario) {
        usuariosCadastrados.add(usuario);
    }

    /*
    Adiciona ingresso comprado pelo usuário na lista de ingressos do usuário e na lista de ingressos comprados
    do evento correspondente, se houver um evento associado ao ingresso.
     */
    public void adicionarIngressoComprado(Ingresso ingresso) {
        ingressosComprados.add(ingresso);
    }

    /*
    Este método remove um ingresso da lista de ingressos comprados pelo usuário e, caso o ingresso esteja associado a
    um evento, ele também é removido da lista de ingressos comprados do evento.
    */
    public boolean cancelarIngressoComprado(Ingresso ingresso) {
        // Remove compra da lista do usuário.
        Iterator<Ingresso> compraIngressoUsuario = ingressosComprados.iterator();
        while (compraIngressoUsuario.hasNext()) {
            Ingresso i = compraIngressoUsuario.next();
            if (i.equals(ingresso)) {
                compraIngressoUsuario.remove(); // Remove o ingresso sem causar ConcurrentModificationException
                i.setStatus(false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) o;
        return Objects.equals(login, usuario.login) && Objects.equals(cpf, usuario.cpf) && Objects.equals(email, usuario.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login, cpf, email);
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

    public void setAdm(boolean adm) {
        this.adm = adm;
    }

}
