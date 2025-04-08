package dev_marcelo.maNotes.infra.security.interface_grafica.notificacao;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;
import javafx.geometry.Pos;


public class PopupNotification {

    private static Timer timer;

    public static void mostrar(String titulo, String descricao) {
        Platform.runLater(() -> {
            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Notificação");

            Label titleLabel = new Label(titulo);
            titleLabel.getStyleClass().add("popup-title");
            titleLabel.setWrapText(true);

            Label descLabel = new Label(descricao);
            descLabel.getStyleClass().add("popup-description");
            descLabel.setWrapText(true);

            Button closeButton = new Button("Fechar");
            closeButton.getStyleClass().add("popup-button");
            closeButton.setOnAction(e -> {
                if (timer != null) {
                    timer.cancel();
                }
                stage.close();
            });

            VBox layout = new VBox(15, titleLabel, descLabel, closeButton);
            layout.getStyleClass().add("popup-container");
            layout.setAlignment(Pos.CENTER); // Centraliza os elementos
            layout.setSpacing(15);

            Scene scene = new Scene(layout, 350, 180);
            scene.getStylesheets().add("css/notificacao.css"); // Certifique-se de que o CSS está acessível
            stage.setScene(scene);

            tocarSomLoop();

            stage.show();
        });
    }

    private static void tocarSomLoop() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                Toolkit.getDefaultToolkit().beep();
            }
        }, 0, 2000); // O som tocará a cada 2 segundos
    }
}




