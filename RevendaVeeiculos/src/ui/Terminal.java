package ui;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Scanner;

import exceptions.OpcaoInvalidaException;
import exceptions.SaindoException;
import model.Cliente;
import model.Pessoa;
import model.Revenda;
import model.Veiculo;
import model.Vendedor;
import utils.Util;

public class Terminal {
    private Revenda revenda;
    ArrayList<Cliente> clientes;
    Vendedor vendedor;
    private boolean PRODUCAO = false;
    private String SAIR = "SAIR";
    private String VOLTAR = "VOLTAR";

    public Terminal(Revenda revenda, ArrayList<Cliente> clientes) {
        this.revenda = revenda;
        this.clientes = clientes;
        PRODUCAO = false;
        this.start();
    }

    public void limparTela() {
        // Limpa a tela no windows, no linux e no MacOS
        try {
            if (System.getProperty("os.name").contains("Windows"))
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            else
                Runtime.getRuntime().exec("clear");
        } catch (InterruptedException e) {
        } catch (IOException e) {
        }
    }

    private boolean geraNota(boolean entrada, Cliente cliente, Vendedor vendedor, Veiculo veiculo,
            Scanner input) {
        int op = 0;
        boolean alternativa;
        do {
            limparTela();
            System.out.printf("+%s+\n", "-".repeat(100));
            System.out.printf("|%s|\n", " ".repeat(100));
            if (entrada) {
                System.out.printf("|%s%s%s|\n", " ".repeat(41), " ENTRADA - VEICULO", " ".repeat(41));
                showNFE(veiculo, cliente, vendedor);
            } else {
                System.out.printf("|%s%s%s|\n", " ".repeat(41), " SAIDA - VEICULO  ", " ".repeat(41));
                showNFS(veiculo, cliente, vendedor);
            }
            System.out.printf("|%s|\n", " ".repeat(100));
            System.out.printf("+%s+\n", "-".repeat(100));
            printLines(13 + 5);

            System.out.printf("\nNEGOCIAÇÃO DIGITE: 1 CONFIRMA ou 0 pra CANCELA: ");
            try {
                op = Integer.parseInt(input.nextLine());
                alternativa = (op == 0) ? true : false;
                if (!alternativa) {
                    alternativa = (op == 1) ? true : false;
                }
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    alternativa = false;
                }
            }
        } while (!alternativa);
        return (op == 0) ? false : true;
    }

    private void showNFS(Veiculo veiculo, Cliente cliente, Vendedor vendedor) {
        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar cal = Calendar.getInstance();
        final String data = df.format(cal.getTime());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "PROPRIETÁRIO:", "CPF/CNPJ:", "RESPONSÁVEL");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", vendedor.getRevenda().getNome().toUpperCase(),
                vendedor.getRevenda().getCnpj(), vendedor.getNome());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "COMPRADOR:", "CPF/CNPJ:", "");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", cliente.getNome().toUpperCase(), cliente.getCpf(), "");
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "VEICULO:", "COR PREDOMINANTE:", "CATEGORIA:");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", veiculo.getNome().toUpperCase(), veiculo.getCor(),
                veiculo.getCategoria());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "", "DATA:", "VALOR:");
        System.out.printf("|  %-30s | %30s | %30.2f  |\n", "", data, veiculo.getValor());
        System.out.printf("|  %-30s +-%-30s-+-%-30s--|\n", "", "-".repeat(30), "-".repeat(30));
    }

    private void showNFE(Veiculo veiculo, Cliente cliente, Vendedor vendedor) {
        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar cal = Calendar.getInstance();
        final String data = df.format(cal.getTime());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "PROPRIETÁRIO:", "CPF/CNPJ:", "");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", cliente.getNome().toUpperCase(), cliente.getCpf(), "");
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "COMPRADOR:", "CPF/CNPJ:", "RESPONSÁVEL");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", vendedor.getRevenda().getNome().toUpperCase(),
                vendedor.getRevenda().getCnpj(), vendedor.getNome());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "VEICULO:", "COR PREDOMINANTE:", "CATEGORIA:");
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", veiculo.getNome().toUpperCase(), veiculo.getCor(),
                veiculo.getCategoria());
        System.out.printf("|--%-30s-+-%-30s-+-%-30s--|\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("|  %-30s | %-30s | %-30s  |\n", "", "DATA:", "VALOR:");
        System.out.printf("|  %-30s | %30s | %30.2f  |\n", "", data, veiculo.getValor());
        System.out.printf("|  %-30s +-%-30s-+-%-30s--|\n", "", "-".repeat(30), "-".repeat(30));
    }

    private void espera(int x) {
        try {
            int i = 0;
            do {
                System.out.print(" .");
                Thread.sleep(100);
            } while (x != i++);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        int op = -2, opOld = -1;
        Cliente cliente = null;
        Veiculo veiculo = null;
        boolean isMenuCadastro = false, isSubMenu;
        try {
            if (PRODUCAO)
                this.vendedor = revenda.getVendedores().get(1);
            else
                this.vendedor = opMenuVendedor(revenda.getVendedores(), input);

            do {
                isSubMenu = true;
                switch (op) {
                    case 1:
                        menuCadastro();
                        op = opcaoMenu(input, 4);
                        isMenuCadastro = true;
                        break;
                    case 2:
                        menuNegociacao();
                        op = opcaoMenu(input, 4);
                        isMenuCadastro = false;
                        break;
                    case 3:
                        if (isMenuCadastro) {
                            listarClientes(0);
                            op = opcaoMenu(input, 2);
                        } else {
                            if (cliente == null) {
                                listarClientes(2);
                                op = opcaoMenu(input, 2 + clientes.size());
                                if (op > 2) {
                                    cliente = clientes.get(op - 3);
                                } else if (op == 0) {
                                    cliente = null;
                                }
                            }
                            if (cliente != null) {
                                if (veiculo == null) {
                                    listarVeiculos(cliente.getVeiculos(), 2);
                                    op = opcaoMenu(input, 2 + cliente.getVeiculos().size());
                                    if (op > 2) {
                                        veiculo = cliente.getVeiculos().get(op - 3);
                                    } else if (op == 0 || cliente.getVeiculos().isEmpty()) {
                                        veiculo = null;
                                        cliente = null;
                                    }
                                }
                            }
                            if ((cliente != null) && (veiculo != null)) {
                                if (geraNota(true, cliente, vendedor, veiculo, input)) {
                                    vendedor.comprar(cliente, veiculo);
                                    atualizarArquivoVeiculos();
                                    op = -2;
                                    cliente = null;
                                }
                                veiculo = null;
                            }
                        }
                        break;
                    case 4:
                        if (isMenuCadastro) {
                            listarVeiculos(revenda.getVeiculos(), 0);
                            op = opcaoMenu(input, 2);
                        } else {
                            if (veiculo == null) {
                                listarVeiculos(revenda.getVeiculos(), 2);
                                op = opcaoMenu(input, 2 + revenda.getVeiculos().size());
                                if (op > 2) {
                                    veiculo = revenda.getVeiculos().get(op - 3);
                                } else if (op == 0 || revenda.getVeiculos().isEmpty()) {
                                    veiculo = null;
                                }
                            }
                            if (veiculo != null) {
                                if (cliente == null) {
                                    listarClientes(2);
                                    op = opcaoMenu(input, 2 + clientes.size());
                                    if (op > 2) {
                                        cliente = clientes.get(op - 3);
                                    } else if (op == 0) {
                                        veiculo = null;
                                        cliente = null;
                                    }
                                }
                            }
                            if ((cliente != null) && (veiculo != null)) {
                                if (geraNota(false, cliente, vendedor, veiculo, input)) {
                                    vendedor.vender(cliente, veiculo);
                                    atualizarArquivoVeiculos();
                                    op = -2;
                                    veiculo = null;
                                }
                                cliente = null;
                            }
                        }
                        break;
                    default:
                        menuPrincipal(SAIR);
                        printLines(0);
                        op = opcaoMenu(input, 2);
                        isSubMenu = false;
                        break;
                }
                if (op != -1)
                    opOld = op;
                else if (op == -1)
                    op = opOld;
                if (isSubMenu && op == 0)
                    op = -2;
            } while (op != 0);

            if (op == 0)
                throw new SaindoException();
        } catch (SaindoException e) {
            System.out.print(e.getMessage());
            espera(5);
            System.exit(0);

        } finally {
            input.close();
        }
    }

    private void atualizarArquivoVeiculos() {
        List<Veiculo> veiculos = new ArrayList<Veiculo>();
        for (Veiculo v : revenda.getVeiculos())
            veiculos.add(v);

        for (Cliente c : clientes)
            for (Veiculo v : c.getVeiculos())
                veiculos.add(v);

        Util.writeVeiculos(veiculos);
    }

    private void listarVeiculos(List<Veiculo> veiculos, int x) {
        menuPrincipal(VOLTAR);
        int lines = veiculos.size() + 4;
        System.out.printf("|%s%s%s|", " ".repeat(46), "VEICULOS", " ".repeat(46));
        String formatTitulo = "| %2s | %-36s | %-21s | %-16s | %-11s |";
        String format = "| %2s | %-36s | %-21s | %-16s | %11s |";
        String cabecalho = String.format(formatTitulo, "ID", "NOME", "CATEGORIA", "COR", "VALOR");
        String newLine = String.format("\n+-%2s-+-%-36s-+-%-21s-+-%-16s-+-%-11s-+", "-".repeat(2), "-".repeat(36),
                "-".repeat(21), "-".repeat(16), "-".repeat(11));
        System.out.printf(newLine);
        System.out.println();
        System.out.printf(cabecalho);
        System.out.printf(newLine);
        if (veiculos.size() == 0) {
            lines = 13;
            System.out.println();
            printLines(lines++);
            System.out.printf("|%s %s %s|\n", " ".repeat(35), "(  L I S T A    V A Z I A  )", " ".repeat(35));
            System.out.printf("+%s+\n", "-".repeat(100));
        } else {
            for (Veiculo v : veiculos) {
                System.out.println();
                System.out.printf(format, ++x, v.getNome(), v.getCategoria(), v.getCor().name(),
                        String.valueOf(v.getValor()));
            }
            System.out.println();
        }
        printLines(lines);
    }

    private void listarClientes(int x) {
        menuPrincipal(VOLTAR);
        int lines = clientes.size() + 4;
        System.out.printf("|%s%s%s|", " ".repeat(46), "CLIENTES", " ".repeat(46));
        String format = "| %2s | %-76s | %-14s |";
        String cabecalho = String.format(format, "ID", "NOME", "CPF");
        String newLine = String.format("\n+-%2s-+-%-76s-+-%-14s-+", "-".repeat(2), "-".repeat(76), "-".repeat(14));
        System.out.printf(newLine);
        System.out.println();
        System.out.printf(cabecalho);
        System.out.printf(newLine);
        for (Cliente c : clientes) {
            System.out.println();
            System.out.printf(format, ++x, c.getNome(), c.getCpf());
        }
        System.out.println();
        printLines(lines);
    }

    private void menuNegociacao() {
        menuPrincipal(VOLTAR);
        int x = 2, position = 22, lines = 3;
        System.out.printf("|%s| %2d - %-15s |%s|\n", " ".repeat(position), ++x, "COMPRAR", " ".repeat(76 - position));
        System.out.printf("|%s| %2d - %-15s |%s|\n", " ".repeat(position), ++x, "VENDER", " ".repeat(76 - position));
        System.out.printf("|%s+-%s-+%s|\n", " ".repeat(position), "-".repeat(20), " ".repeat(76 - position));
        printLines(lines);
    }

    private void menuCadastro() {
        menuPrincipal(VOLTAR);
        int x = 2;
        int lines = 3;
        System.out.printf("| %2d - %-15s | %s |\n", ++x, "LISTAR CLIENTES", " ".repeat(75));
        System.out.printf("| %2d - %-15s | %s |\n", ++x, "LISTAR VEICULOS", " ".repeat(75));
        System.out.printf("|-%s-+%s|\n", "-".repeat(20), " ".repeat(77));
        printLines(lines);
    }

    private int opcaoMenu(Scanner input, int x) {
        int op = 0;
        do {
            System.out.print("   Digite uma opção: ");
            try {
                op = Integer.parseInt(input.nextLine());
                if ((op > x) || (op < 0)) {
                    throw new OpcaoInvalidaException();
                }
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.print(e1.getMessage());
                    espera(5);
                    return -1;
                }
            }
        } while (op > x || op < 0);
        return op;
    }

    private void printLines(int lines) {
        lines = 20 - lines;
        while (lines-- != 0) {
            System.out.printf("| %s |\n", " ".repeat(98));
        }
        System.out.printf("+-%s-+\n", "-".repeat(98));
    }

    private void menuPrincipal(String esq) {
        limparTela();
        // MENU
        System.out.printf("\n");
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(22), "-".repeat(22), "-".repeat(22), "-".repeat(31));
        System.out.printf("| %2d - %-15s | %2d - %-15s | %2d - %-15s | %s |\n", 1, "CADASTRO", 2, "NEGOCIAÇÃO", 0,
                esq, " ".repeat(29));
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(22), "-".repeat(22), "-".repeat(22), "-".repeat(31));
    }

    // ###############################################################################################
    private Vendedor opMenuVendedor(List<Vendedor> list, Scanner input) throws SaindoException {
        return (Vendedor) opMenuPessoa((List) list, "VENDEDOR", input);
    }

    private Cliente opMenuCliente(List<Pessoa> list, Scanner input) throws SaindoException {
        return (Cliente) opMenuPessoa((List) list, "CLIENTE", input);
    }

    private Pessoa opMenuPessoa(List<Pessoa> list, String titulo, Scanner input) throws SaindoException {
        int op = 0;
        int x = list.size();
        do {
            menuPessoa(list, titulo);
            op = opcaoMenu(input, x);
        } while (op != 0);
        return list.get(op - 1);
    }

    private void menuPessoa(List<Pessoa> list, String titulo) {
        int x = 0, max = 16;
        limparTela();

        for (Pessoa p : list) {
            max = (p.getNome().length() > max) ? p.getNome().length() : max;
        }

        // MENU
        System.out.printf("\n");
        System.out.printf("##-- SELECIONE UM %-8s %s-##\n\n", titulo, "-".repeat(max - 15));
        System.out.printf("+-------------%s+\n", "-".repeat(max));
        for (Pessoa p : list)
            System.out.printf("| Opção %2d - %-" + (max) + "s |\n", ++x, p.getNome());
        System.out.printf("| Opção %2d - %-" + (max) + "s |\n", 0, "Sair");
        System.out.printf("+-------------%s+\n", "-".repeat(max));
    }
}