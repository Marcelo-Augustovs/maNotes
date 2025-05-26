package dev_marcelo.maNotes.infra.security.interface_grafica;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import dev_marcelo.maNotes.infra.security.interface_grafica.tela_login.LoginApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AnotacaoApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/v1/anotacoes";

    public String criarAnotacao(String anotacao) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"anotacao\": \"%s\"}", anotacao);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
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

    public List<AnotacoesResponseDto> buscarAnotacoes() throws URISyntaxException, IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Accept", "application/json")
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

            AnotacoesResponseDto[] responseArray = mapper.readValue(response.body(), AnotacoesResponseDto[].class);
            if (responseArray != null) {
                return Arrays.asList(responseArray);
            } else {
                return Collections.emptyList();
            }
        } else {
            throw new RuntimeException("Falha ao buscar anotações: " + response.body());
        }
    }

    public String editarAnotacao(Long idAnotacao, String novoTexto) throws URISyntaxException, IOException, InterruptedException {
        String PATCH_URL = BASE_URL + "/" + idAnotacao;
        System.out.println("Url:" + PATCH_URL);

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
                {
                  "anotacao": "%s"
                }
                """, novoTexto);

        System.out.println(jsonBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(PATCH_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
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

    public void removerAnotacao(Long id) {
        String DELETE_URL = BASE_URL + "/" + id;
        System.out.println("Url:" + DELETE_URL);
        System.out.println("removendo o objeto fundos");

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(DELETE_URL))
                    .header("Accept", "application/json")
                    .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 204) {
                System.out.println("funcionou deletado fundos");
            } else {
                throw new RuntimeException("Falha ao de " + response.statusCode() + " - " + response.body());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

