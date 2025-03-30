package dev_marcelo.maNotes.infra.security.interface_grafica.fundos;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

@Controller
public class ConfirmarFundosController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtNomeFundo;

    @FXML
    private TextField txtValorRecebido;

    private FinanciasApiClient apiClient;


    public ConfirmarFundosController() {
        this.apiClient = new FinanciasApiClient();  // instância manual
    }

    @FXML
    private void adicionarFundos(ActionEvent event) {
        String nomeFundo = txtNomeFundo.getText();
        String valorRecebido = txtValorRecebido.getText();

        // Chama a API para criar o fundo
        try {
            String resposta = apiClient.criarFundos(nomeFundo, valorRecebido);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Fundo adicionado com sucesso!");
            alert.showAndWait();
    } catch (Exception e) {
            System.err.println("Erro ao adicionar fundo: " + e.getMessage());
        }
    }

}

