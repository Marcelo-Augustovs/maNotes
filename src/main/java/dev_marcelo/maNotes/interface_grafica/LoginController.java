package dev_marcelo.maNotes.interface_grafica;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label errorMessage;

    @FXML
    private Button loginButton;

    @FXML
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            errorMessage.setText("Por favor, preencha todos os campos.");
            return;
        }

        try {
            // Substitua por uma chamada ao seu cliente HTTP para autenticação
            String jwtToken = AuthService.authenticate(username, password);

            if (jwtToken != null) {
                // Salve o token e abra a tela principal
                App.setJwtToken(jwtToken);
                App.showMainScreen(); // Método para abrir a tela principal
            } else {
                errorMessage.setText("Credenciais inválidas.");
            }
        } catch (Exception e) {
            errorMessage.setText("Erro ao conectar-se ao servidor.");
        }
    }
}
}
