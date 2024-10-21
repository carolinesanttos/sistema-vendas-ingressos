package br.uefs.ecomp.vendaingressos.model.persistencia;

import br.uefs.ecomp.vendaingressos.model.Evento;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaEventos{

    private String caminhoArquivo;

    public PersistenciaEventos(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void salvarDados(List<Evento> eventosAtivos) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            // Tenta abrir o arquivo para escrita
            writer = new FileWriter(caminhoArquivo);
            // Converte a lista de eventos para JSON e escreve no arquivo
            gson.toJson(eventosAtivos, writer);
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
