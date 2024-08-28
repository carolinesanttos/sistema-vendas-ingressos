package br.uefs.ecomp.vendaingressos.model;

public class Compra {
    private Usuario usuario;
    private Ingresso ingresso;
    private String data;

    public Compra(Usuario usuario, Ingresso ingresso, String data) {
        this.usuario = usuario;
        this.ingresso = ingresso;
        this.data = data;
    }



    public Usuario getUsuario() {
        return usuario;
    }

    public Ingresso getIngresso() {
        return ingresso;
    }

    public String getData() {
        return data;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public void setIngresso(Ingresso ingresso) {
        this.ingresso = ingresso;
    }

    public void setData(String data) {
        this.data = data;
    }
}
