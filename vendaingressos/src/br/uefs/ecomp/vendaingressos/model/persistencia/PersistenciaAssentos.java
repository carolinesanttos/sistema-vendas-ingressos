package br.uefs.ecomp.vendaingressos.model.persistencia;

import br.uefs.ecomp.vendaingressos.model.Evento;
import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaAssentos {

    private String caminhoArquivo;

    public PersistenciaAssentos(String caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public void salvarDados(List<Evento> assentos, String caminhoArquivo) {
        Gson gson = new Gson();
        FileWriter writer = null;
        try {
            // Tenta abrir o arquivo para escrita
            writer = new FileWriter(caminhoArquivo);
            // Converte a lista de eventos para JSON e escreve no arquivo
            gson.toJson(assentos, writer);
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
        FileReader reader = null;
        try {
            // Tenta abrir o arquivo para leitura
            reader = new FileReader(caminhoArquivo);
            // Lê o JSON do arquivo e converte para um array de Eventos
            Evento[] eventosArray = gson.fromJson(reader, Evento[].class);
            // Retorna a lista de eventos
            return new ArrayList<>(List.of(eventosArray));
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
