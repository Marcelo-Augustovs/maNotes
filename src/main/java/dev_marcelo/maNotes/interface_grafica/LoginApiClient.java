package dev_marcelo.maNotes.interface_grafica;

import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.AuthenticationDto;
import dev_marcelo.maNotes.entity.Usuario;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class LoginApiClient {
    private static final String BASE_URL = "http://localhost:8080/api/v1/auth";

    public String logar(String login, String password) throws Exception {
        String Login_URL = BASE_URL + "/login";

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"login\": \"%s\"," +
                "\"password\": \"%s\"}", login,password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(Login_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 200) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new RuntimeException("usuario não encontrado ou não cadastrado: " + response.body());
        }
    }

    public String registrar(String login, String password) throws Exception {
        String Register_URL = BASE_URL + "/register";
        Usuario.Role role = Usuario.Role.USER;

        HttpClient client = HttpClient.newHttpClient();
        String jsonBody = String.format("{\"login\": \"%s\"," +
                "\"password\": \"%s\"}" +
                "\"role\": \"%s\"}", login,password,role);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(Register_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new RuntimeException("Erro ao cadastrar o usuario. usuario ja existente ou caracteres nao validos " + response.body());
        }
    }
}

