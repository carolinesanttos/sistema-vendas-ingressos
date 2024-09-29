package br.uefs.ecomp.vendaingressos.model;

public class UserAdministrador extends Usuario{

    public UserAdministrador (String login, String senha, String nome, String cpf, String email, boolean adm) {
        super(login, senha, nome, cpf, email, adm);
    }

}
