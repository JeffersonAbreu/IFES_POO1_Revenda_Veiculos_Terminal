package model;

import utils.Cor;

public class Hatch extends Carro {
    public Hatch(int id, String nome, double valor, Cor cor, int idProprietario) {
        super(id, nome, valor, cor, Hatch.class.getSimpleName(), idProprietario);
    }
}
