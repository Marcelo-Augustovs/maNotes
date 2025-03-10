package dev_marcelo.maNotes.infra.security.interface_grafica;

import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.util.Pair;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Controller
public class MaNotesController {
    private static final AnotacaoApiClient apiClient = new AnotacaoApiClient();
    private static final FinanciasApiClient financiasApiClient = new FinanciasApiClient();
    private static final DespesasApiClient despesasApiClient = new DespesasApiClient();


    @FXML
    private TableView<AnotacoesResponseDto> anotacaoTable;

    @FXML
    private TableView<Fundos> fundosTable;

    @FXML
    private TableView<Despesa> despesaTable;


    @FXML
    private TableColumn<Despesa,String> colunaDespesas;

    @FXML
    private TableColumn<Despesa,Float> colunaDespesasValor;

    @FXML
    private TableColumn<Despesa, LocalDateTime> colunaMes;

    @FXML
    private TableColumn<Despesa, String> colunaStatusDespesas;

    @FXML
    private TableColumn<Fundos,String> colunaFundos;

    @FXML
    private TableColumn<Fundos,Float> colunaFundosValores;

    @FXML
    private TableColumn<Fundos, LocalDateTime> colunaFundosDataModificacao;

    @FXML
    private TableColumn<AnotacoesResponseDto, Float> colunaId;

    @FXML
    private TableColumn<AnotacoesResponseDto, String> colunaAnotacao;

    @FXML
    private TableColumn<AnotacoesResponseDto, LocalDateTime> colunaDataModificacao;
    @FXML
    private BorderPane MaNotesInterfaceGrafica;

    @FXML
    private MenuBar menuTop;

    @FXML
    private TabPane menuCenter;

    @FXML
    private Text menuBottom;

    @FXML
    private MenuItem menuCalendario;

    @FXML
    private AnchorPane menuPrincipal;

    @FXML
    private AnchorPane menuFundos;

    @FXML
    private AnchorPane menuDespesas;

    @FXML
    private TabPane meioDoApp;

    @FXML
    private Pane painelAdicionarTexto;

    @FXML
    private Pane painelAdicionarFinancias;

    @FXML
    private TextArea campoAnotacao;

    @FXML
    private TextArea txtValor;

    @FXML
    private Button btnFundos;

    @FXML
    private Button btnAtualizarFundos;

    @FXML
    private Button btnAtualizarDespesas;

    @FXML
    private Button btnDespesas;

    @FXML
    private Button btnNotes;

    @FXML
    private void abrirConfirmarFundos() {
        // Abre a janela de configurações sem fechar a tela principal
        AppManager.abrirJanelaAuxiliar("/confirmarFundos.fxml", "Adicionar Fundos", 500, 400, false);
    }

    @FXML
    private void abrirConfirmarDespesa() {
        // Abre a janela de relatórios sem fechar a tela principal
        AppManager.abrirJanelaAuxiliar("/confirmarDespesa.fxml", "Relatórios", 600, 450, false);
    }
    @FXML
    private void abrirEditarFundos() {
        // Abre a janela de configurações sem fechar a tela principal
        AppManager.abrirJanelaAuxiliar("/confirmarFundos.fxml", "Adicionar Fundos", 500, 400, false);
    }
    @FXML
    private void abrirEditarDespesa() {
        // Abre a janela de configurações sem fechar a tela principal
        AppManager.abrirJanelaAuxiliar("/confirmarFundos.fxml", "Adicionar Fundos", 500, 400, false);
    }

    @FXML
    private void abrirCalendario(){
        AppManager.abrirJanelaAuxiliar("/Calendar.fxml","Calendario",600, 450, false);
    }
    @FXML
    public void initialize() throws Exception {
        carregarDadosAnotacoes();
        carregarDadosFundos();
        carregarDadosDespesas();

        configurarCliqueDuplo(fundosTable);
        configurarCliqueDuplo(despesaTable);
    }

    private void carregarDadosAnotacoes() throws Exception {
        List<AnotacoesResponseDto> listaDeAnotacoes = apiClient.buscarAnotacoes();
        ObservableList<AnotacoesResponseDto> dados = FXCollections.observableArrayList(listaDeAnotacoes);
        anotacaoTable.setItems(dados);

        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaAnotacao.setCellValueFactory(new PropertyValueFactory<>("anotacao"));
        colunaDataModificacao.setCellValueFactory(new PropertyValueFactory<>("dataModificacao"));
    }

    private void carregarDadosFundos() throws Exception {
        List<Fundos> listaDeFundos = financiasApiClient.buscarFundos();
        ObservableList<Fundos> dadosFundos = FXCollections.observableArrayList(listaDeFundos);
        fundosTable.setItems(dadosFundos);

        colunaFundos.setCellValueFactory(new PropertyValueFactory<>("origemDoFundo"));
        colunaFundosValores.setCellValueFactory(new PropertyValueFactory<>("valorRecebido"));
        colunaFundosDataModificacao.setCellValueFactory(new PropertyValueFactory<>("dataModificacao"));
    }

    private void carregarDadosDespesas() throws Exception {
        List<Despesa> listaDeDespesas = despesasApiClient.buscarDespesas();
        ObservableList<Despesa> dadosDespesas = FXCollections.observableArrayList(listaDeDespesas);
        despesaTable.setItems(dadosDespesas);

        colunaDespesas.setCellValueFactory(new PropertyValueFactory<>("nomeDaConta"));
        colunaDespesasValor.setCellValueFactory(new PropertyValueFactory<>("valorDaConta"));
        colunaMes.setCellValueFactory(new PropertyValueFactory<>("dataModificacao"));
        colunaStatusDespesas.setCellValueFactory(new PropertyValueFactory<>("statusDaConta"));
    }

