package dev_marcelo.maNotes.infra.security.interface_grafica.notificacao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.entity.Notificacao;
import dev_marcelo.maNotes.infra.security.exceptions.ApiChangeValorException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiCreateException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiDeleteException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.infra.security.interface_grafica.tela_login.LoginApiClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class NotificacaoApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1/notificacao";

    public Notificacao criarNotificacao(String titulo, String descricao, String dataHora) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
        {
          "titulo": "%s",
          "descricao": "%s",
          "dataHora": "%s"
        }
        """, titulo, descricao, dataHora);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule()); // para LocalDateTime
            return mapper.readValue(response.body(), Notificacao.class);
        } else {
            throw new ApiCreateException("Falha na criação da notificação: Código " + response.statusCode() + " - " + response.body());
        }
    }


    public List<Notificacao> buscarNotificacao() throws Exception {
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

                System.out.println("Resposta JSON: " + response.body());
                return Arrays.asList(objectMapper.readValue(response.body(), Notificacao[].class));
            } else {
                throw new ApiNotFoundException("Erro ao buscar Notificacao: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String editarNotificacao(Long id,String titulo,String descricao,String dataHora) throws URISyntaxException, IOException, InterruptedException {
        String PATCH_URL = BASE_URL + "/" + id;

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
                {
                  "titulo": "%s",
                  "descricao": "%s",
                  "dataHora": "%s"
                }
                """, titulo, descricao,dataHora);

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
            throw new ApiChangeValorException("Falha ao editar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    public void removerNotificacao(Long id) {
        String DELETE_URL = BASE_URL + "/" + id;

        try {
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(DELETE_URL))
                    .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                    .header("Accept", "application/json")
                    .DELETE()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 204) {
                throw new ApiDeleteException("Falha ao de " + response.statusCode() + " - " + response.body());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}