package com.ecowallet;

import model.Despesa;
import model.Receita;

public class Main {

    public static void main(String[] args) {

        Receita salario = new Receita(
                "Salário",
                3000,
                "18/05/2026",
                "SALÁRIO"
        );

        Despesa aluguel = new Despesa(
                "Aluguel",
                1200,
                "18/05/2026",
                "MORADIA"
        );

        double saldo =
                salario.calcularImpacto()
                        + aluguel.calcularImpacto();

        System.out.println("Saldo: R$ " + saldo);
    }
}