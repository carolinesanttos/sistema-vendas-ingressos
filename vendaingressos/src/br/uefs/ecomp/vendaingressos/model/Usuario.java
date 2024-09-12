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

    // Adiciona usuário à lista de cadastro.
    public void cadastroDeUsuarios (Usuario usuario) {
        usuariosCadastrados.add(usuario);
    }

    // Verifica login do usuário, retornando true se o login e senha estiverem corretos.
    public boolean login(String login, String senha) {
        return this.login.equals(login) && this.senha.equals(senha);
    }

//    Adiciona ingresso comprado pelo usuário na lista de ingressos comprados por ele.
    public void adicionarIngressoComprado(Ingresso ingresso) {
        ingressosComprados.add(ingresso);
    }

    // Este método remove um ingresso da lista de ingressos comprados pelo usuário
    public boolean cancelarIngressoComprado(Ingresso ingresso) {
        // Remove compra da lista do usuário.
        Iterator<Ingresso> compraIngressoUsuario = ingressosComprados.iterator();
        while (compraIngressoUsuario.hasNext()) { // Percorre a lista de ingressos comprados pelo usuário
            Ingresso i = compraIngressoUsuario.next();
            if (i.equals(ingresso)) { // Verifica se o ingresso atual é o mesmo que precisa ser removido
                compraIngressoUsuario.remove(); // Remove o ingresso da lista do usuário sem causar ConcurrentModificationException
                i.setStatus(false); // Marca o ingresso como cancelado (status false)
                return true; // Retorna true para indicar que o ingresso foi removido
            }
        }
        return false; // Retorna false caso o ingresso não tenha sido encontrado na lista
    }

//   Sobrescreve o método equals para comparar dois objetos Usuario.
//   Dois usuários são considerados iguais se o login, cpf e email forem iguais.
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

    // Sobrescreve o método hashCode para garantir a coerência com o método equals.
    @Override
    public int hashCode() {
        return Objects.hash(login, cpf, email);
    }

    public String getLogin() {
        return login;
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

    public List<Ingresso> getIngressos() {
        return ingressosComprados;
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
