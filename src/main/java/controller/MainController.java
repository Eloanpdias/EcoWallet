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

    @FXML
    private ComboBox<String> comboCategoria;

    @FXML
    private TableColumn<Transacao, String> colCategoria;

    @FXML
    private TableColumn<Transacao, String> colTipo;

    private ObservableList<Transacao> transacoes =
            FXCollections.observableArrayList();
    @FXML
    public void initialize() {

        datePicker.setEditable(false);

        colTipo.setCellFactory(column -> new TableCell<>() {

            @Override
            protected void updateItem(String item, boolean empty) {

                super.updateItem(item, empty);

                if (empty || item == null) {

                    setText(null);
                    setStyle("");

                } else {

                    setText(item);

                    if (item.contains("Receita")) {

                        setStyle("-fx-text-fill: green;");

                    } else {

                        setStyle("-fx-text-fill: red;");
                    }
                }
            }
        });

        colTipo.setCellValueFactory(cellData -> {

            if (cellData.getValue() instanceof Receita) {

                return new javafx.beans.property.SimpleStringProperty(
                        "+ Receita"
                );
            }

            return new javafx.beans.property.SimpleStringProperty(
                    "- Despesa"
            );
        });

        txtValor.textProperty().addListener((observable, oldValue, newValue) -> {

            if (!newValue.matches("\\d*(\\.\\d*)?")) {

                txtValor.setText(oldValue);
            }
        });

        comboTipo.setOnAction(event -> {

            comboCategoria.getItems().clear();

            String tipoSelecionado = comboTipo.getValue();

            if (tipoSelecionado == null) {
                return;
            }

            if (tipoSelecionado.equals("Receita")) {

                comboCategoria.getItems().addAll(
                        "SALÁRIO",
                        "INVESTIMENTO",
                        "PRESENTE",
                        "OUTROS"
                );

            } else {

                comboCategoria.getItems().addAll(
                        "TRANSPORTE",
                        "ALIMENTAÇÃO",
                        "SAÚDE",
                        "LAZER",
                        "MORADIA",
                        "OUTROS"
                );
            }
        });

        comboTipo.getItems().addAll(
                "Receita",
                "Despesa");

        colDescricao.setCellValueFactory(
                        new PropertyValueFactory<>("descricao")
        );

        colValor.setCellValueFactory(
                new PropertyValueFactory<>("valor")
        );

        colCategoria.setCellValueFactory(
                new PropertyValueFactory<>("categoria")
        );

        colData.setCellValueFactory(
                new PropertyValueFactory<>("data")
        );

        tableTransacoes.setItems(transacoes);
        colDescricao.setPrefWidth(200);

        colValor.setPrefWidth(100);

        colCategoria.setPrefWidth(120);

        colData.setPrefWidth(100);

        colTipo.setPrefWidth(100);

        tableTransacoes.setColumnResizePolicy(
                TableView.CONSTRAINED_RESIZE_POLICY
        );
    }

    @FXML
    private void adicionarTransacao() {

        if (
                txtDescricao.getText().isEmpty()
                        || txtValor.getText().isEmpty()
                        || comboTipo.getValue() == null
                        || datePicker.getValue() == null
                        || comboCategoria.getValue() == null
        ) {

            Alert alerta = new Alert(Alert.AlertType.ERROR);

            alerta.setTitle("Erro");

            alerta.setHeaderText("Campos vazios");

            alerta.setContentText(
                    "Preencha todos os campos!"
            );

            alerta.show();

            return;
        }

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
                    data,
                    comboCategoria.getValue()
            );

        } else {

            transacao = new Despesa(
                    descricao,
                    valor,
                    data,
                    comboCategoria.getValue()
            );
        }

        transacoes.add(transacao);

        atualizarSaldo();
    }

    @FXML
    private void removerTransacao() {

        Transacao transacaoSelecionada =
                tableTransacoes.getSelectionModel()
                        .getSelectedItem();

        if (transacaoSelecionada != null) {

            transacoes.remove(transacaoSelecionada);

            atualizarSaldo();
        }
    }

    private void atualizarSaldo() {

        txtDescricao.clear();

        txtValor.clear();

        comboTipo.setValue(null);

        datePicker.setValue(null);

        comboCategoria.setValue(null);

        double saldo = 0;

        for (Transacao t : transacoes) {

            saldo += t.calcularImpacto();
        }

        lblSaldo.setText(
                "Saldo: R$ " + saldo
        );
    }
}