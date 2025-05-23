package dev_marcelo.maNotes;

import dev_marcelo.maNotes.dto.anotacoes.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.fundos_despesa.FundosEDespesasDto;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(scripts = "/sql/relatorio-mensal/relatorio-mensal-insert.sql",executionPhase = Sql.ExecutionPhase.BEFORE_TEST_CLASS)
@Sql(scripts = "/sql/relatorio-mensal/relatorio-mensal-delete.sql",executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RelatorioMensalIT {

    @Autowired
    WebTestClient testClient;

    @Test
    public void criarRelatorioMensal_ComUsuarioLogado_RetornarFundosEDespesasComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/saldo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FundosEDespesasDto(2,2025))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(FundosEDespesaMensal.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void buscarTodosRelatorios_Authenticado_RetornarListaDeFundosEDespesaMensalComStatus200(){
        List<FundosEDespesaMensal> responseBody = testClient
                .get()
                .uri("/api/v1/saldo")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(FundosEDespesaMensal.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void AtualizarRelatorioMensal_ComUsuarioLogado_RetornarFundosEDespesasComStatus200(){
        var responseBody = testClient
                .patch()
                .uri("/api/v1/saldo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new FundosEDespesasDto(3,2025))
                .exchange()
                .expectStatus().isOk()
                .expectBody(FundosEDespesaMensal.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }

    @Test
    public void deletarNotificacao_ComUsuarioLogado_RetornarAnotacaoComStatus201(){
        var responseBody = testClient
                .post()
                .uri("/api/v1/saldo")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new AnotacoesCreateDto("anotação criada com sucesso"))
                 .exchange()
                .expectStatus().isCreated()
                .expectBody(AnotacoesResponseDto.class)
                .returnResult().getResponseBody();

        org.assertj.core.api.Assertions.assertThat(responseBody).isNotNull();
    }
}
