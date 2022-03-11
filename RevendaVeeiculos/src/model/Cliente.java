package model;

import java.util.List;

import interfaces.Negociante;
import utils.Util;

public class Cliente extends Pessoa implements Negociante {
    private List<Veiculo> veiculos;

    public Cliente(int id, String nome, String cpf, List<Veiculo> lista) {
        super(id, nome, cpf);
        this.veiculos = lista;
    }

    @Override
    public void comprar(Pessoa vendedor, Veiculo veiculo) {
        comprar((Vendedor) vendedor, veiculo);
    }

    public void comprar(Vendedor vendedor, Veiculo veiculo) {
        vendedor.vender(this, veiculo);
    }

    @Override
    public void vender(Pessoa comprador, Veiculo veiculo) {
        vender((Vendedor) comprador, veiculo);
    }

    public void vender(Vendedor comprador, Veiculo veiculo) {
        comprador.comprar(this, veiculo);
    }

    @Override
    public Veiculo getVeiculo(int id) {
        return this.veiculos.get(id - 1);
    }

    @Override
    public List<Veiculo> getVeiculos() {
        return this.veiculos;
    }

    public void mostrarVeiculos() {
        int id = 1;
        System.out.printf("Veículos do(a) %s\n\n", this.getNome());
        for (Veiculo veiculo : getVeiculos()) {
            Util.veiculoShow(id++, veiculo);
        }
        System.out.println();
    }
}
