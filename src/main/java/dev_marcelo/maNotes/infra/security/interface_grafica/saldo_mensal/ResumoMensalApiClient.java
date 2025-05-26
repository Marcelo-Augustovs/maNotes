package dev_marcelo.maNotes.infra.security.interface_grafica.saldo_mensal;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import dev_marcelo.maNotes.infra.security.exceptions.ApiChangeValorException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiCreateException;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.infra.security.interface_grafica.tela_login.LoginApiClient;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.List;

public class ResumoMensalApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1/saldo";

    public List<FundosEDespesaMensal> buscarFundosEDespesaMensal() throws Exception {
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

                return Arrays.asList(objectMapper.readValue(response.body(), FundosEDespesaMensal[].class));
            } else {
                throw new ApiNotFoundException("Erro ao buscar o Resumo Mensal: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Erro ao conectar à API: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public String criarFundosEDespesaMensal(String mes, String ano) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\n" +
                "  \"mes\": \"%s\",\n" +
                "  \"ano\": \"%s\"\n" +
                "}", mes, ano);

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
            throw new ApiCreateException("Falha ao adicionar fundos: Código " + response.statusCode() + " - " + response.body());
        }
    }

    public String updateFundosEDespesaMensal(String mes, String ano) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\n" +
                "  \"mes\": \"%s\",\n" +
                "  \"ano\": \"%s\"\n" +
                "}", mes, ano);

        System.out.println(jsonBody);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL))
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
}