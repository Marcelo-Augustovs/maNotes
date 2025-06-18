package dev_marcelo.maNotes.infra.security.interface_grafica.calendario;

import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.infra.security.exceptions.ApiChangeValorException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiCreateException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
        try {
            List<Lembrete> lembretes = apiClient.buscarLembretes();

            eventos.clear();

            for (Lembrete lembrete : lembretes) {
                String dataChave = lembrete.getDiaMarcado().toString(); // Garante formato YYYY-MM-DD
                eventos.computeIfAbsent(dataChave, k -> new ArrayList<>()).add(new CalendarActivity(lembrete.getNomeDoEvento()));
            }
        } catch (Exception e) {
            throw new ApiNotFoundException("Erro ao buscar eventos do calendário: ");
        }

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

        LocalDate hoje = LocalDate.now();

        for (int i = 0; i < totalCells; i++) {
            VBox cell = new VBox();
            cell.setPrefSize(100, 80);
            cell.setPadding(new Insets(5));
            cell.setAlignment(Pos.TOP_CENTER);
            cell.setStyle("-fx-border-color: #ccc; -fx-background-color: #fff;");

            if (i >= firstDayOfWeek && dayCounter <= daysInMonth) {
                Text dayText = new Text(String.valueOf(dayCounter));

                // Verifica se é o dia atual e aplica destaque
                LocalDate dataCelula = LocalDate.of(mesAtual.getYear(), mesAtual.getMonthValue(), dayCounter);
                if (dataCelula.equals(hoje)) {
                    cell.setStyle("-fx-background-color: #FF5722; -fx-border-color: #ccc;");
                    dayText.setFill(javafx.scene.paint.Color.WHITE); // Define a cor do texto para branco
                    dayText.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
                }

                cell.getChildren().add(dayText);

                // Adiciona eventos ao dia
                String dataChave = String.format("%04d-%02d-%02d", mesAtual.getYear(), mesAtual.getMonthValue(), dayCounter);
                List<CalendarActivity> activities = eventos.getOrDefault(dataChave, new ArrayList<>());

                for (CalendarActivity activity : activities) {
                    Text eventText = new Text(activity.getClientName());
                    eventText.setStyle("-fx-fill: blue; -fx-font-size: 12;");
                    cell.getChildren().add(eventText);
                }

                int day = dayCounter;
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
                    throw new ApiCreateException("nao foi possivel criar o lembrete");
                }
                eventos.computeIfAbsent(dataChave, k -> new ArrayList<>())
                        .add(new CalendarActivity(ZonedDateTime.now(), evento, eventos.get(dataChave).size() + 1));
                drawCalendar();
            }
        });
    }

    private void abrirMenuDeOpcoes(int day) {
        String dataChave = String.format("%04d-%02d-%02d", mesAtual.getYear(), mesAtual.getMonthValue(), day);

        List<String> options = new ArrayList<>();
        options.add("Adicionar Evento");
        List<CalendarActivity> activities = eventos.getOrDefault(dataChave, new ArrayList<>());

        if (!activities.isEmpty()) {
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
        String dataChave = String.format("%04d-%02d-%02d", mesAtual.getYear(), mesAtual.getMonthValue(), dia);
        List<CalendarActivity> activities = eventos.getOrDefault(dataChave, new ArrayList<>());

        if (activities.isEmpty()) return;

        List<String> eventNames = activities.stream()
                .map(CalendarActivity::getClientName)
                .collect(Collectors.toList());

        ChoiceDialog<String> dialog = new ChoiceDialog<>(null, eventNames);
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
                for (CalendarActivity e : activities) {
                    if (e.getClientName().equals(selectedEvent)) {
                        e.setClientName(newName);
                        break;
                    }
                }

                try {
                    apiClient.editarLembrete(dialog.getResult(),editDialog.getResult(),dataChave);
                } catch (Exception e) {
                    throw new ApiChangeValorException("nao foi possivel editar o evento");
                }

                eventos.put(dataChave, new ArrayList<>(activities));

                // Atualiza o calendário
                drawCalendar();
            });
        });
    }
    private void removerEvento(int dia) {
        String dataChave = String.format("%04d-%02d-%02d", mesAtual.getYear(), mesAtual.getMonthValue(), dia);
        if (!eventos.containsKey(dataChave) || eventos.get(dataChave).isEmpty()) {
            return;
        }

        List<CalendarActivity> activities = eventos.get(dataChave);

        List<String> eventNames = activities.stream()
                .map(CalendarActivity::getClientName)
                .collect(Collectors.toList());

        if (eventNames.isEmpty()) return;

        ChoiceDialog<String> dialog = new ChoiceDialog<>(eventNames.get(0), eventNames);
        dialog.setTitle("Remover Evento");
        dialog.setHeaderText("Escolha um evento para remover:");
        dialog.setContentText("Evento:");

        Optional<String> eventToRemove = dialog.showAndWait();

        eventToRemove.ifPresent(selectedEvent -> {
            Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
            confirmDialog.setTitle("Confirmação de Remoção");
            confirmDialog.setHeaderText(null);
            confirmDialog.setContentText("Tem certeza que deseja remover o evento: " + selectedEvent + "?");

            Optional<ButtonType> confirmation = confirmDialog.showAndWait();

            if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
                try {
                    Long id = apiClient.buscarIdPorNomeEDia(selectedEvent, dataChave);
                    apiClient.removerLembrete(id);
                } catch (IllegalArgumentException e) {
                    throw new ApiNotFoundException("Evento não encontrado:");
                } catch (Exception e) {
                    System.err.println("Erro ao remover o lembrete da API: " + e.getMessage());
                }

                activities.removeIf(e -> e.getClientName().equals(selectedEvent));

                if (activities.isEmpty()) {
                    eventos.remove(dataChave);
                }

                // Atualiza o calendário
                drawCalendar();
            }
        });
    }
}