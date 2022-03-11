package model;

import java.util.ArrayList;
import java.util.List;

import utils.Cor;
import utils.Util;

public class Revenda {
    private int id;
    private String nome;
    private String cnpj;
    private List<Nota> entradas = new ArrayList<>();
    private List<Nota> saidas = new ArrayList<>();
    private List<Veiculo> veiculos = new ArrayList<>();
    private List<Vendedor> vendedores = new ArrayList<>();

    public String getNome() {
        return nome.toUpperCase();
    }

    public String getCnpj() {
        return cnpj;
    }

    public Revenda(String nome, String cnpj, List<Veiculo> veiculos) {
        this.id = 0;
        this.nome = nome;
        this.cnpj = cnpj;
        this.veiculos = veiculos;
    }

    public List<Nota> getNotasEntradas() {
        return entradas;
    }

    private void addEntrada(Nota entrada) {
        this.entradas.add(entrada);
        Util.writeNota(entrada);
    }

    public List<Nota> getNotasSaidas() {
        return saidas;
    }

    private void addSaida(Nota saida) {
        this.saidas.add(saida);
        Util.writeNota(saida);
    }

    public void comprar(Veiculo veiculo, Cliente vendedor, Vendedor comprador) {
        this.addEntrada(new Nota(TipoNota.ENTRADA, veiculo, vendedor, comprador));
        vendedor.getVeiculos().remove(veiculo);
        veiculo.setIdProprietario(getID());
        comprador.getVeiculos().add(veiculo);
    }

    public void vender(Veiculo veiculo, Vendedor vendedor, Cliente comprador) {
        this.addSaida(new Nota(TipoNota.SAIDA, veiculo, vendedor, comprador));
        vendedor.getVeiculos().remove(veiculo);
        veiculo.setIdProprietario(comprador.getID());
        comprador.getVeiculos().add(veiculo);
    }

    public Veiculo getVeiculo(int id) {
        return this.veiculos.get(id - 1);
    }

    public List<Veiculo> getVeiculos() {
        return getVeiculos(null);
    }

    public List<Veiculo> getVeiculos(Cor cor) {
        if (cor != null) {
            List<Veiculo> veiculos = new ArrayList<>();
            for (Veiculo veiculo : this.veiculos) {
                if (cor == veiculo.getCor()) {
                    veiculos.add(veiculo);
                }
            }
            return veiculos;
        }
        return this.veiculos;
    }

    private void mostrarVeiculos(Cor cor) {
        int id = 1;
        String addMsg = "TODOS";
        if (cor != null)
            addMsg = "Na COR - ".concat(cor.name());
        System.out.println();
        System.out.printf("Veículos da revenda %s : %s\n\n", this.nome, addMsg);
        for (Veiculo veiculo : getVeiculos(cor)) {
            Util.veiculoShow(id++, veiculo);
        }
        System.out.println();
    }

    public void mostrarTodosVeiculos() {
        mostrarVeiculos(null);
    }

    public void mostrarVeiculosNaCor(Cor cor) {
        mostrarVeiculos(cor);
    }

    public void addVendedor(Vendedor vendedor) {
        this.vendedores.add(vendedor);
    }

    public List<Vendedor> getVendedores() {
        return this.vendedores;
    }

    public int getID() {
        return id;
    }
}
