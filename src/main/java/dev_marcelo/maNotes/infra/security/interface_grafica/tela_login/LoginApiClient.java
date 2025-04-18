package dev_marcelo.maNotes.infra.security.interface_grafica.tela_login;

import dev_marcelo.maNotes.infra.security.exceptions.UsernameUniqueViolationException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
        } else { // não pega o bad request, quando digita a senha errada
            throw new RuntimeException("usuario não encontrado ou não cadastrado: " + response.body());
        }
    }

    public String registrar(String login, String password) throws Exception {
        String Register_URL = BASE_URL + "/register";
        String role = "USER";

        HttpClient client = HttpClient.newHttpClient();
        // Corrige a formatação do JSON
        String jsonBody = String.format("{\"login\": \"%s\", \"password\": \"%s\", \"role\": \"%s\"}", login, password, role);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(Register_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(jsonBody))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        if (response.statusCode() == 201) {
            return response.body(); // Retorna a resposta JSON
        } else {
            throw new UsernameUniqueViolationException("Erro ao cadastrar o usuario. usuario ja existente ou caracteres nao validos " + response.body());
        }
    }
}

