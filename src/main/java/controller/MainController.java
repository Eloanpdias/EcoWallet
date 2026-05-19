package controller;

import javafx.fxml.FXML;
import javafx.scene.control.*;

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
    public void initialize() {

        comboTipo.getItems().addAll(
                "Receita",
                "Despesa"
        );
    }

    @FXML
    private void adicionarTransacao() {

        String descricao = txtDescricao.getText();

        String valor = txtValor.getText();

        String tipo = comboTipo.getValue();

        lblSaldo.setText(
                "Última transação: "
                        + descricao
                        + " | "
                        + valor
                        + " | "
                        + tipo
        );
    }
}