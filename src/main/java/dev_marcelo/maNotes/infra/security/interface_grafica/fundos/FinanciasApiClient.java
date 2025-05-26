package dev_marcelo.maNotes.infra.security.interface_grafica.fundos;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.ApiChangeValorException;
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

public class FinanciasApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1/fundos";

    public String criarFundos(String origemDoFundo,String valorRecebido) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\n" +
                "  \"origemDoFundo\": \"%s\",\n" +
                "  \"valorRecebido\": \"%s\"\n" +
                "}", origemDoFundo, valorRecebido);

        System.out.println(jsonBody);
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
            throw new RuntimeException("Falha ao adicionar fundos: Código " + response.statusCode() + " - " + response.body());
        }
    }


    public List<Fundos> buscarFundos() throws Exception {
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
                objectMapper.registerModule(new JavaTimeModule()); // Adiciona suporte a LocalDate

                return Arrays.asList(objectMapper.readValue(response.body(), Fundos[].class));
            } else {
                throw new ApiNotFoundException("Erro ao buscar fundos: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String editarFundos(Long id,String nomeFundo, String valorRecebido) throws URISyntaxException, IOException, InterruptedException {
        String PATCH_URL = BASE_URL + "/" + id;

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("""
                {
                  "origemDoFundo": "%s",
                  "valorRecebido": "%s"
                }
                """, nomeFundo, valorRecebido);

        System.out.println(jsonBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(PATCH_URL))
                .header("Authorization", "Bearer " + LoginApiClient.getJwtToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body();
        } else {
            throw new ApiChangeValorException("Falha ao editar o evento: Código " + response.statusCode() + " - " + response.body());
        }
    }

    public void removerFundos(Long id) {
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
                throw new RuntimeException("Falha ao de " + response.statusCode() + " - " + response.body());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