    private <T> void configurarCliqueDuplo(TableView<T> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // Clique duplo
                    T objetoSelecionado = row.getItem();
                    System.out.println("Selecionado: " + objetoSelecionado);
                    editarObjeto(objetoSelecionado);
                }
            });
            return row;
        });
    }

    private <T> void editarObjeto(T objeto) {
        System.out.println("Processando: " + objeto.toString());

        if (objeto instanceof Fundos fundos) {
            Long idFundo = fundos.getId();
            String origemAtual = fundos.getOrigemDoFundo();
            String valorAtual = String.valueOf(fundos.getValorRecebido());

            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Editar Fundo");
            dialog.setHeaderText("Modifique os valores abaixo:");

            // Botões do Dialog
            ButtonType confirmarButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmarButton, ButtonType.CANCEL);

            // Campos de edição
            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField origemField = new TextField(origemAtual);
            TextField valorField = new TextField(valorAtual.toString());

            grid.add(new Label("Origem:"), 0, 0);
            grid.add(origemField, 1, 0);
            grid.add(new Label("Valor Recebido:"), 0, 1);
            grid.add(valorField, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmarButton) {
                    return new Pair<>(origemField.getText(), valorField.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> resultado = dialog.showAndWait();
            resultado.ifPresent(novosValores -> {
                String novaOrigem = novosValores.getKey();
                String novoValor = novosValores.getValue();

                try {
                    financiasApiClient.editarFundos(idFundo, novaOrigem, novoValor);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        if (objeto instanceof Despesa despesa) { // Editando Despesa
            Long idDespesa = despesa.getId();
            String nomeAtual = despesa.getNomeDaConta();
            String valorAtual = String.valueOf(despesa.getValorDaConta());

            Dialog<Pair<String, String>> dialog = new Dialog<>();
            dialog.setTitle("Editar Despesa");
            dialog.setHeaderText("Modifique os valores abaixo:");

            ButtonType confirmarButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmarButton, ButtonType.CANCEL);

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20, 150, 10, 10));

            TextField nomeField = new TextField(nomeAtual);
            TextField valorField = new TextField(valorAtual.toString());

            grid.add(new Label("Nome da Conta:"), 0, 0);
            grid.add(nomeField, 1, 0);
            grid.add(new Label("Valor da Despesa:"), 0, 1);
            grid.add(valorField, 1, 1);

            dialog.getDialogPane().setContent(grid);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmarButton) {
                    return new Pair<>(nomeField.getText(), valorField.getText());
                }
                return null;
            });

            Optional<Pair<String, String>> resultado = dialog.showAndWait();
            resultado.ifPresent(novosValores -> {
                String novoNome = novosValores.getKey();
                String novoValor = novosValores.getValue();

                try {
                    despesasApiClient.editarDespesa(idDespesa, novoNome, novoValor);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }


    public void mostrarCampoDeTexto(javafx.event.ActionEvent event) {
        try {
            painelAdicionarTexto.setVisible(true);
            painelAdicionarTexto.setManaged(true);

            // Certifique-se de que o botão para adicionar está desativado
            btnNotes.setDisable(true);

            // Opcional: limpar o campo para nova anotação
            campoAnotacao.clear();

        } catch (Exception e) {
            System.err.println("Erro ao exibir campo de texto: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarCampoDeFundos(javafx.event.ActionEvent event) {
        try {
            abrirConfirmarFundos();

        } catch (Exception e) {
            System.err.println("Erro ao exibir Confirmar Fundos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void mostrarCampoDeDespesa(javafx.event.ActionEvent event) {
        try {
            abrirConfirmarDespesa();

        } catch (Exception e) {
            System.err.println("Erro ao exibir Confirmar Despesa: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void confirmarAdicao(ActionEvent event) {
        try {
            String mensagem = campoAnotacao.getText().replace("\n","\\n");
            if (mensagem == null || mensagem.trim().isEmpty()) {
                mensagem = "Anotação vazia";
            }

            apiClient.criarAnotacao(mensagem);

            // Atualizando os dados
            List<AnotacoesResponseDto> listaDeAnotacoes = apiClient.buscarAnotacoes();
            ObservableList<AnotacoesResponseDto> dados = FXCollections.observableArrayList(listaDeAnotacoes);
            anotacaoTable.setItems(dados);


            campoAnotacao.clear();
            painelAdicionarTexto.setVisible(false);
            painelAdicionarTexto.setManaged(false);
            btnNotes.setDisable(false);
        } catch (Exception e) {
            System.err.println("Erro ao confirmar adição: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void cancelarAdicao(ActionEvent event) {
        painelAdicionarTexto.setVisible(false);
        painelAdicionarTexto.setManaged(false);
        campoAnotacao.clear();
        btnNotes.setDisable(false);
    }

    public void atualizarTabelaFundos() throws Exception {
        List<Fundos> listaDeFundos = financiasApiClient.buscarFundos();
        ObservableList<Fundos> dadosFundos = FXCollections.observableArrayList(listaDeFundos);
        fundosTable.setItems(dadosFundos);
    }

    public void atualizarTabelaDespesa() throws Exception {
        List<Despesa> listaDeDespesa = despesasApiClient.buscarDespesas();
        ObservableList<Despesa> dadosDespesa = FXCollections.observableArrayList(listaDeDespesa);
        despesaTable.setItems(dadosDespesa);
    }
}