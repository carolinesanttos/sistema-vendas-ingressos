/**
 * Sistema Operacional: Windows 10 - 64 Bits
 * IDE: IntelliJ
 * Versão Da Linguagem: Java JDK 22
 * Autor: Caroline Santos de Jesus
 * Componente Curricular: Algoritmos II
 * Concluído em: 28/10/2024
 * Declaro que este código foi elaborado por mim de forma individual e não contém nenhum trecho de código de outro
 * colega ou de outro autor, tais como provindos de livros e apostilas, e páginas ou documentos eletrônicos da Internet.
 * Qualquer trecho de código de outra autoria que não a minha está destacado com uma citação para o autor e a fonte do
 * código, e estou ciente que estes trechos não serão considerados para fins de avaliação.
 */

package br.uefs.ecomp.vendaingressos.model.persistencia;

import br.uefs.ecomp.vendaingressos.model.Usuario;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Classe responsável por gerenciar a persistência de dados de usuários em um arquivo JSON.
 * Permite salvar e carregar listas de usuários para manter os dados persistentes.
 */
public class PersistenciaUsuarios {

    private String caminhoArquivo;

    public PersistenciaUsuarios(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Salva uma lista de usuários no arquivo especificado em formato JSON.
     *
     * @param usuarios A lista de usuários a ser salva no arquivo.
     * @throws RuntimeException Se ocorrer algum erro ao escrever os dados no arquivo.
     */
    public void salvarDados(List<Usuario> usuarios) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            // Tenta abrir o arquivo para escrita
            writer = new FileWriter(caminhoArquivo);
            // Converte a lista de eventos para JSON e escreve no arquivo
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            // Lança uma nova exceção com uma mensagem de erro
            throw new RuntimeException("Não foi possível salvar os dados em " + caminhoArquivo + ": " + e.getMessage());
        } finally {
            // Fecha o escritor se não for nulo
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

    /**
     * Carrega uma lista de usuários a partir do arquivo especificado em formato JSON.
     *
     * @return Uma lista de objetos {@code Usuario} carregada do arquivo.
     * @throws RuntimeException Se ocorrer algum erro ao ler os dados do arquivo.
     */
    public List<Usuario> carregarDados() {
        Gson gson = new Gson();
        FileReader reader = null;
        try {
            // Tenta abrir o arquivo para leitura
            reader = new FileReader(caminhoArquivo);
            // Lê o JSON do arquivo e converte para um array de Eventos
            Usuario[] usuariosArray = gson.fromJson(reader, Usuario[].class);
            // Retorna a lista de eventos
            return new ArrayList<>(List.of(usuariosArray));
        } catch (IOException e) {
            // Lança uma nova exceção com uma mensagem de erro
            throw new RuntimeException("Não foi possível carregar os dados de " + caminhoArquivo + ": " + e.getMessage());
        } finally {
            // Fecha o leitor se não for nulo
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException("Erro ao fechar o arquivo: " + e.getMessage());
                }
            }
        }
    }

}
