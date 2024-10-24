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
    private transient List <Compra> ingressosComprados = new ArrayList<>();

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

    /**
     * Adiciona usuário à lista de cadastro.
     *
     * @param usuario o usuário a ser cadastrado
     * @throws JaCadastradoException se o usuário já estiver cadastrado
     */
    public void cadastroDeUsuarios (Usuario usuario) {
        usuariosCadastrados.add(usuario);
    }

    /**
     * Verifica o login do usuário.
     *
     * @param login  o login do usuário
     * @param senha  a senha do usuário
     * @return true se o login e senha estiverem corretos
     * @throws CredencialInvalidaException se o login ou a senha estiverem incorretos
     * @throws NaoEncontradoException se o usuário não for encontrado
     */
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

    /**
     * Retorna true se o usuário for administrador.
     *
     * @return true se o usuário for administrador, caso contrário, false
     */
    public boolean isAdmin() {
        return adm;
    }

    /**
     * Desloga o usuário.
     */
    public void logout() {
        setLogado(false);
    }

    /**
     * Adiciona uma forma de pagamento à lista de formas de pagamento do usuário.
     *
     * @param pagamento a forma de pagamento a ser adicionada
     */
    public void adicionaFormaDePagamento (Pagamento pagamento) {
        formasDePagamento.add(pagamento);  // Adiciona o pagamento à lista

    }

    /**
     * Remove uma forma de pagamento da lista de formas de pagamento do usuário.
     *
     * @param pagamento a forma de pagamento a ser removida
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
     * @param pagamento a forma de pagamento a ser escolhida
     * @return a forma de pagamento escolhida
     * @throws NaoEncontradoException se a forma de pagamento não for encontrada
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
     * @param compra a compra a ser adicionada
     */
    public void adicionarCompras(Compra compra) {
        this.compra = compra;
        ingressosComprados.add(compra);
    }

    /**
     * Remove um ingresso da lista de ingressos comprados pelo usuário.
     *
     * @param ingresso o ingresso a ser cancelado
     * @return true se o ingresso foi cancelado com sucesso, caso contrário, false
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
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Método equals sobrescrito para comparar dois objetos Usuario.
     * Dois usuários são iguais se o login, CPF e email forem iguais.
     *
     * @param o o objeto a ser comparado
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
     * @return a lista de ingressos comprados
     */
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

    /**
     * Define a nova senha do usuário.
     *
     * @param senha A nova senha a ser definida.
     * @throws IllegalStateException Se o usuário não estiver logado.
     */
    public void setSenha(String senha) {
        this.senha = senha;
    }

    /**
     * Define o novo nome do usuário.
     *
     * @param nome O novo nome a ser definido.
     * @throws IllegalStateException Se o usuário não estiver logado.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Define o novo email do usuário.
     *
     * @param email O novo email a ser definido.
     * @throws IllegalStateException Se o usuário não estiver logado.
     */
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
