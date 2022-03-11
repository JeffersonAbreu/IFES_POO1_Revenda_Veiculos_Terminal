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
import interfaces.Negociante;
import model.Cliente;
import model.Pessoa;
import model.Revenda;
import model.Veiculo;
import model.Vendedor;

public abstract class BKP {

    public static void limparTela() {
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

    public static int menuCompraeVenda(boolean isCliente, boolean isCompra, Cliente cliente, Vendedor vendedor,
            Scanner input) {
        int op = 0, x;
        boolean alternativa = false;
        boolean confirma;
        do {
            do {
                limparTela();
                x = showVeiculosClienteOuVendedor(isCliente, isCompra, cliente, vendedor);
                if (x == 1) {
                    System.out.printf("\nDIGITE O NUMERO REFERENTE A ESCOLHA, 1 ou 0 pra VOLTAR: ");
                } else {
                    System.out.printf("\nDIGITE O NUMERO REFERENTE A ESCOLHA, 1 à %d ou 0 pra VOLTAR: ", x);
                }
                try {
                    op = Integer.parseInt(input.nextLine());
                    alternativa = (op == 0) ? true : false;
                    if (!alternativa) {
                        alternativa = (op >= 1 && op <= x) ? true : false;
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
            if (op == 0) {
                return 0;
            } else {
                Negociante proprietario = null;
                boolean isNFE = true;
                if (isCliente) {
                    proprietario = (isCompra) ? vendedor : cliente;
                    isNFE = (isCompra) ? false : true;
                } else {
                    proprietario = (!isCompra) ? vendedor : cliente;
                    isNFE = (!isCompra) ? false : true;
                }
                Veiculo veiculo = proprietario.getVeiculo(op);
                confirma = geraNota(isNFE, cliente, vendedor, veiculo, input);
                if (confirma) {
                    if (isCliente) {
                        if (isCompra) {
                            cliente.comprar(vendedor, veiculo);
                        } else {
                            cliente.vender(vendedor, veiculo);
                        }
                    } else {
                        if (isCompra) {
                            vendedor.comprar(cliente, veiculo);
                        } else {
                            vendedor.vender(cliente, veiculo);
                        }
                    }
                }
            }
        } while (!confirma);
        return op;
    }

    public static boolean geraNota(boolean entrada, Cliente cliente, Vendedor vendedor, Veiculo veiculo,
            Scanner input) {
        int op = 0;
        boolean alternativa;
        do {
            limparTela();
            if (entrada) {
                showNFE(veiculo, cliente, vendedor);
            } else {
                showNFS(veiculo, cliente, vendedor);
            }

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

    private static void showNFS(Veiculo veiculo, Cliente cliente, Vendedor vendedor) {
        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar cal = Calendar.getInstance();
        final String data = df.format(cal.getTime());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "PROPRIETÁRIO:", "CPF/CNPJ:", "RESPONSÁVEL");
        System.out.printf("| %-30s | %-30s | %-30s |\n", vendedor.getRevenda().getNome().toUpperCase(),
                vendedor.getRevenda().getCnpj(), vendedor.getNome());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "COMPRADOR:", "CPF/CNPJ:", "");
        System.out.printf("| %-30s | %-30s | %-30s |\n", cliente.getNome().toUpperCase(), cliente.getCpf(), "");
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "VEICULO:", "COR PREDOMINANTE:", "CATEGORIA:");
        System.out.printf("| %-30s | %-30s | %-30s |\n", veiculo.getNome().toUpperCase(), veiculo.getCor(),
                veiculo.getCategoria());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("  %-30s | %-30s | %-30s |\n", "", "DATA:", "VALOR:");
        System.out.printf("  %-30s | %30s | %30.2f |\n", "", data, veiculo.getValor());
        System.out.printf("  %-30s +-%-30s-+-%-30s-+\n", "", "-".repeat(30), "-".repeat(30));
    }

    private static void showNFE(Veiculo veiculo, Cliente cliente, Vendedor vendedor) {
        final DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        final Calendar cal = Calendar.getInstance();
        final String data = df.format(cal.getTime());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "PROPRIETÁRIO:", "CPF/CNPJ:", "");
        System.out.printf("| %-30s | %-30s | %-30s |\n", cliente.getNome().toUpperCase(), cliente.getCpf(), "");
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "COMPRADOR:", "CPF/CNPJ:", "RESPONSÁVEL");
        System.out.printf("| %-30s | %-30s | %-30s |\n", vendedor.getRevenda().getNome().toUpperCase(),
                vendedor.getRevenda().getCnpj(), vendedor.getNome());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("| %-30s | %-30s | %-30s |\n", "VEICULO:", "COR PREDOMINANTE:", "CATEGORIA:");
        System.out.printf("| %-30s | %-30s | %-30s |\n", veiculo.getNome().toUpperCase(), veiculo.getCor(),
                veiculo.getCategoria());
        System.out.printf("+-%-30s-+-%-30s-+-%-30s-+\n", "-".repeat(30), "-".repeat(30), "-".repeat(30));
        System.out.printf("  %-30s | %-30s | %-30s |\n", "", "DATA:", "VALOR:");
        System.out.printf("  %-30s | %30s | %30.2f |\n", "", data, veiculo.getValor());
        System.out.printf("  %-30s +-%-30s-+-%-30s-+\n", "", "-".repeat(30), "-".repeat(30));
    }

    private static int menuCompraeVenda(boolean isCliente, Cliente cliente, Vendedor vendedor, Scanner input) {
        int op = 0;
        String menu = "";
        boolean comprar = false, vender = false, alternativa = false;
        do {
            limparTela();
            showVeiculosClienteOuVendedor(cliente, vendedor);
            if (isCliente) {
                comprar = (vendedor.getVeiculos().size() == 0) ? false : true;
                vender = (cliente.getVeiculos().size() == 0) ? false : true;
            } else {
                comprar = (cliente.getVeiculos().size() == 0) ? false : true;
                vender = (vendedor.getVeiculos().size() == 0) ? false : true;
            }
            if (comprar) {
                System.out.printf("[ %2d  ] Comprar\n", 1);
                menu = "1";
            }
            if (vender) {
                System.out.printf("[ %2d  ] Vender\n", 2);
                menu = "2";
            }
            if (comprar && vender)
                menu = "1 ou 2";

            System.out.printf("\nDIGITE O NUMERO REFERENTE A ESCOLHA, " + menu + " ou 0 pra VOLTAR: ");
            try {
                op = Integer.parseInt(input.nextLine());
                alternativa = (op == 0) ? true : false;
                if (!alternativa) {
                    if (comprar) {
                        alternativa = ((op == 1) ? true : false) || alternativa;
                    }
                    if (vender) {
                        alternativa = ((op == 2) ? true : false) || alternativa;
                    }
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
        return op;
    }

    private static void espera(int x) {
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

    private static int menuClienteOuVendedor(Cliente cliente, Vendedor vendedor, Scanner input) throws SaindoException {
        int op = 0;
        int x;
        do {
            limparTela();
            showVeiculosClienteOuVendedor(cliente, vendedor);
            x = 0;
            System.out.printf("[ %2d  ] Cliente  - %s\n", ++x, cliente.getNome());
            System.out.printf("[ %2d  ] Vendedor - %s\n", ++x, vendedor.getNome());

            System.out.printf("\nDIGITE O NUMERO REFERENTE A ESCOLHA, 1 ou %d, ou 0 pra VOLTAR: ", x);
            try {
                op = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
        } while (op < 0 || op > x);
        return op;
    }

    private static int showVeiculosClienteOuVendedor(boolean isCliente, boolean isCompra, Cliente cliente,
            Vendedor vendedor) {
        String cn, vn;
        int lines = 0, x = 1;
        int max = 0;
        boolean listarAEsqueda;
        lines = (cliente.getVeiculos().size() > lines) ? cliente.getVeiculos().size() : lines;
        lines = (vendedor.getVeiculos().size() > lines) ? vendedor.getVeiculos().size() : lines;
        for (Veiculo v : cliente.getVeiculos()) {
            max = (v.getNome().length() > max) ? v.getNome().length() : max;
        }
        for (Veiculo v : vendedor.getVeiculos()) {
            max = (v.getNome().length() > max) ? v.getNome().length() : max;
        }
        if (isCliente) {
            listarAEsqueda = (isCompra) ? false : true;
            cn = (isCompra) ? "Comprando" : "Vendendo";
            vn = (!isCompra) ? "Comprando" : "Vendendo";
        } else {
            listarAEsqueda = (isCompra) ? true : false;
            cn = (!isCompra) ? "Comprando" : "Vendendo";
            vn = (isCompra) ? "Comprando" : "Vendendo";
        }
        String cabecalho = "%-9s %-" + (max) + "s %-13s";
        String cabecalhoID = "%3s %-9s %-" + (max) + "s %-13s";
        String titulo = "%-" + max + "s %-9s %-13s";
        String tituloID = "%-3s %-" + max + "s %-9s %-13s";
        String format = "%-" + max + "s %9.2f %-13s";
        String formatID = "%3d %-" + max + "s %9.2f %-13s";
        if (listarAEsqueda) {
            System.out.printf(cabecalhoID, " ".repeat(3), "CLIENTE:", cliente.getNome(), cn);
            System.out.print(" | ");
            System.out.printf(cabecalho, "VENDEDOR:", vendedor.getNome(), vn);
            System.out.printf("\n%3s %" + max + "s %9s %13s", ("-").repeat(3), ("-").repeat(max), ("-").repeat(9),
                    ("-").repeat(13));
            System.out.print(" | ");
            System.out.printf("%" + max + "s %9s %13s\n", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
            System.out.printf(tituloID, "ID", "NOME", "VALOR", "COR");
            System.out.print(" | ");
            System.out.printf(titulo, "NOME", "VALOR", "COR");
            System.out.printf("\n%3s %" + max + "s %9s %13s", ("-").repeat(3), ("-").repeat(max), ("-").repeat(9),
                    ("-").repeat(13));
            System.out.print(" | ");
            System.out.printf("%" + max + "s %9s %13s\n", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
        } else {
            System.out.printf(cabecalho, "CLIENTE:", cliente.getNome(), cn);
            System.out.print(" | ");
            System.out.printf(cabecalhoID, " ".repeat(3), "VENDEDOR:", vendedor.getNome(), vn);
            System.out.printf("\n%" + max + "s %9s %13s", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
            System.out.print(" | ");
            System.out.printf("%3s %" + max + "s %9s %13s\n", ("-").repeat(3), ("-").repeat(max), ("-").repeat(9),
                    ("-").repeat(13));
            System.out.printf(titulo, "NOME", "VALOR", "COR");
            System.out.print(" | ");
            System.out.printf(tituloID, "ID", "NOME", "VALOR", "COR");
            System.out.printf("\n%" + max + "s %9s %13s", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
            System.out.print(" | ");
            System.out.printf("%3s %" + max + "s %9s %13s\n", ("-").repeat(3), ("-").repeat(max), ("-").repeat(9),
                    ("-").repeat(13));
        }
        Veiculo v;
        for (int i = 0; i < lines; i++) {
            if (i >= cliente.getVeiculos().size()) {
                if (listarAEsqueda)
                    System.out.printf("%3s %" + max + "s %9s %13s", (" ").repeat(3), (" ").repeat(max),
                            (" ").repeat(9),
                            (" ").repeat(13));
                else
                    System.out.printf("%" + max + "s %9s %13s", (" ").repeat(max), (" ").repeat(9), (" ").repeat(13));
            } else {
                v = cliente.getVeiculos().get(i);
                if (listarAEsqueda)
                    System.out.printf(formatID, x++, v.getNome(), v.getValor(), v.getCor().name());
                else
                    System.out.printf(format, v.getNome(), v.getValor(), v.getCor().name());
            }
            System.out.print(" | ");
            if (!(i >= vendedor.getVeiculos().size())) {
                v = vendedor.getVeiculos().get(i);
                if (!listarAEsqueda)
                    System.out.printf(formatID, x++, v.getNome(), v.getValor(), v.getCor().name());
                else
                    System.out.printf(format, v.getNome(), v.getValor(), v.getCor().name());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
        return x - 1;
    }

    private static void showVeiculosClienteOuVendedor(Cliente cliente, Vendedor vendedor) {
        int lines = 0;
        lines = (cliente.getVeiculos().size() > lines) ? cliente.getVeiculos().size() : lines;
        lines = (vendedor.getVeiculos().size() > lines) ? vendedor.getVeiculos().size() : lines;
        int max = 0;
        for (Veiculo v : cliente.getVeiculos()) {
            max = (v.getNome().length() > max) ? v.getNome().length() : max;
        }
        for (Veiculo v : vendedor.getVeiculos()) {
            max = (v.getNome().length() > max) ? v.getNome().length() : max;
        }
        String cabecalho = "%-9s %-" + (max) + "s %-13s";
        String titulo = "%-" + max + "s %-9s %-13s";
        String format = "%-" + max + "s %9.2f %-13s";
        System.out.printf(cabecalho, "CLIENTE:", cliente.getNome(), " ");
        System.out.print(" | ");
        System.out.printf(cabecalho, "   VENDEDOR:", vendedor.getNome(), " ");
        System.out.println();
        System.out.println();
        System.out.printf(titulo, "NOME", "VALOR", "COR");
        System.out.print(" | ");
        System.out.printf(titulo, "NOME", "VALOR", "COR");
        System.out.printf("\n%" + max + "s %9s %13s", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
        System.out.print(" | ");
        System.out.printf("%" + max + "s %9s %13s\n", ("-").repeat(max), ("-").repeat(9), ("-").repeat(13));
        Veiculo v;
        for (int i = 0; i < lines; i++) {
            if (i >= cliente.getVeiculos().size()) {
                System.out.printf("%" + max + "s %9s %13s", (" ").repeat(max), (" ").repeat(9), (" ").repeat(13));
            } else {
                v = cliente.getVeiculos().get(i);
                System.out.printf(format, v.getNome(), v.getValor(), v.getCor().name());
            }
            System.out.print(" | ");
            if (i >= vendedor.getVeiculos().size()) {
                System.out.printf("%" + max + "s %9s %13s", "", "", "");
            } else {
                v = vendedor.getVeiculos().get(i);
                System.out.printf(format, v.getNome(), v.getValor(), v.getCor().name());
            }
            System.out.println();
        }
        System.out.println();
        System.out.println();
    }

    private static Cliente menuCliente(ArrayList<Cliente> clientes, Scanner input) throws SaindoException {
        int op = 0;
        int x;
        do {
            limparTela();
            // MENU
            x = 0;
            for (Cliente cliente : clientes) {
                System.out.printf("[ %2d  ] - %s\n", ++x, cliente.getNome());
            }
            System.out.printf("\nDIGITE O NUMERO REFERENTE AO CLIENTE, DE 1 à %d ou 0 pra SAIR: ", x);
            try {
                op = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
            if (op == 0) {
                throw new SaindoException();
            }
        } while (op > x || op < 0);
        return clientes.get(op - 1);
    }

    public static void start(Revenda revenda, ArrayList<Cliente> clientes) {
        Scanner input = new Scanner(System.in);
        int op = 0;
        boolean cadastroSelected = false, negociacaoSelected = false;
        Vendedor vendedor = null;
        Cliente cliente = null;
        try {
            while (true) {
                vendedor = menuVendedor(revenda.getVendedores(), input);
                do {
                    if (!cadastroSelected && !negociacaoSelected)
                        op = opMenuPrincipal(input);
                    else if (cadastroSelected)
                        op = opMenuCadastro(input);
                    else if (cadastroSelected)
                        op = opMenuNegociacao(input);

                    try {
                        Thread.sleep(5000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    // cliente = menuCliente(clientes, input);
                    // op = menuClienteOuVendedor(cliente, vendedor, input);
                    // if (op != 0) {
                    // isCliente = (op == 1) ? true : false;
                    // op = menuCompraeVenda(isCliente, cliente, vendedor, input);
                    // if (op != 0) {
                    // isCompra = (op == 1) ? true : false;
                    // op = menuCompraeVenda(isCliente, isCompra, cliente, vendedor, input);
                    // if (op != 0) {
                    // System.out.print("\n Salvando Registro.");
                    // espera(9);
                    // }
                    // }
                    // }

                    if (op == 1) {
                        cadastroSelected = true;
                        negociacaoSelected = false;
                    } else if (op == 2) {
                        cadastroSelected = false;
                        negociacaoSelected = true;
                    }
                } while (op != 0);
            }
        } catch (SaindoException e) {
            System.out.print(e.getMessage());
            espera(5);
            System.exit(0);

        } finally {
            input.close();
        }
    }

    private static int opMenuNegociacao(Scanner input) {
        return 0;
    }

    private static int opMenuCadastro(Scanner input) throws SaindoException {
        int op = 0;
        do {
            menuPrincipal();
            menuCadastro();
            try {
                System.out.print("   Digite uma opção: ");
                op = Integer.parseInt(input.nextLine());
                if ((op > 2) || (op < 0))
                    throw new OpcaoInvalidaException();

            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
        } while (op > 4 || op < 0);
        return op;
    }

    private static void menuCadastro() {
    }

    private static int opMenuPrincipal(Scanner input) throws SaindoException {
        int op = 0;
        do {
            menuPrincipal();
            System.out.print("   Digite uma opção: ");
            try {
                op = Integer.parseInt(input.nextLine());
                if ((op > 2) || (op < 0)) {
                    throw new OpcaoInvalidaException();
                }
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.print(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
        } while (op > 2 || op < 0);
        return op;
    }

    // ###############################################################################################
    private static Vendedor menuVendedor(List<Vendedor> list, Scanner input) throws SaindoException {
        return (Vendedor) menuPessoa((List) list, "VENDEDOR", input);
    }

    private static Cliente menuCliente(List<Pessoa> list, Scanner input) throws SaindoException {
        return (Cliente) menuPessoa((List) list, "CLIENTE", input);
    }

    private static Pessoa menuPessoa(List<Pessoa> list, String titulo, Scanner input) throws SaindoException {
        int op = 0;
        int x = list.size();
        do {
            menu(list, titulo);
            try {
                op = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
        } while (op > x || op < 0);
        return list.get(op - 1);
    }

    private static void menu(List<Pessoa> list, String titulo) {
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
        System.out.print("   Digite uma opção: ");
    }

    private static void menuNegociacao() {
        int x = 0, max = 16;
        limparTela();
        // MENU
        System.out.printf("\n");
        System.out.printf("##-- SELECIONE UMA %-8s%s-##\n\n", "AÇÃO", "-".repeat(max - 15));
        System.out.printf("+-------------%s+\n", "-".repeat(max));
        System.out.printf("| Opção %2d - %-" + (max) + "s |\n", ++x, "VENDER");
        System.out.printf("| Opção %2d - %-" + (max) + "s |\n", ++x, "COMPRAR");
        System.out.printf("| Opção %2d - %-" + (max) + "s |\n", 0, "Sair");
        System.out.printf("+-------------%s+\n", "-".repeat(max));
        System.out.print("   Digite uma opção: ");
    }

    private static Pessoa menuNegociacao(List<Pessoa> list, String titulo, Scanner input) throws SaindoException {
        int op = 0;
        int x = list.size();
        do {
            menu(list, titulo);
            try {
                op = Integer.parseInt(input.nextLine());
            } catch (Exception e) {
                try {
                    throw new OpcaoInvalidaException();
                } catch (OpcaoInvalidaException e1) {
                    System.out.println(e1.getMessage());
                    espera(5);
                    op = -1;
                }
            }
            if (op == 0) {
                throw new SaindoException();
            }
        } while (op > x || op < 0);
        return list.get(op - 1);
    }

    private static void menuPrincipal() {
        int x = 0;
        limparTela();
        // MENU
        System.out.printf("\n");
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(22), "-".repeat(22), "-".repeat(22), "-".repeat(31));
        System.out.printf("| %2d - %-15s | %2d - %-15s | %2d - %-15s | %s |\n", ++x, "CADASTRO", ++x, "NEGOCIAÇÃO", 0,
                "SAIR", " ".repeat(29));
        System.out.printf("+%s+%s+%s+%s+\n", "-".repeat(22), "-".repeat(22), "-".repeat(22), "-".repeat(31));
    }
}