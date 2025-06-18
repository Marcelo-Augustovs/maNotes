package dev_marcelo.maNotes.infra.security.interface_grafica.calendario;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.infra.security.exceptions.ApiChangeValorException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiCreateException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.infra.security.interface_grafica.tela_login.LoginApiClient;

import java.net.URI;
import java.net.URISyntaxException;
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
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body();
        } else {
            throw new ApiCreateException("Falha ao marcar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    public List<Lembrete> buscarLembretes() throws Exception {
        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(BASE_URL))
                    .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                ObjectMapper objectMapper = new ObjectMapper();
                objectMapper.registerModule(new JavaTimeModule());

                return Arrays.asList(objectMapper.readValue(response.body(), Lembrete[].class));
            } else {
                throw new ApiNotFoundException("Erro ao buscar lembretes: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String editarLembrete(String nomeDoEvento, String novoNomeDoEvento, String diaMarcado) throws Exception {
        Long id = buscarIdPorNomeEDia(nomeDoEvento, diaMarcado);
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
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody)) // Usa PATCH corretamente
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new ApiChangeValorException("Falha ao editar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    protected Long buscarIdPorNomeEDia(String nomeDoEvento, String diaMarcado) throws Exception {
        List<Lembrete> lembretes = buscarLembretes();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dataFormatada = LocalDate.parse(diaMarcado, formatter);

        return lembretes.stream()
                .filter(lembrete -> lembrete.getNomeDoEvento().trim().equalsIgnoreCase(nomeDoEvento.trim()) &&
                        lembrete.getDiaMarcado().isEqual(dataFormatada))
                .map(Lembrete::getId)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado: " + nomeDoEvento + " no dia " + diaMarcado));
    }

    public void removerLembrete(Long id) throws Exception {
        String DELETE_URL = BASE_URL + "/" + id;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(DELETE_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200 || response.statusCode() == 204) {
            System.out.println("Lembrete removido com sucesso!");
        } else if (response.statusCode() == 404) {
            throw new ApiNotFoundException("Lembrete não encontrado para remoção: Código " + response.statusCode() + " - " + response.body());
        } else {
            throw new ApiChangeValorException("Erro ao remover o lembrete: Código " + response.statusCode() + " - " + response.body());
        }
    }

}



