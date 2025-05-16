package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.entity.Despesa;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/despesa/despesa-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/despesa/despesa-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class DespesaIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarDespesa_ComUsuarioLogado_RetornarAnotacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/despesa")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new DespesaDto("teste",20.00))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Despesa.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void atualizarDespesa_ComUsuarioAutenticado_RetornarStatus204(){
        var responseBody = testClient
                .patch()
                .uri("/api/v1/despesa/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue( new DespesaDto("modificando",20.00))
                .exchange()
                .expectStatus().isOk()
                .expectBody(DespesaDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public void buscarTodasDespesa_Authenticado_RetornarListaDeDespesaComStatus200(){
        List<Despesa> responseBody = testClient
                .get()
                .uri("/api/v1/despesa")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Despesa.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

}
