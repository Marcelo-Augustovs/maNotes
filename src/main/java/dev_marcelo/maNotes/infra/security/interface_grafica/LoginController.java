package dev_marcelo.maNotes.infra.security.interface_grafica;

import dev_marcelo.maNotes.entity.Fundos;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.List;

public class LoginController {

    @FXML
    public Button registerButton;
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private Button loginButton;

    private final LoginApiClient loginApiClient = new LoginApiClient();

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Por favor, preencha todos os campos.");
            return;
        }
        try {
            System.out.println("Tentando conectar à API...");
            FinanciasApiClient financiasApiClient = new FinanciasApiClient();
            List<Fundos> fundos = financiasApiClient.buscarFundos(); // Aqui pode estar o erro
            System.out.println("Resposta recebida: " + fundos);
        } catch (Exception e) {
            e.printStackTrace();
            errorMessage.setText("Erro ao conectar à API: " + e.getMessage()); // Exibir erro no JavaFX
        }








        try {
            // Chamada ao cliente HTTP para autenticação
            String jwtToken = loginApiClient.logar(username, password);

            if (jwtToken != null && !jwtToken.isEmpty()) {
                // Salva o token e abre a tela principal
                AppManager.setJwtToken(jwtToken);
                AppManager.showMainScreen(); // Método para abrir a tela principal
            } else {
                errorMessage.setText("Credenciais inválidas.");
            }
        } catch (Exception e) {
            errorMessage.setText("Erro ao conectar-se ao servidor: " + e.getMessage());
        }
    }

    @FXML
    public void handleRegister() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            // Chamada ao cliente HTTP para autenticação
            String jwtToken = loginApiClient.registrar(username, password);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}