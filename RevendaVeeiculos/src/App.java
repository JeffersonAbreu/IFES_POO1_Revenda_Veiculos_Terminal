
import java.util.ArrayList;
import java.util.List;

import model.Cliente;
import model.Revenda;
import model.Veiculo;
import model.Vendedor;
import ui.Terminal;
import utils.Util;

public class App {
    public static void main(String[] args) throws Exception {
        List<Veiculo> veiculos = Util.readVeiculos();
        Cliente ana = new Cliente(1, "Ana Maria Pio", "10829727169", new ArrayList<Veiculo>());
        Cliente jose = new Cliente(2, "Jose", "29727133492", new ArrayList<Veiculo>());
        Cliente pedro = new Cliente(3, "Pedro", "19283402839", new ArrayList<Veiculo>());

        ArrayList<Cliente> clientes = new ArrayList<>();
        clientes.add(ana);
        clientes.add(pedro);
        clientes.add(jose);

        List<Veiculo> veiculosNewCar = new ArrayList<Veiculo>();
        for (Veiculo v : veiculos) {
            if (v.getIdProprietario() == 0) {
                veiculosNewCar.add(v);
            } else {
                for (Cliente c : clientes) {
                    if (v.getIdProprietario() == c.getID()) {
                        c.getVeiculos().add(v);
                    }
                }
            }
        }

        Revenda newCar = new Revenda("New Car", "19827281719272718", veiculosNewCar);
        newCar.addVendedor(new Vendedor(1, "José Filipe", "182739279820", newCar));
        newCar.addVendedor(new Vendedor(2, "Maria", "182732379820", newCar));
        newCar.addVendedor(new Vendedor(3, "Monique", "222739279820", newCar));
        newCar.addVendedor(new Vendedor(4, "Marcos", "189032379820", newCar));

        new Terminal(newCar, clientes);
    }
}