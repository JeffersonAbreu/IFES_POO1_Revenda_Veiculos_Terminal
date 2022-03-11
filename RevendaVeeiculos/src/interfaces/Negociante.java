package interfaces;

import java.util.List;

import model.Pessoa;
import model.Veiculo;

public interface Negociante {
    /**
     * @param vendedor <b> Vendedor
     * @param veiculo  <b>Veículo
     */
    void comprar(Pessoa vendedor, Veiculo veiculo);

    /**
     * @param comprador <b> Comprador
     * @param veiculo   <b> Veículo
     */
    void vender(Pessoa comprador, Veiculo veiculo);

    /**
     * @param id <b> Código do Veículo
     * @return Veículo
     */
    Veiculo getVeiculo(int id);

    List<Veiculo> getVeiculos();
}
