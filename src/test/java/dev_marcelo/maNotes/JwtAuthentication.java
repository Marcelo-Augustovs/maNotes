package dev_marcelo.maNotes;


import dev_marcelo.maNotes.dto.autenticacao.AuthenticationDto;
import dev_marcelo.maNotes.dto.autenticacao.LoginResponseDto;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

public class JwtAuthentication{

    public static Consumer<HttpHeaders> getHeaderAuthorization(WebTestClient client, String username, String password){
        String token = client
                .post()
                .uri("/api/v1/auth")
                .bodyValue(new AuthenticationDto(username,password))
                .exchange()
                .expectStatus().isOk()
                .expectBody(LoginResponseDto.class)
                .returnResult().getResponseBody().token();
        return headers -> headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
