package dev_marcelo.maNotes.infra.security.interface_grafica;

import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.FundosEDespesasDto;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FinanciasApiClient {

    private static final String BASE_URL = "http://localhost:8080/api/v1";

    // falta colocar a quebra de linha em json
    public String criarFundos(String origemDoFundo,String valorRecebido) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"origemDoFundo\": \"%s\"," +
                "\"valorRecebido\": \"%s\"}", origemDoFundo,valorRecebido);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/fundos"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new RuntimeException("Falha ao adicionar fundos: " + response.body());
        }
    }

    public String criarDespesa(String nomeDaConta,String valorDaConta) throws Exception {
        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"nomeDaConta\": \"%s\"," +
                "\"valorDaConta\": \"%s\"}", nomeDaConta,valorDaConta);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(BASE_URL + "/despesa"))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new RuntimeException("Falha ao adicionar despesa: " + response.body());
        }
    }

    public List<FundosEDespesasDto> buscarFundosEDespesas() {
        RestTemplate restTemplate = new RestTemplate();

        FundosEDespesasDto[] responseArray = restTemplate.getForObject(BASE_URL + "/saldo", FundosEDespesasDto[].class);

        if (responseArray != null) {
            return Arrays.asList(responseArray);  // Converte o array para lista
        } else {
            return Collections.emptyList();  // Retorna uma lista vazia se não houver dados
        }
    }
}
