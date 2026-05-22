package model;

public class Receita extends Transacao {

    public Receita(
            String descricao,
            double valor,
            String data,
            String categoria
    ) {

        super(descricao, valor, data, categoria);
    }

    @Override
    public double calcularImpacto() {
        return getValor();
    }
}