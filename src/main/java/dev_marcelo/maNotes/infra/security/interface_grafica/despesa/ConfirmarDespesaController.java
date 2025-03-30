package dev_marcelo.maNotes.infra.security.interface_grafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Controller;

@Controller
public class ConfirmarDespesaController {

    @FXML
    private Button btnConfirmar;

    @FXML
    private TextField txtNomeDaConta;

    @FXML
    private TextField txtValorDaConta;

    private DespesasApiClient apiClient;

    public ConfirmarDespesaController() {
        this.apiClient = new DespesasApiClient();  // instância manual
    }


    @FXML
    private void adicionarDespesa(ActionEvent event) {
        String nomeDaConta = txtNomeDaConta.getText();
        String valorDaConta = txtValorDaConta.getText();

        // Chama a API para criar o fundo
        try {
            String resposta = apiClient.criarDespesa(nomeDaConta, valorDaConta);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Sucesso");
            alert.setHeaderText(null);
            alert.setContentText("Despesa anotada com sucesso!");
            alert.showAndWait();
        } catch (Exception e) {
            System.err.println("Erro ao adicionar despesa: " + e.getMessage());
        }
    }


}
