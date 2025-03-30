package dev_marcelo.maNotes.infra.security.interface_grafica.calendario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.entity.Lembrete;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class CalendarApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1/calendario";


    public String criarLembrete(String nomeDoEvento, String diaMarcado) throws Exception {
        String[] partes = diaMarcado.split("-");
        String dataCorrigida = String.format("%04d-%02d-%02d",
                Integer.parseInt(partes[0]),
                Integer.parseInt(partes[1]),
                Integer.parseInt(partes[2])
        );

        LocalDate data = LocalDate.parse(dataCorrigida, DateTimeFormatter.ISO_LOCAL_DATE);
        String dataFormatada = data.format(DateTimeFormatter.ISO_LOCAL_DATE);

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
                {
                  "nomeDoEvento": "%s",
                  "diaMarcado": "%s"
                }
                """, nomeDoEvento, dataFormatada);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body();
        } else {
            throw new RuntimeException("Falha ao marcar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    public List<Lembrete> buscarLembretes() throws Exception {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte a LocalDate

                System.out.println("Resposta JSON: " + response.body());
                return Arrays.asList(objectMapper.readValue(response.body(), Lembrete[].class));
            } else {
                throw new RuntimeException("Erro ao buscar lembretes: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String editarLembrete(String nomeDoEvento, String novoNomeDoEvento, String diaMarcado) throws Exception {
        Long id = buscarIdPorNomeEDia(nomeDoEvento, diaMarcado); // Busca o evento pelo nome e pelo dia
        System.out.println("id recuperado:" + id);

        if (id == null) {
            throw new IllegalArgumentException("Evento não encontrado: " + nomeDoEvento);
        }

        String PATCH_URL = BASE_URL + "/" + id;
        System.out.println("Url:" + PATCH_URL);

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
                {
                  "nomeDoEvento": "%s",
                  "diaMarcado": "%s"
                }
                """, novoNomeDoEvento, diaMarcado);

        System.out.println(jsonBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(PATCH_URL))
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody)) // Usa PATCH corretamente
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            System.out.println("teste:" + jsonBody);
            return response.body();
        } else {
            throw new RuntimeException("Falha ao editar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    private Long buscarIdPorNomeEDia(String nomeDoEvento, String diaMarcado) throws Exception {
        List<Lembrete> lembretes = buscarLembretes(); // Usa a API para obter todos os lembretes

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // ou o formato correto da data
        LocalDate dataFormatada = LocalDate.parse(diaMarcado, formatter);

        return lembretes.stream()
                .filter(lembrete -> lembrete.getNomeDoEvento().trim().equalsIgnoreCase(nomeDoEvento.trim()) &&
                        lembrete.getDiaMarcado().isEqual(dataFormatada)) // Agora ambos são LocalDate
                .map(Lembrete::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + nomeDoEvento + " no dia " + diaMarcado));
    }
}



