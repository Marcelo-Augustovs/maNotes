package dev_marcelo.maNotes.infra.security.interface_grafica;

import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.Label;

import java.util.List;
import java.util.Optional;

public class ResumoMensalController {

    @FXML
    private Label labelMesAno;

    @FXML
    private Label labelFundos;

    @FXML
    private Label labelDespesa;

    @FXML
    private Label labelTotal;

    @FXML
    private BarChart<Number, String> barChart;

    @FXML
    private NumberAxis xAxis;

    @FXML
    private CategoryAxis yAxis;

    private static final ResumoMensalApiClient apiClient = new ResumoMensalApiClient();

    public void initialize() throws Exception {
        List<String> opcoes = obterListaDoEndpoint();

        ChoiceDialog<String> dialog = new ChoiceDialog<>(opcoes.get(0), opcoes);
        dialog.setTitle("Seleção de Período");
        dialog.setHeaderText("Escolha um mês para visualizar:");
        dialog.setContentText("Mês:");

        Optional<String> resultado = dialog.showAndWait();

        resultado.ifPresentOrElse(this::carregarDados, () -> System.out.println("Nenhuma opção selecionada."));
    }

    private void carregarDados(String mesSelecionado) {
        System.out.println("Carregando dados para: " + mesSelecionado);

        try {
            List<FundosEDespesaMensal> dados = apiClient.buscarFundosEDespesaMensal();

            // Filtrar os dados pelo mês selecionado
            FundosEDespesaMensal dadosMes = dados.stream()
                    .filter(f -> f.getMesReferente().equals(mesSelecionado))
                    .findFirst()
                    .orElse(null);

            if (dadosMes == null) {
                System.out.println("Nenhum dado encontrado para o mês selecionado.");
                return;
            }

            double fundos = dadosMes.getFundos();
            double despesa = dadosMes.getDespesa();
            double total = dadosMes.getTotal();

            Platform.runLater(() -> {
                labelMesAno.setText("Mês Referente: " + mesSelecionado);
                labelFundos.setText("Fundos: R$ " + fundos);
                labelDespesa.setText("Despesa: R$ " + despesa);
                labelTotal.setText("Total: R$ " + total);

                XYChart.Series<Number, String> series = new XYChart.Series<>();
                series.setName("Resumo Financeiro");

                series.getData().add(new XYChart.Data<>(fundos, "Fundos"));
                series.getData().add(new XYChart.Data<>(despesa, "Despesa"));
                series.getData().add(new XYChart.Data<>(total, "Total"));

                barChart.getData().clear();
                barChart.getData().add(series);

                series.getData().forEach(data -> {
                    if (data.getYValue().equals("Fundos")) {
                        data.getNode().setStyle("-fx-bar-fill: #4CAF50;");
                    } else if (data.getYValue().equals("Despesa")) {
                        data.getNode().setStyle("-fx-bar-fill: #FFC107;");
                    } else if (data.getYValue().equals("Total")) {
                        data.getNode().setStyle("-fx-bar-fill: #000000;");
                    }
                });

                yAxis.setCategories(FXCollections.observableArrayList("Total", "Despesa", "Fundos"));
            });

        } catch (Exception e) {
            System.err.println("Erro ao carregar dados: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private List<String> obterListaDoEndpoint() throws Exception {
        List<FundosEDespesaMensal> dados = apiClient.buscarFundosEDespesaMensal();

        return dados.stream()
                .map(FundosEDespesaMensal::getMesReferente)
                .toList();
    }
}