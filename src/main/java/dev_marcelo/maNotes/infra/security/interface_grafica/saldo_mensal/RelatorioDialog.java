package dev_marcelo.maNotes.infra.security.interface_grafica.saldo_mensal;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.time.Year;
import java.util.Optional;
import java.util.stream.IntStream;

public class RelatorioDialog {

    public static Optional<Pair<Integer, Integer>> show() {
        Dialog<Pair<Integer, Integer>> dialog = new Dialog<>();
        dialog.setTitle("Gerar Relatório Mensal");
        dialog.setHeaderText("Selecione o mês e o ano");

        ButtonType gerarButtonType = new ButtonType("Gerar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(gerarButtonType, ButtonType.CANCEL);

        ComboBox<String> mesComboBox = new ComboBox<>();
        mesComboBox.getItems().addAll(
                "Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
                "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"
        );
        mesComboBox.getSelectionModel().select(0);

        ComboBox<Integer> anoComboBox = new ComboBox<>();
        int anoAtual = Year.now().getValue();
        anoComboBox.getItems().addAll(IntStream.range(anoAtual - 10, anoAtual + 1).boxed().toList());
        anoComboBox.getSelectionModel().select(Integer.valueOf(anoAtual));

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.add(new Label("Mês:"), 0, 0);
        grid.add(mesComboBox, 1, 0);
        grid.add(new Label("Ano:"), 0, 1);
        grid.add(anoComboBox, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == gerarButtonType) {
                return new Pair<>(mesComboBox.getSelectionModel().getSelectedIndex() + 1, anoComboBox.getValue());
            }
            return null;
        });

        return dialog.showAndWait();
    }
}