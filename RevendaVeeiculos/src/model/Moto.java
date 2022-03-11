package model;

import utils.Cor;

public class Moto extends Veiculo {
    public Moto(int id, String nome, double valor, Cor cor, int idProprietario) {
        super(id, nome, valor, cor, Moto.class.getSimpleName(), idProprietario);
    }
}
