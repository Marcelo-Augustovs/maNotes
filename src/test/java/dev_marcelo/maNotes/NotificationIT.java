package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.anotacoes.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import dev_marcelo.maNotes.entity.Notificacao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/notificacao/notificacao-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/notificacao/notificacao-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class NotificationIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarNotificacao_ComUsuarioLogado_RetornarNotificacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/notificacao")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Notificacao("teste","descricao", LocalDateTime.of(2025,5,12,6,0)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Notificacao.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void buscarTodasNotificacao_Authenticado_RetornarListaDeNotificacaoComStatus200(){
        List<Notificacao> responseBody = testClient
                .get()
                .uri("/api/v1/notificacao")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Notificacao.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void deletarNotificacao_ComUsuarioLogado_RetornarAnotacaoComStatus204(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/notificacao")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AnotacoesCreateDto("anotação criada com sucesso"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AnotacoesResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }


}
