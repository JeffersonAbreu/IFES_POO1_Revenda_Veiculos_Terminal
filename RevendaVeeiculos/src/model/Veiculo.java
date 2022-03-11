package model;

import utils.Cor;

public abstract class Veiculo {
    private int id;
    private String nome;
    private double valor;
    private Cor cor;
    private String categoria;
    private int idProprietario;

    public Veiculo(int id, String nome, double valor, Cor cor, String categoria, int idProprietario) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.cor = cor;
        this.categoria = categoria;
        this.setIdProprietario(idProprietario);
    }

    public int getIdProprietario() {
        return idProprietario;
    }

    public void setIdProprietario(int idProprietario) {
        this.idProprietario = idProprietario;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria.toUpperCase();
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public double getValor() {
        return valor;
    }

    public Cor getCor() {
        return cor;
    }
}
