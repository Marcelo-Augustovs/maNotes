package dev_marcelo.maNotes.infra.security.interface_grafica.notificacao;

import dev_marcelo.maNotes.entity.Notificacao;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NotificationController {

    @FXML private TextField tituloField;
    @FXML private TextField descricaoField;
    @FXML private DatePicker dataPicker;
    @FXML private TextField horaField;
    @FXML private Button salvarButton;

    private final NotificacaoApiClient apiClient;
    private final NotificationScheduler scheduler;

    public NotificationController(){
        apiClient = new NotificacaoApiClient();
        scheduler = new NotificationScheduler();
    }


    @FXML
    private void initialize() {
        salvarButton.setOnAction(event -> criarNotificacao());
        scheduler.iniciar();
    }

    private void criarNotificacao() {
        String titulo = tituloField.getText();
        String descricao = descricaoField.getText();
        LocalDate data = dataPicker.getValue();
        String horaTexto = horaField.getText();

        if (titulo.isEmpty() || data == null || horaTexto.isEmpty()) {
            mostrarAlerta("Preencha todos os campos!");
            return;
        }

        try {
            LocalTime hora = LocalTime.parse(horaTexto);
            LocalDateTime dataHora = LocalDateTime.of(data, hora);
            // Formata a dataHora como string ISO (ex: 2025-04-08T15:30)
            String dataHoraFormatada = dataHora.toString();

            Notificacao novaNotificacao = apiClient.criarNotificacao(titulo, descricao, dataHoraFormatada);
            mostrarAlerta("Notificação agendada com sucesso!");
            scheduler.agendarNovaNotificacao(novaNotificacao);

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Erro ao agendar notificação: " + e.getMessage());
        }
    }

    private void mostrarAlerta(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Notificação");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
