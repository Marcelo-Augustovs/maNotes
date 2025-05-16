package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.anotacoes.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/anotacao/anotacao-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/anotacao/anotacao-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AnotacaoIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarAnotacao_ComUsuarioLogado_RetornarAnotacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/anotacoes")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AnotacoesCreateDto("anotação criada com sucesso"))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AnotacoesResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void atualizarTexto_ComUsuarioAutenticado_RetornarStatus204(){
       var responseBody = testClient
                .patch()
                .uri("/api/v1/anotacoes/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue( new AnotacoesCreateDto("novo texto"))
                .exchange()
                .expectStatus().isOk()
                .expectBody(AnotacoesResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public void buscarTodosUsuarios_Authenticado_RetornarListaDeUsuariosComStatus200(){
        List<AnotacoesResponseDto> responseBody = testClient
                .get()
                .uri("/api/v1/anotacoes")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AnotacoesResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

}
