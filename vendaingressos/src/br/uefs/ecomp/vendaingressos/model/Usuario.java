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
    List<Ingresso> ingressos = new ArrayList<>();


    public Usuario(String login, String senha, String nome, String cpf, String email) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
    }

    public void adicionarIngresso(Ingresso ingresso) {
        ingressos.add(ingresso);
    }

    public List<Ingresso> getIngressos() {
        return ingressos;
    }

    public void cancelarIngresso(String id) {

        // O erro ConcurrentModificationException ocorre precisamente quando tentamos quebrar uma regra e alterar a
        // lista enquanto iteramos por ela. Em Java, para remover elementos durante a iteração, você precisa usar um objeto
        // especial - um iterador (classe Iterator). A turma Iteratoré responsável por percorrer com segurança uma lista de elementos.

        Iterator<Ingresso> ingressosIterator = ingressos.iterator(); // cria um iterador

        while(ingressosIterator.hasNext()) { // contanto que haja elementos na lista

            Ingresso nextIngresso = ingressosIterator.next(); // obter o próximo elemento
            if (nextIngresso.getId().equals(id)) {
                ingressosIterator.remove(); // exclua o ingresso com o nome desejado
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
