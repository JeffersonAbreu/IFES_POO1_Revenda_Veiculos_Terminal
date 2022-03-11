package model;

import utils.Cor;

public class Sedan extends Carro {
    public Sedan(int id, String nome, double valor, Cor cor, int idProprietario) {
        super(id, nome, valor, cor, Sedan.class.getSimpleName(), idProprietario);
    }
}
