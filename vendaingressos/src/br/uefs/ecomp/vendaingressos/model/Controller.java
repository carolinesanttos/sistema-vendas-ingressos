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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Controller {

    Usuario usuario;
    Evento evento;
    Ingresso ingresso;
    private List<Evento> eventosCadastrados = new ArrayList<>();
    private List<Ingresso> ingressosComprados = new ArrayList<>();

    // Cadastra um novo usuário. Recebe as informações do usuário, cria um objeto do tipo "Usuario" e cadastra.
    public Usuario cadastrarUsuario(String login, String senha, String nome, String cpf, String email, boolean isAdm) {
        usuario = new Usuario(login, senha, nome, cpf, email, isAdm); // Cria novo usuário.
        usuario.cadastroDeUsuarios(usuario); // Cadastra usuário na lista de usuários.
        return usuario; // Retorna usuário criado.
    }

    // Cadastra um novo evento. Verifica se usuário tem permissão de administrador antes de cadastrar.
    public Evento cadastrarEvento(Usuario usuario, String nomeDoEvento, String descricao, Date data) throws SecurityException{
        evento = new Evento(usuario, nomeDoEvento, descricao, data); // Cria um novo evento com as informações dadas.
        try { // Tenta cadastrar evento.
            evento.cadastroDeEventos(evento);
            eventosCadastrados.add(evento); // Adiciona evento à lista de eventos cadastrados.
        } catch (SecurityException e) { // Se usuário não for administrador, lança uma exceção.
            throw new SecurityException("Somente administradores podem cadastrar eventos.");
        }
        return evento; // Retorna evento criado.

    }

    // Adiciona assento disponível a um evento específico. Procura o evento pelo nome e adiciona
    // o assento à lista de assentos disponíveis.
    public void adicionarAssentoEvento(String nomeDoEvento, String assento) {
        evento = evento.encontrarEventoPorNome(nomeDoEvento); // Encontra o evento pelo nome.
        if (evento!= null) { // Verifica se o evento foi encontrado.
            evento.adicionarAssento(assento); // Adiciona o assento ao evento.
        }
    }

    // Comprar ingresso para um evento. Cria novo ingresso associando usuário, evento e assento.
    public Ingresso comprarIngresso(Usuario usuario, String nomeDoEvento, String assento) {
        ingresso = evento.venderIngresso(usuario, nomeDoEvento, assento); // Cria um novo ingresso.
        ingressosComprados.add(ingresso); // Adiciona ingresso à lista de ingressos comprados.

        return ingresso; // Retorna ingresso criado.
    }

    // Cancela a compra de um ingresso.
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
