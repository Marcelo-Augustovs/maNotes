package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.fundos.FundosValoresDto;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/fundos/fundos-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(scripts = "/sql/fundos/fundos-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class FundosIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarFundos_ComUsuarioLogado_RetornarAnotacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/fundos")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana", "123"))
                .bodyValue(new FundosValoresDto("teste",20.00))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
    @Test
    public void criarFundos_Usuario_RetornarStatus401(){
        ErrorMessage responseBody = testClient
                .post()
                .uri("/api/v1/fundos")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FundosValoresDto("teste",20.00))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();

    }

    @Test
    public void atualizarFundos_ComUsuarioAutenticado_RetornarStatus204(){
        var responseBody = testClient
                .patch()
                .uri("/api/v1/fundos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana", "123"))
                .bodyValue( new FundosValoresDto("modificando",20.00))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }
    @Test
    public void atualizarFundos_SemUsuario_RetornarStatus204(){
       ErrorMessage responseBody = testClient
                .patch()
                .uri("/api/v1/fundos/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue( new FundosValoresDto("modificando",20.00))
                .exchange()
                .expectStatus().isForbidden()
                .expectBody(ErrorMessage.class)
                .returnResult().getResponseBody();


    }

    @Test
    public void buscarTodosFundos_Authenticado_RetornarListaDeDespesaComStatus200(){
        List<Fundos> responseBody = testClient
                .get()
                .uri("/api/v1/fundos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana", "123"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
    @Test
    public void buscarTodosFundos_SemAuthenticacao_RetornarStatus401(){
        List<Fundos> responseBody = testClient
                .get()
                .uri("/api/v1/fundos")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana", "123"))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void deletarDespesa_ComUsuarioLogado_RetornarStatus204(){
        var responseBody = testClient
                .delete()
                .uri("/api/v1/fundos/100")
                .headers(JwtAuthentication.getHeaderAuthorization(testClient, "ana", "123"))
                .exchange()
                .expectStatus().isNoContent();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
    @Test
    public void deletarDespesa_SemUsuario_RetornarStatus401(){
        var responseBody = testClient
                .delete()
                .uri("/api/v1/fundos/100")
                .exchange()
                .expectStatus().isForbidden();

    }
}
