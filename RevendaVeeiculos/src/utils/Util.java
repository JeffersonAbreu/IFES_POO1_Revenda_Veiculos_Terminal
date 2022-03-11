package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import model.Caminhonete;
import model.Hatch;
import model.Moto;
import model.Nota;
import model.SUV;
import model.Sedan;
import model.Veiculo;

public abstract class Util {
    public static void veiculoShow(int id, Veiculo v) {
        System.out.printf("%2d :  %-25s %s %9.2f - %s\n", id, v.getNome(), "-", v.getValor(), v.getCor().name());
    }

    public static String veiculoToString(Veiculo v) {
        String format = "ID: %2d ; Modelo: %-25s ; R$: %9.2f ; Cor: %-9s ; Categoria: %-11s ; ID Proprietario: %2d";
        return String.format(format, v.getId(), v.getNome(), v.getValor(), v.getCor().name(), v.getCategoria(),
                v.getIdProprietario());
    }

    public static List<Nota> readNotas() {
        Charset utf8 = StandardCharsets.UTF_8;
        List<Nota> notas = new ArrayList<>();
        // Le o arquivo
        FileReader ler = null;
        BufferedReader reader = null;
        try {
            ler = new FileReader("Registros.txt", utf8);
            reader = new BufferedReader(ler);
            String linha;
            while ((linha = reader.readLine()) != null) {
                System.out.println(linha);
                notas.add(new Nota(linha));
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                ler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Imprime confirmacao
        System.out.println("Feito =D");
        return notas;
    }

    public static void writeNota(Nota nota) {
        Charset utf8 = StandardCharsets.UTF_8;
        // Cria arquivo
        File file = new File("Registros.txt");
        BufferedWriter bw = null;
        try {
            // Se o arquivo nao existir, ele gera
            if (!file.exists()) {
                file.createNewFile();
            }

            // Prepara para escrever no arquivo
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), utf8, true);
            bw = new BufferedWriter(fw);

            // Escreve e fecha arquivo
            bw.write(nota.toLine());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static List<Veiculo> readVeiculos() {
        Charset utf8 = StandardCharsets.UTF_8;
        List<Veiculo> veiculos = new ArrayList<>();
        Veiculo veiculo = null;
        // Le o arquivo
        FileReader ler = null;
        BufferedReader reader = null;
        try {
            ler = new FileReader("Veiculos.txt", utf8);
            reader = new BufferedReader(ler);
            String linha;
            while ((linha = reader.readLine()) != null) {
                veiculo = lineToVeiculo(linha);
                veiculos.add(veiculo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
                ler.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return veiculos;
    }

    private static Veiculo lineToVeiculo(String linha) {
        Veiculo v = null;
        String aux;
        String[] strings = linha.split(" ; ");
        aux = strings[0];
        String[] s = aux.split(": ");
        int id = Integer.parseInt(s[1].trim());
        s = strings[1].split(": ");
        String nome = s[1].trim();
        s = strings[2].split(": ");
        double valor = Double.parseDouble(s[1].trim().replace(",", "."));
        s = strings[3].split(": ");
        String cor = s[1].trim();
        s = strings[4].split(": ");
        String categoria = s[1].trim();
        s = strings[5].split(": ");
        int idProprietario = Integer.parseInt(s[1].trim());

        Cor COR = Cor.valueOf(cor);
        if (Caminhonete.class.getSimpleName().toUpperCase().equals(categoria)) {
            v = new Caminhonete(id, nome, valor, COR, idProprietario);
        } else if (Hatch.class.getSimpleName().toUpperCase().equals(categoria)) {
            v = new Hatch(id, nome, valor, COR, idProprietario);
        } else if (Moto.class.getSimpleName().toUpperCase().equals(categoria)) {
            v = new Moto(id, nome, valor, COR, idProprietario);
        } else if (Sedan.class.getSimpleName().toUpperCase().equals(categoria)) {
            v = new Sedan(id, nome, valor, COR, idProprietario);
        } else if (SUV.class.getSimpleName().toUpperCase().equals(categoria)) {
            v = new SUV(id, nome, valor, COR, idProprietario);
        }
        return v;
    }

    public static void writeVeiculos(List<Veiculo> veiculos) {
        Charset utf8 = StandardCharsets.UTF_8;
        // Cria arquivo
        File file = new File("Veiculos.txt");
        BufferedWriter bw = null;
        try {
            // Se o arquivo nao existir, ele gera
            if (!file.exists()) {
                file.createNewFile();
            }

            // Prepara para escrever no arquivo
            FileWriter fw = new FileWriter(file.getAbsoluteFile(), utf8, false);
            bw = new BufferedWriter(fw);

            int size = 1;
            for (Veiculo veiculo : veiculos) {
                bw.write(veiculoToString(veiculo));
                if (veiculos.size() != size++)
                    bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}