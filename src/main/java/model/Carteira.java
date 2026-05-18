package model;

public class Carteira {

    private double saldo;

    public Carteira() {
        this.saldo = 0;
    }

    public void adicionarSaldo(double valor) {
        saldo += valor;
    }

    public void gastar(double valor) {
        saldo -= valor;
    }

    public double getSaldo() {
        return saldo;
    }
}