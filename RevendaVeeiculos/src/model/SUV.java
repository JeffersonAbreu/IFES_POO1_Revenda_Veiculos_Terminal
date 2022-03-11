package model;

import utils.Cor;

public class SUV extends Carro {

    public SUV(int id, String nome, double valor, Cor cor, int idProprietario) {
        super(id, nome, valor, cor, SUV.class.getSimpleName(), idProprietario);
    }
}
