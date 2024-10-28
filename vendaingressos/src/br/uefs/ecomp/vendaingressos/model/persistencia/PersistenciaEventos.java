package br.uefs.ecomp.vendaingressos.model.persistencia;

import br.uefs.ecomp.vendaingressos.model.Evento;
import br.uefs.ecomp.vendaingressos.model.Ingresso;
import br.uefs.ecomp.vendaingressos.model.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Classe responsável por gerenciar a persistência de dados de eventos em um arquivo JSON.
 * Permite salvar e carregar listas de eventos para manter os dados persistentes.
 */
public class PersistenciaEventos{

    private String caminhoArquivo;

    public PersistenciaEventos(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    /**
     * Salva uma lista de eventos ativos no arquivo especificado em formato JSON.
     *
     * @param eventosAtivos A lista de eventos a ser salva no arquivo.
     * @throws RuntimeException Se ocorrer algum erro ao escrever os dados no arquivo.
     */
    public void salvarDados(List<Evento> eventosAtivos) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            writer = new FileWriter(caminhoArquivo);
            gson.toJson(eventosAtivos, writer);
        } catch (IOException e) {
            throw new RuntimeException("Não foi possível salvar os dados em " + caminhoArquivo + ": " + e.getMessage());
        } finally {
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
     * Carrega uma lista de eventos a partir do arquivo especificado em formato JSON.
     *
     * @return Uma lista de objetos {@code Evento} carregada do arquivo.
     * @throws RuntimeException Se ocorrer algum erro ao ler os dados do arquivo.
     */
    public List<Evento> carregarDados() {
        Gson gson = new Gson();
        FileReader reader = null; // Inicializa o leitor como nulo
        try {
            // Tenta abrir o arquivo para leitura
            reader = new FileReader(caminhoArquivo);
            // Define o tipo como List<Evento>
            Type eventoListType = new TypeToken<List<Evento>>() {}.getType();
            // Lê o JSON do arquivo e converte diretamente para uma lista de eventos
            return gson.fromJson(reader, eventoListType);
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
