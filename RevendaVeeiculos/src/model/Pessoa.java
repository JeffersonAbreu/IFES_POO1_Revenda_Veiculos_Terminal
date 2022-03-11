package model;

public abstract class Pessoa {
    private int id;
    private String nome;
    private String cpf;

    public Pessoa(int id, String nome, String cpf) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
    }

    public String getNome() {
        return nome.toUpperCase();
    }

    public String getCpf() {
        return cpf;
    }

    public int getID() {
        return id;
    }
}
