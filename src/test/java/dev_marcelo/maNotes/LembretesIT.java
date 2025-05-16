package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.dto.fundos.FundosValoresDto;
import dev_marcelo.maNotes.entity.Fundos;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/lembrete/lembretes-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/lembrete/lembretes-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class LembretesIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarLembretes_ComUsuarioLogado_RetornarAnotacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/calendario")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new LembreteDto(200L,"evento",LocalDate.of(2025, 5, 12)))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(LembreteDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void atualizarLembretes_ComUsuarioAutenticado_RetornarStatus204(){
        var responseBody = testClient
                .patch()
                .uri("/api/v1/calendario/100")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue( new FundosValoresDto("modificando",20.00))
                .exchange()
                .expectStatus().isOk()
                .expectBody(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();

    }

    @Test
    public void buscarTodosLembretes_Authenticado_RetornarListaDeDespesaComStatus200(){
        List<Fundos> responseBody = testClient
                .get()
                .uri("/api/v1/calendario")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Fundos.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

}
