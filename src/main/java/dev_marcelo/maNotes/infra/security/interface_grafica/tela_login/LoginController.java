package dev_marcelo.maNotes.infra.security.interface_grafica.tela_login;

import dev_marcelo.maNotes.infra.security.interface_grafica.util.AppManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


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
            String jwtToken = loginApiClient.logar(username, password);

            if (jwtToken != null && !jwtToken.isEmpty()) {
                AppManager.setJwtToken(jwtToken);
                AppManager.showMainScreen();
            } else {
                errorMessage.setText("Credenciais inválidas.");
            }
        } catch (Exception e) {
            errorMessage.setText(e.getMessage());
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
            String jwtToken = loginApiClient.registrar(username, password);
        } catch (Exception e) {
            errorMessage.setText("Usuario já cadastrado");
        }
    }
}