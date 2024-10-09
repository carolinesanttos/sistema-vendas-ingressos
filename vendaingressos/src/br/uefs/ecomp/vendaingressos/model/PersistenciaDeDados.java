package br.uefs.ecomp.vendaingressos.model;

import java.util.List;

public interface PersistenciaDeDados<T> {
    public void salvarDados (List<T> dados);
    public List<T> carregarDados();
}
