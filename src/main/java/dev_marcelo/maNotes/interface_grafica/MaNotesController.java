package dev_marcelo.maNotes.interface_grafica;

import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;


@Controller
public class MaNotesController {
    private static final AnotacaoApiClient apiClient = new AnotacaoApiClient();
    private static final FinanciasApiClient financiasApiClient = new FinanciasApiClient();


    @FXML
    private TableView<AnotacoesResponseDto> anotacaoTable;

    @FXML
    private TableView<?> financiasTable;

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
    private AnchorPane menuPrincipal;

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
    private Button btnDespesas;

    @FXML
    private Button btnNotes;

    @FXML
    public void initialize() {
        List<AnotacoesResponseDto> listaDeAnotacoes = apiClient.buscarAnotacoes();
        // Converte a lista em ObservableList
        ObservableList<AnotacoesResponseDto> dados = FXCollections.observableArrayList(listaDeAnotacoes);

        // Define os itens da TableView
        anotacaoTable.setItems(dados);

        // Configura as colunas
        colunaId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colunaAnotacao.setCellValueFactory(new PropertyValueFactory<>("anotacao"));
        colunaDataModificacao.setCellValueFactory(new PropertyValueFactory<>("dataModificacao"));

        AnchorPane.setTopAnchor(meioDoApp, 0.0);
        AnchorPane.setBottomAnchor(meioDoApp, 0.0);
        AnchorPane.setLeftAnchor(meioDoApp, 0.0);
        AnchorPane.setRightAnchor(meioDoApp, 0.0);

        colunaAnotacao.setCellFactory(tc -> new TableCell<AnotacoesResponseDto, String>() {
            private final Text text = new Text();

            {
                text.wrappingWidthProperty().bind(colunaAnotacao.widthProperty());
                setGraphic(text);
                setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
            }

            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    text.setText(null);
                } else {
                    text.setText(item); // Define o texto atual
                }
            }
        });
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

    public void mostrarCampoDeFinancias(javafx.event.ActionEvent event) {
        try {
            painelAdicionarFinancias.setVisible(true);
            painelAdicionarFinancias.setManaged(true);

            // Certifique-se de que o botão para adicionar está desativado
            btnFundos.setDisable(true);

            // Opcional: limpar o campo para nova anotação
            txtValor.clear();

        } catch (Exception e) {
            System.err.println("Erro ao exibir campo de texto: " + e.getMessage());
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

    public void adicionarFundos(ActionEvent event) {
        try {
            String origemDoFundo = txtValor.getText();
            String valor = txtValor.getText();
            if (origemDoFundo == null || origemDoFundo.trim().isEmpty()) {
                origemDoFundo = "Origem Desconhecida";
            }

            financiasApiClient.criarFundos(origemDoFundo,valor);

            // Atualizando os dados
        /*    List<?> listaDeAnotacoes = apiClient.buscarAnotacoes();
            ObservableList<?> dados = FXCollections.observableArrayList(listaDeAnotacoes);
            financiasTable.setItems(dados);*/


            txtValor.clear();
            painelAdicionarFinancias.setVisible(false);
            painelAdicionarFinancias.setManaged(false);
            btnFundos.setDisable(false);
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

    public void cancelarFuncao(ActionEvent event) {
        painelAdicionarFinancias.setVisible(false);
        painelAdicionarFinancias.setManaged(false);
        txtValor.clear();
        btnFundos.setDisable(false);
    }
}