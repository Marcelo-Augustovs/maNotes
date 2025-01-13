package dev_marcelo.maNotes.infra.security.interface_grafica;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppManager {
    private static String jwtToken;
    private static Stage primaryStage;

    public static String getJwtToken() {
        return jwtToken;
    }

    public static void setJwtToken(String token) {
        jwtToken = token;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setPrimaryStage(Stage stage) {
        primaryStage = stage;
    }

    public static void showMainScreen() {
        try {
            FXMLLoader loader = new FXMLLoader(AppManager.class.getResource("/MaNotes.fxml")); // Tela principal
            Scene scene = new Scene(loader.load());
            primaryStage.setScene(scene);
            primaryStage.setTitle("MaNotes - Tela Principal");
        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar a tela principal: " + e.getMessage());
        }
    }
}