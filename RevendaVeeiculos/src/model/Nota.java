package model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Nota {
    private TipoNota tipo;// Entrada ou saida
    private String dataHora;
    private double valor;
    private String veiculoNome;
    private String veiculoCor;
    private String veiculoCategoria;
    private String proprietario;
    private String proprietarioDOC;
    private String comprador;
    private String compradorDOC;
    private String resposavel;

    public Nota(TipoNota tipo, Veiculo veiculo, Pessoa vendedor, Pessoa comprador) {
        if (tipo == TipoNota.SAIDA) {
            this.comprador = comprador.getNome();
            this.compradorDOC = comprador.getCpf();
            this.proprietario = ((Vendedor) vendedor).getRevenda().getNome();
            this.proprietarioDOC = ((Vendedor) vendedor).getRevenda().getCnpj();
            this.resposavel = vendedor.getNome();
        } else {
            this.comprador = ((Vendedor) comprador).getRevenda().getNome();
            this.compradorDOC = ((Vendedor) comprador).getRevenda().getCnpj();
            this.proprietario = vendedor.getNome();
            this.proprietarioDOC = vendedor.getCpf();
            this.resposavel = comprador.getNome();
        }
        this.tipo = tipo;
        final DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        final Calendar cal = Calendar.getInstance();
        this.dataHora = df.format(cal.getTime());
        this.valor = veiculo.getValor();
        this.veiculoNome = veiculo.getNome();
        this.veiculoCor = veiculo.getCor().name();
        this.veiculoCategoria = veiculo.getCategoria();
    }

    public Nota(String line) {
        String[] part = line.split(";");
        this.tipo = (part[0].equals(TipoNota.SAIDA.name()) ? TipoNota.SAIDA : TipoNota.ENTRADA);
        this.dataHora = part[1];
        this.valor = Double.parseDouble(part[2]);
        this.veiculoNome = part[3];
        this.veiculoCor = part[4];
        this.veiculoCategoria = part[5];
        this.proprietario = part[6];
        this.proprietarioDOC = part[7];
        this.comprador = part[8];
        this.compradorDOC = part[9];
        this.resposavel = part[10];
    }

    public String toLine() {
        StringBuffer format = new StringBuffer();
        format.append("%s;");
        format.append("%s;");
        format.append("%.2f;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s;");
        format.append("%s");
        return String.format(format.toString(), tipo.name(), dataHora, valor, veiculoNome, veiculoCor, veiculoCategoria,
                proprietario, proprietarioDOC, comprador, compradorDOC, resposavel);
    }
}
