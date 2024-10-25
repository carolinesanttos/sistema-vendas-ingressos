/**
 * <p>
 * Sistema Operacional: Windows 10 - 64 Bits<br>
 * IDE: IntelliJ<br>
 * Versão Da Linguagem: Java JDK 22<br>
 * Autor: Caroline Santos de Jesus<br>
 * Componente Curricular: Algoritmos II<br>
 * Concluído em: 21/10/2024<br>
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 * </p>
 */

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

    public Usuario(String login, String senha, String nome, String cpf, String email, boolean adm) {
        this.login = login;
        this.senha = senha;
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.adm = adm;
        this.isLogado = false;
    }

    /**
     * Adiciona usuário à lista de cadastro.
     *
     * @param usuario será o usuário a ser cadastrado
     * @throws JaCadastradoException se usuário já estiver cadastrado
     */
    public void cadastroDeUsuarios (Usuario usuario) {
        usuariosCadastrados.add(usuario);
    }

    /**
     * Verifica as credenciais de login do usuário.
     *
     * @param login  será o login do usuário
     * @param senha  será a senha do usuário
     * @return true se login e senha estiverem corretos, caso contrário, false
     */
    public boolean login(String login, String senha) {
        if (this.login.equals(login) && this.senha.equals(senha)) {
            setLogado(true);
            return true;
        }
        return false;
    }

    /**
     * Verifica se usuário está cadastrado com o login e senha passados.
     *
     * @param login  será o login do usuário
     * @param senha  será a senha do usuário
     * @return true se usuário estiver cadastrado, caso contrário, false
     */
    public boolean isCasdastrado (String login, String senha) {
        for (Usuario usuario : usuariosCadastrados) {
            if (usuario.getLogin().equals(login)) {
                // Se o login estiver cadastrado, verifica a senha
                if (usuario.getSenha().equals(senha)) {
                    return true; // Usuário encontrado
                }
            }
        }
        return false; // Usuário não encontrado
    }

    /**
     * Retorna true se usuário for administrador.
     *
     * @return true se usuário for administrador, senão, false
     */
    public boolean isAdmin() {
        return adm;
    }

    /**
     * Desloga usuário.
     */
    public void logout() {
        setLogado(false);
    }

    /**
     * Adiciona uma forma de pagamento à lista de formas de pagamento do usuário.
     *
     * @param pagamento a forma de pagamento que será adicionada
     */
    public void adicionaFormaDePagamento (Pagamento pagamento) {
        formasDePagamento.add(pagamento);

    }

    /**
     * Remove forma de pagamento da lista de formas de pagamento do usuário.
     *
     * @param pagamento a forma de pagamento que será removida
     * @throws NaoEncontradoException se a forma de pagamento não for encontrada
     */
    public void removerFormaDePagamento (Pagamento pagamento) {
        if (formasDePagamento.contains(pagamento)) {
            formasDePagamento.remove(pagamento);
        } else {
            throw new NaoEncontradoException("Forma de pagamento não encontrada.");
        }

    }

    /**
     * Escolhe uma forma de pagamento da lista de formas de pagamento do usuário.
     *
     * @param pagamento forma de pagamento escolhida
     * @return forma de pagamento escolhida
     * @throws NaoEncontradoException caso a forma de pagamento não for encontrada
     */
    public Pagamento escolheFormaPagamento (Pagamento pagamento) {
        boolean contemPagamento = formasDePagamento.contains(pagamento);
        if (contemPagamento) {
            formasDePagamento.add(pagamento);
            return pagamento;
        } else {
            throw new NaoEncontradoException("Forma de pagamento não cadastrada.");
        }
    }

    /**
     * Adiciona uma compra à lista de ingressos comprados pelo usuário.
     *
     * @param compra compra que será adicionada
     */
    public void adicionarCompras(Compra compra) {
        this.compra = compra;
        ingressosComprados.add(compra);
    }

    /**
     * Remove ingresso da lista de ingressos comprados pelo usuário.
     *
     * @param ingresso ingresso que será cancelado
     * @return true se ingresso foi cancelado com sucesso, senão, false
     */
    public boolean cancelarIngressoComprado(Usuario usuario, Ingresso ingresso) {
        Iterator<Compra> iterator = ingressosComprados.iterator();

        while (iterator.hasNext()) {
            Compra ing = iterator.next();

            if (ing.getIngresso().equals(ingresso)) {
                boolean cancelar = ingresso.cancelarIngresso();

                if (cancelar) {
                    iterator.remove();
                    compra.cancelarCompra();
                    ingresso.getEvento().cancelarIngressoComprado(ingresso);
                    return true; // Retorna true se o ingresso foi cancelado
                }
            }
        }
        return false; // Retorna false se o ingresso não foi encontrado
    }

    /**
     * Método equals sobrescrito para comparar dois objetos Usuario.
     * Dois usuários são iguais se o login, CPF e email forem iguais.
     *
     * @param o objeto que será comparado
     * @return true se os objetos forem iguais, caso contrário, false
     */
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

    /**
     * Limpa a lista de usuários cadastrados.
     */
    public static void limparUsuariosCadastrados() {
        usuariosCadastrados.clear();
    }

    /**
     * Retorna a lista de ingressos comprados pelo usuário.
     *
     * @return lista de ingressos comprados
     */
    public List<Ingresso> getIngressos() {
        List <Ingresso>ingressosComprados = new ArrayList<>();
        for (Compra compra : this.ingressosComprados) {
            Ingresso ingresso = compra.getIngresso();
            if (ingresso != null) {
                ingressosComprados.add(ingresso);
            }
        }
        return ingressosComprados;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setEmail(String email) {
        this.email = email;

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

    public List<Pagamento> getFormasDePagamento() {
        return formasDePagamento;
    }

    public List<Compra> getIngressosComprados() {
        return ingressosComprados;
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

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setLogado(boolean logado) {
        isLogado = logado;
    }
}
