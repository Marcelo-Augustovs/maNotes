package dev_marcelo.maNotes.interface_grafica;

import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnotacaoApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/v1/anotacoes";

    public String criarAnotacao(String anotacao) throws Exception {
        String token = AppManager.getJwtToken();
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"anotacao\": \"%s\"}", anotacao);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new RuntimeException("Falha ao criar nota: " + response.body());
        }
    }

    public List<AnotacoesResponseDto> buscarAnotacoes() {
        RestTemplate restTemplate = new RestTemplate();

        AnotacoesResponseDto[] responseArray = restTemplate.getForObject(BASE_URL, AnotacoesResponseDto[].class);

        if (responseArray != null) {
            return Arrays.asList(responseArray);  // Converte o array para lista
        } else {
            return Collections.emptyList();  // Retorna uma lista vazia se não houver dados
        }
    }
}

