package dev_marcelo.maNotes.infra.security.interface_grafica;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import org.springframework.stereotype.Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZonedDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class CalendarController implements Initializable {

    private ZonedDateTime mesAtual;
    private ZonedDateTime today;
    private final Map<String, List<CalendarActivity>> eventos = new HashMap<>();

    @FXML
    private Text ano;

    @FXML
    private Text mes;

    @FXML
    private FlowPane calendar;

    private CalendarApiClient apiClient;

    public CalendarController(){
        this.apiClient = new CalendarApiClient();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mesAtual = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }

    @FXML
    void voltarMes(ActionEvent event) {
        mesAtual = mesAtual.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void avancarMes(ActionEvent event) {
        mesAtual = mesAtual.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    private void drawCalendar() {
        calendar.getChildren().clear();
        ano.setText(String.valueOf(mesAtual.getYear()));
        mes.setText(String.valueOf(mesAtual.getMonth()));

        YearMonth currentMonth = YearMonth.of(mesAtual.getYear(), mesAtual.getMonthValue());
        LocalDate firstOfMonth = currentMonth.atDay(1);
        int daysInMonth = currentMonth.lengthOfMonth();
        int firstDayOfWeek = firstOfMonth.getDayOfWeek().getValue();

        firstDayOfWeek = (firstDayOfWeek == 7) ? 6 : firstDayOfWeek - 1;

        int totalCells = 7 * 6;
        int dayCounter = 1;

        GridPane grid = new GridPane();
        grid.setHgap(5);
        grid.setVgap(5);

        for (int col = 0; col < 7; col++) {
            grid.getColumnConstraints().add(new ColumnConstraints(100));
        }

        for (int i = 0; i < totalCells; i++) {
            VBox cell = new VBox();
            cell.setPrefSize(100, 80);
            cell.setPadding(new Insets(5));
            cell.setAlignment(Pos.TOP_CENTER);
            cell.setStyle("-fx-border-color: #ccc; -fx-background-color: #fff;");

            if (i >= firstDayOfWeek && dayCounter <= daysInMonth) {
                Text dayText = new Text(String.valueOf(dayCounter));
                cell.getChildren().add(dayText);

                int day = dayCounter;
                String dataChave = mesAtual.getYear() + "-" + mesAtual.getMonthValue() + "-" + day;
                List<CalendarActivity> activities = eventos.getOrDefault(dataChave, new ArrayList<>());

                for (CalendarActivity activity : activities) {
                    Text eventText = new Text(activity.getClientName());
                    eventText.setStyle("-fx-fill: blue; -fx-font-size: 12;");
                    cell.getChildren().add(eventText);
                }

                cell.setOnMouseClicked(event -> abrirMenuDeOpcoes(day));

                dayCounter++;
            }

            int row = i / 7;
            int col = i % 7;
            grid.add(cell, col, row);
        }

        calendar.getChildren().add(grid);
    }

    private void adicionarEvento(int dia) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Adicionar Evento");
        dialog.setHeaderText("Digite o nome do evento para o dia " + dia);
        dialog.setContentText("Evento:");



        dialog.showAndWait().ifPresent(evento -> {
            if (!evento.trim().isEmpty()) {
                String dataChave = mesAtual.getYear() + "-" + mesAtual.getMonthValue() + "-" + dia;
                try {
                    apiClient.criarLembrete(dialog.getResult(),dataChave);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                eventos.computeIfAbsent(dataChave, k -> new ArrayList<>())
                        .add(new CalendarActivity(ZonedDateTime.now(), evento, eventos.get(dataChave).size() + 1));
                drawCalendar();
            }
        });
    }

    private void abrirMenuDeOpcoes(int day) {
        String dataChave = mesAtual.getYear() + "-" + mesAtual.getMonthValue() + "-" + day;
        List<String> options = new ArrayList<>();
        options.add("Adicionar Evento");

        if (eventos.containsKey(dataChave) && !eventos.get(dataChave).isEmpty()) {
            options.add("Editar Evento");
            options.add("Remover Evento");
        }

        ChoiceDialog<String> dialog = new ChoiceDialog<>(options.get(0), options);
        dialog.setTitle("Gerenciar Evento");
        dialog.setHeaderText("Escolha uma ação para o dia " + day);
        dialog.setContentText("Ação:");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(choice -> {
            if (choice.equals("Adicionar Evento")) {
                adicionarEvento(day);
            } else if (choice.equals("Editar Evento")) {
                editarEvento(day);
            } else if (choice.equals("Remover Evento")) {
                removerEvento(day);
            }
        });
    }

    private void editarEvento(int dia) {
        String dataChave = mesAtual.getYear() + "-" + mesAtual.getMonthValue() + "-" + dia;
        List<CalendarActivity> activities = eventos.getOrDefault(dataChave, new ArrayList<>());
        if (activities.isEmpty()) return;

        List<String> eventNames = activities.stream()
                .map(CalendarActivity::getClientName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(eventNames.get(0), eventNames);
        dialog.setTitle("Editar Evento");
        dialog.setHeaderText("Escolha um evento para editar:");
        dialog.setContentText("Evento:");

        Optional<String> eventToEdit = dialog.showAndWait();
        eventToEdit.ifPresent(selectedEvent -> {
            TextInputDialog editDialog = new TextInputDialog(selectedEvent);
            editDialog.setTitle("Editar Evento");
            editDialog.setHeaderText("Digite o novo nome para o evento:");
            editDialog.setContentText("Novo nome:");

            editDialog.showAndWait().ifPresent(newName -> {
                activities.stream()
                        .filter(e -> e.getClientName().equals(selectedEvent))
                        .findFirst()
                        .ifPresent(e -> e.setClientName(newName));
                drawCalendar();
            });
        });
    }
    private void removerEvento(int dia) {
        String dataChave = mesAtual.getYear() + "-" + mesAtual.getMonthValue() + "-" + dia;

        // Verifica se há eventos no dia
        if (!eventos.containsKey(dataChave) || eventos.get(dataChave).isEmpty()) {
            return;
        }

        List<CalendarActivity> activities = eventos.get(dataChave);

        // Cria uma lista com os nomes dos eventos disponíveis
        List<String> eventNames = activities.stream()
                .map(CalendarActivity::getClientName)
                .collect(Collectors.toList());

        if (eventNames.isEmpty()) return; // Caso não haja eventos, não faz nada

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, eventNames);
        dialog.setTitle("Remover Evento");
        dialog.setHeaderText("Escolha um evento para remover:");
        dialog.setContentText("Evento:");

        Optional<String> eventToRemove = dialog.showAndWait();

        eventToRemove.ifPresent(selectedEvent -> {
            // Remove o evento da lista
            activities.removeIf(e -> e.getClientName().equals(selectedEvent));

            // Remove o dia do mapa se não houver mais eventos
            if (activities.isEmpty()) {
                eventos.remove(dataChave);
            }

            // Atualiza o calendário para refletir a remoção
            drawCalendar();
        });
    }
}