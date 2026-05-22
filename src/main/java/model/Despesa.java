package model;

public class Despesa extends Transacao {

    public Despesa(
            String descricao,
            double valor,
            String data,
            String categoria
    ) {

        super(descricao, valor, data, categoria);
    }

    @Override
    public double calcularImpacto() {
        return -getValor();
    }
}