package service;

import model.Transacao;
import model.Receita;
import model.Despesa;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.io.BufferedReader;
import java.io.FileReader;

import java.util.ArrayList;
import java.util.List;

public class ArquivoService {

    private static final String ARQUIVO =
            "transacoes.txt";

    public static void salvar(
            List<Transacao> transacoes
    ) {

        try {

            BufferedWriter writer =
                    new BufferedWriter(
                            new FileWriter(ARQUIVO)
                    );

            for (Transacao t : transacoes) {

                writer.write(
                        t.getDescricao()
                                + ";"
                                + t.getValor()
                                + ";"
                                + t.getCategoria()
                                + ";"
                                + t.getData()
                );

                writer.newLine();
            }

            writer.close();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    public static List<Transacao> carregar() {

        List<Transacao> transacoes =
                new ArrayList<>();

        try {

            BufferedReader reader =
                    new BufferedReader(
                            new FileReader(ARQUIVO)
                    );

            String linha;

            while ((linha = reader.readLine()) != null) {

                String[] partes = linha.split(";");

                String descricao = partes[0];

                double valor =
                        Double.parseDouble(partes[1]);

                String categoria = partes[2];

                String data = partes[3];

                Transacao transacao;

                if (
                        categoria.equals("SALÁRIO")
                                || categoria.equals("INVESTIMENTO")
                                || categoria.equals("PRESENTE")
                ) {

                    transacao = new Receita(
                            descricao,
                            valor,
                            data,
                            categoria
                    );

                } else {

                    transacao = new Despesa(
                            descricao,
                            valor,
                            data,
                            categoria
                    );
                }

                transacoes.add(transacao);
            }

            reader.close();

        } catch (IOException e) {

            e.printStackTrace();
        }

        return transacoes;
    }
}