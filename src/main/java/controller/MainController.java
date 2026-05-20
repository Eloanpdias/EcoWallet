package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

import model.Despesa;
import model.Receita;
import model.Transacao;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

public class MainController {

    @FXML
    private TextField txtDescricao;

    @FXML
    private TextField txtValor;

    @FXML
    private ComboBox<String> comboTipo;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label lblSaldo;

    @FXML
    private TableView<Transacao> tableTransacoes;

    @FXML
    private TableColumn<Transacao, String> colDescricao;

    @FXML
    private TableColumn<Transacao, Double> colValor;

    @FXML
    private TableColumn<Transacao, String> colData;

    private ObservableList<Transacao> transacoes =
            FXCollections.observableArrayList();
    @FXML
    public void initialize() {

        comboTipo.getItems().addAll(
                "Receita",
                "Despesa");

        colDescricao.setCellValueFactory(
                        new PropertyValueFactory<>("descricao")
        );

        colValor.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );

        colData.setCellValueFactory(
                new PropertyValueFactory<>("data")
        );

        tableTransacoes.setItems(transacoes);
    }

    @FXML
    private void adicionarTransacao() {

        String descricao =
                txtDescricao.getText();

        double valor =
                Double.parseDouble(
                        txtValor.getText()
                );

        String tipo =
                comboTipo.getValue();

        String data =
                datePicker.getValue().toString();

        Transacao transacao;

        if (tipo.equals("Receita")) {

            transacao = new Receita(
                    descricao,
                    valor,
                    data
            );

        } else {

            transacao = new Despesa(
                    descricao,
                    valor,
                    data
            );
        }

        transacoes.add(transacao);

        atualizarSaldo();
    }

    private void atualizarSaldo() {

        double saldo = 0;

        for (Transacao t : transacoes) {

            saldo += t.calcularImpacto();
        }

        lblSaldo.setText(
                "Saldo: R$ " + saldo
        );
    }
}