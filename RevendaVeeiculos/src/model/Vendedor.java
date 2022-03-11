package model;

import java.util.List;

import interfaces.Negociante;
import utils.Cor;

public class Vendedor extends Pessoa implements Negociante {
    private Revenda revenda;

    public Vendedor(int id, String nome, String cpf, Revenda revenda) {
        super(id, nome, cpf);
        this.revenda = revenda;
    }

    @Override
    public void comprar(Pessoa vendedor, Veiculo veiculo) {
        revenda.comprar(veiculo, (Cliente) vendedor, this);
    }

    @Override
    public void vender(Pessoa comprador, Veiculo veiculo) {
        revenda.vender(veiculo, this, (Cliente) comprador);
    }

    @Override
    public Veiculo getVeiculo(int id) {
        return revenda.getVeiculo(id);
    }

    @Override
    public List<Veiculo> getVeiculos() {
        return revenda.getVeiculos(null);
    }

    public List<Veiculo> getVeiculos(Cor cor) {
        return revenda.getVeiculos(cor);
    }

    public void mostrarVeiculos(Cor cor) {
        revenda.mostrarVeiculosNaCor(cor);
    }

    public void mostrarTodosVeiculos() {
        revenda.mostrarTodosVeiculos();
    }

    public Revenda getRevenda() {
        return revenda;
    }
}
