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
    private static final ResumoMensalApiClient resumoMensalApiClient = new ResumoMensalApiClient();
    private Object objetoSelecionado;

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
    private Button btnRemoverAnotacao;
    @FXML
    private Button btnQuitarDespesa;
    @FXML
    private Button btnFundos;
    @FXML
    private Button btnAtualizarFundos;
    @FXML
    private Button btnRemoverFundos;

    @FXML
    private Button btnAtualizarDespesas;


    @FXML
    private Button btnNotes;

    @FXML
    private void abrirConfirmarFundos() {
        AppManager.abrirJanelaAuxiliar("/confirmarFundos.fxml", "Adicionar Fundos", 500, 400, false);
    }
    @FXML
    private void abrirConfirmarDespesa() {
        AppManager.abrirJanelaAuxiliar("/confirmarDespesa.fxml", "Adicionar Despesa", 600, 450, false);
    }
    @FXML
    private void abrirFundosEDespesasPainel() {
        AppManager.abrirJanelaAuxiliar("/fundosEDespesasPainel.fxml", "Resumo Financeiro do Mês", 600, 450, false);
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

        configurarClique(fundosTable);
        configurarClique(despesaTable);
        configurarClique(anotacaoTable);

        aplicarEstilo();
    }

    private void aplicarEstilo() {
        MaNotesInterfaceGrafica.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
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

    private <T> void configurarClique(TableView<T> tableView) {
        tableView.setRowFactory(tv -> {
            TableRow<T> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) { // Clique duplo
                    T objetoSelecionado = row.getItem();
                    editarObjeto(objetoSelecionado);
                }
                if (!row.isEmpty() && event.getClickCount() == 1){
                    this.objetoSelecionado = row.getItem();
                    System.out.println("selecionado");
                }
            });
            return row;
        });
    }

    private <T> void editarObjeto(T objeto) {

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

        if (objeto instanceof AnotacoesResponseDto anotacao) {
            Long idAnotacao = anotacao.getId();
            String textoAtual = anotacao.getAnotacao();

            Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("Editar Anotação");
            dialog.setHeaderText("Modifique a anotação abaixo:");

            // Botão para salvar a edição
            ButtonType confirmarButton = new ButtonType("Salvar", ButtonBar.ButtonData.OK_DONE);
            dialog.getDialogPane().getButtonTypes().addAll(confirmarButton, ButtonType.CANCEL);

            // Campo de edição
            TextArea campoEdicao = new TextArea(textoAtual);
            campoEdicao.setWrapText(true);
            campoEdicao.setPrefRowCount(5);
            campoEdicao.setPrefColumnCount(30);

            dialog.getDialogPane().setContent(campoEdicao);

            dialog.setResultConverter(dialogButton -> {
                if (dialogButton == confirmarButton) {
                    return campoEdicao.getText();
                }
                return null;
            });

            Optional<String> resultado = dialog.showAndWait();
            resultado.ifPresent(novoTexto -> {
                try {
                    String mensagem = novoTexto.replace("\n","\\n");
                    if (mensagem == null || mensagem.trim().isEmpty()) {
                        mensagem = "Anotação vazia";
                    }

                    apiClient.editarAnotacao(idAnotacao, mensagem);
                    carregarDadosAnotacoes(); // Atualiza a tabela após edição
                } catch (URISyntaxException | IOException | InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });
        }
    }

    public void removerDespesa(ActionEvent event) throws Exception {
        if(objetoSelecionado.getClass().equals(Despesa.class)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Deseja realmente excluir esta despesa?");
            alert.setContentText("Esta ação não poderá ser desfeita.");

            // Obtendo a resposta do usuário
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                removerObjeto(objetoSelecionado);
            }
        }else {
            System.out.println("nenhuma Despesa selecionada");

        }
    }
    public void removerFundos(ActionEvent event) throws Exception {
        if(objetoSelecionado.getClass().equals(Fundos.class)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Deseja realmente excluir esta despesa?");
            alert.setContentText("Esta ação não poderá ser desfeita.");

            // Obtendo a resposta do usuário
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                removerObjeto(objetoSelecionado);
            }
        }else {
            System.out.println("nenhum Fundo selecionada");

        }
    }
    public void removerAnotacao(ActionEvent event) throws Exception {
        if(objetoSelecionado.getClass().equals(AnotacoesResponseDto.class)){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação de Exclusão");
            alert.setHeaderText("Deseja realmente excluir esta despesa?");
            alert.setContentText("Esta ação não poderá ser desfeita.");

            // Obtendo a resposta do usuário
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                removerObjeto(objetoSelecionado);
                carregarDadosAnotacoes();
            }
        }else {
            System.out.println("nenhum anotacao selecionada");

        }
    }

    private <T> void removerObjeto(T objeto) throws Exception {
        if (objeto instanceof Fundos fundos) {
            Long id = fundos.getId();
           financiasApiClient.removerFundos(id);
        }
        if (objeto instanceof Despesa despesa) {
            Long id = despesa.getId();
           despesasApiClient.removerDespesas(id);
        }
        if (objeto instanceof AnotacoesResponseDto anotacoesResponseDto) {
            Long id = anotacoesResponseDto.getId();
            apiClient.removerAnotacao(id);
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

    public void criarRelatorioMensal(){
        Optional<Pair<Integer, Integer>> resultado = RelatorioDialog.show();
        resultado.ifPresent(par -> {
            int mes = par.getKey();
            int ano = par.getValue();
            try {
                resumoMensalApiClient.criarFundosEDespesaMensal(Integer.toString(mes), Integer.toString(ano));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
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

    public void AtualizarRelatorioMensal(ActionEvent event) {
        Optional<Pair<Integer, Integer>> resultado = AtualizarRelatorioDialog.show();
        resultado.ifPresent(par -> {
            int mes = par.getKey();
            int ano = par.getValue();
            try {
                resumoMensalApiClient.updateFundosEDespesaMensal(Integer.toString(mes),Integer.toString(ano));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}