package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.BalancoMensal;
import dev_marcelo.maNotes.dto.FundosEDespesasDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.entity.FundosEDespesas;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.UsuarioNotFoundException;
import dev_marcelo.maNotes.repository.DespesaRepository;
import dev_marcelo.maNotes.repository.FundosEDespesasRepository;
import dev_marcelo.maNotes.repository.FundosRepository;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundosEDespesasService {

    private final FundosRepository fundosRepository;
    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FundosEDespesasRepository repository;

    @Transactional
    public FundosEDespesasDto criar(int mes){
        // Obter o usuário autenticado pelo Spring Security
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        // Certifique-se de que o principal seja do tipo esperado (por exemplo, sua classe Usuario)
        Usuario usuarioLogado = (Usuario) auth.getPrincipal();

        List<Fundos> fundosDoMes = fundosRepository.findAll().stream()
                .filter(item -> item.getDataModificacao().getMonthValue() == mes
                        && item.getUsuario().getId().equals(usuarioLogado.getId()))
                .collect(Collectors.toList());

        List<Despesa> despesaDoMes = despesaRepository.findAll().stream()
                .filter(item -> item.getDataModificacao().getMonthValue() == mes
                        && item.getUsuario().getId().equals(usuarioLogado.getId()))
                .collect(Collectors.toList());

        return new FundosEDespesasDto(mes, usuarioLogado,despesaDoMes,fundosDoMes);
    }
    @Transactional
    public BalancoMensal calcularTotal(int mes) {

        List<FundosEDespesas> lista = repository.findAll();

        // Filtra os registros pelo mês especificado
        List<FundosEDespesas> filtrados = lista.stream()
                .filter(item -> item.getDataCriacao().getMonthValue() == mes)
                .collect(Collectors.toList());

        // Soma os valores recebidos dos fundos
        float totalFundos = filtrados.stream()
                .map(item -> item.getFundos().getValorRecebido()) // Extrai os valores
                .reduce(0f, Float::sum); // Soma os valores

        // Soma os valores das despesas
        float totalDespesas = filtrados.stream()
                .map(item -> item.getDespesas().getValorDaConta()) // Extrai os valores
                .reduce(0f, Float::sum); // Soma os valores

        // Exibe os totais calculados
        System.out.println("Total de Fundos no mês " + mes + ": " + totalFundos);
        System.out.println("Total de Despesas no mês " + mes + ": " + totalDespesas);

        double media = totalFundos - totalDespesas;
        return new BalancoMensal(mes,totalFundos,totalDespesas,media);
    }
}
