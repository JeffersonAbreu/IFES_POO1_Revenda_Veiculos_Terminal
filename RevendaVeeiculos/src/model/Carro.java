package model;

import utils.Cor;

public abstract class Carro extends Veiculo {
    public Carro(int id, String nome, double valor, Cor cor, String categoria, int idProprietario) {
        super(id, nome, valor, cor, categoria, idProprietario);
    }
}
