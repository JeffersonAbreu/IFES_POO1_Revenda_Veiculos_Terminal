package model;

import utils.Cor;

public class Caminhonete extends Carro {
    public Caminhonete(int id, String nome, double valor, Cor cor, int idProprietario) {
        super(id, nome, valor, cor, Caminhonete.class.getSimpleName(), idProprietario);
    }
}
