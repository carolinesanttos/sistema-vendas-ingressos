// Sistema Operacional: Windows 10 - 64 Bits
// IDE: IntelliJ
// Versão Da Linguagem: Java JDK 22
// Autor: Caroline Santos de Jesus
// Componente Curricular: Algoritmos II
// Concluido em: 12/09/2024
// Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
// colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
// Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
// código, e estou ciente que estes trechos não serão considerados para fins de avaliação.

package br.uefs.ecomp.vendaingressos.model;

import br.uefs.ecomp.vendaingressos.model.excecao.CredencialInvalidaException;
import br.uefs.ecomp.vendaingressos.model.excecao.JaCadastradoException;
import br.uefs.ecomp.vendaingressos.model.excecao.NaoEncontradoException;
import com.google.gson.annotations.Expose;

import java.util.*;
import java.util.List;

public class Usuario {
    private String login;
    private String senha;
    private String nome;
    private String cpf;
    private String email;
    private boolean adm;
    private boolean isLogado;
    private Compra compra;
    private static List<Usuario> usuariosCadastrados = new ArrayList<>();
    private List<Pagamento> formasDePagamento = new ArrayList<>();
    private List <Compra> ingressosComprados = new ArrayList<>();

    // Construtor que inicializa todos os atributos do usuário ao criar um novo objeto.
    public Usuario(String login, String senha, String nome, String cpf, String email, boolean adm) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.adm = adm;
        this.isLogado = false;
    }

    // Retorna true se o usuário for administrador.
    public boolean isAdmin() {
        return adm;
    }

    // Adiciona usuário à lista de cadastro.
    public void cadastroDeUsuarios (Usuario usuario) {
        boolean contemUsuario = usuariosCadastrados.contains(usuario);
        if (!contemUsuario) {
            usuariosCadastrados.add(usuario);
        } else {
            throw new JaCadastradoException("Usuário já cadastrado.");
        }
    }

    // Verifica login do usuário, retorna true se o login e senha estiverem corretos.
    public boolean login(String login, String senha) {
        // Verifica se o usuário está cadastrado
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getLogin().equals(login)) {
                // Se o usuário estiver cadastrado, verifica a senha
                if (usuario.getSenha().equals(senha)) {
                    usuario.setLogado(true);
                    return true; // Usuário logado com sucesso
                }
                throw new CredencialInvalidaException("Login ou senha inválidos.");
            }
        }
        throw new NaoEncontradoException("Usuário não encontrado."); // Usuário não está cadastrado
    }

    public void logout() {
        setLogado(false); // Desloga o usuário
    }

    public void adicionarCompras(Compra compra) {
        this.compra = compra;
        ingressosComprados.add(compra);
    }

    // Remove um ingresso da lista de ingressos comprados pelo usuário.
    public boolean cancelarIngressoComprado(Ingresso ingresso, Pagamento pagamento) {
        Iterator<Compra> iterator = ingressosComprados.iterator();

        while (iterator.hasNext()) {
            Compra bilhetes = iterator.next();

            if (ingresso.equals(bilhetes.getIngresso())) {
                boolean cancelar = ingresso.cancelarIngresso();

                if (cancelar) {
                    iterator.remove();
                    compra.cancelarCompra(bilhetes, pagamento);
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public void adicionaFormaDePagamento (Pagamento pagamento) {
        if (this.isLogado) {
            if (pagamento != null) {
                formasDePagamento.add(pagamento);  // Adiciona o pagamento à lista
            }
        } else {
            System.out.println("É necessário estar logado para realizar essa ação.");
        }
    }

    public void removerFormaDePagamento (Pagamento pagamento) {
        if (formasDePagamento.contains(pagamento)) {
            formasDePagamento.remove(pagamento);
        } else {
            throw new NaoEncontradoException("Forma de pagamento não encontrada.");
        }

    }

    public Pagamento escolheFormaPagamento (Pagamento pagamento) {
        boolean contemPagamento = formasDePagamento.contains(pagamento);
        if (contemPagamento) {
            return pagamento;
        } else {
            throw new NaoEncontradoException("Forma de pagamento não encontrada");
        }
    }

    // Método equals sobrescrito para comparar dois objetos Usuario.
    // Dois usuários são iguais se o login, cpf e email forem iguais.
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

    public static void limparUsuariosCadastrados() {
        usuariosCadastrados.clear();
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

    public String getSenha() {
        return senha;
    }

    public List<Ingresso> getIngressos() {
        List <Ingresso>ingressosComprados = new ArrayList<>();
        for (Compra compra : this.ingressosComprados) {
            Ingresso ingresso = compra.getIngresso();
            if (ingresso != null) {
                ingressosComprados.add(ingresso);
            }
        }
        return ingressosComprados; // Retorna a lista de ingressos comprados
    }


    public List<Pagamento> getFormasDePagamento() {
        return formasDePagamento;
    }

    public List<Compra> getCompras() {
        return ingressosComprados;
    }

    public Compra getCompra() {
        return compra;
    }

    public static List<Usuario> getUsuariosCadastrados() {
        return usuariosCadastrados;
    }

    public boolean isLogado() {
        return isLogado;
    }

    public void setSenha(String senha) {
        if (this.isLogado) {
            this.senha = senha;
        } else {
            System.out.println("É necessário estar logado para realizar essa ação.");
        }
    }

    public void setNome(String nome) {
        if (this.isLogado) {
            this.nome = nome;
        } else {
            System.out.println("É necessário estar logado para realizar essa ação.");
        }
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setEmail(String email) {
        if (this.isLogado) {
            this.email = email;
        } else {
            System.out.println("É necessário estar logado para realizar essa ação.");
        }
    }

    public void setLogado(boolean logado) {
        isLogado = logado;
    }
}
