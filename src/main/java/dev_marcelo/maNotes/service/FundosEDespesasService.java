package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.BalancoMensal;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import dev_marcelo.maNotes.repository.DespesaRepository;
import dev_marcelo.maNotes.repository.FundosEDespesaMensalRepository;
import dev_marcelo.maNotes.repository.FundosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FundosEDespesasService {

    private final FundosRepository fundosRepository;
    private final DespesaRepository despesaRepository;
    private final FundosEDespesaMensalRepository repository;

    @Transactional
    public FundosEDespesaMensal criar(int mes, Integer ano){

        if (ano == null || ano == 0){
            ano = LocalDate.now().getYear();
        }

        BalancoMensal balancoMensal = calcularTotal(mes,ano);
        FundosEDespesaMensal fundosEDespesaMensal = new FundosEDespesaMensal();

        fundosEDespesaMensal.setMes(balancoMensal.mes());
        fundosEDespesaMensal.setFundos(balancoMensal.totalFundos());
        fundosEDespesaMensal.setDespesa(balancoMensal.totalDespesas());
        fundosEDespesaMensal.setTotal(balancoMensal.ganhoDoMes());
        return repository.save(fundosEDespesaMensal);
    }
    @Transactional
    public BalancoMensal calcularTotal(int mes, int ano) {
        List<Fundos> listaFundos =  fundosRepository.findAll();
        List<Despesa> listaDespesa = despesaRepository.findAll();

        // Filtra os registros pelo mês especificado
        List<Fundos> fundosDoMes = listaFundos.stream()
                .filter(item -> item.getDataModificacao().getMonthValue() == mes && item.getDataModificacao().getYear() == ano)
                .collect(Collectors.toList());

        List<Despesa> despesasDoMes = listaDespesa.stream()
                .filter(item -> item.getDataModificacao().getMonthValue() == mes && item.getDataModificacao().getYear() == ano)
                .collect(Collectors.toList());

        // Soma os valores recebidos dos fundos
        double totalFundos = fundosDoMes.stream()
                .map(fundos -> fundos.getValorRecebido())// Extrai os valores
                .reduce(0.00,Double::sum); // Soma os valores

        // Soma os valores das despesas
        double totalDespesas = despesasDoMes.stream()
                .map(despesa -> despesa.getValorDaConta()) // Extrai os valores
                .reduce(0.00, Double::sum); // Soma os valores

        // Exibe os totais calculados
        System.out.println("Total de Fundos no mês " + mes + ": " + totalFundos);
        System.out.println("Total de Despesas no mês " + mes + ": " + totalDespesas);

        double ganhoDoMes = totalFundos - totalDespesas;
        return new BalancoMensal(mes,totalFundos,totalDespesas, ganhoDoMes);
    }

    @Transactional(readOnly = true)
    public List<FundosEDespesaMensal> findAll() {
       return repository.findAll();
    }

    @Transactional
    public FundosEDespesaMensal atualizarMes(int mes, int ano) {
        BalancoMensal saldoAtualizado = calcularTotal(mes, ano);

        FundosEDespesaMensal saldo = repository.findAll().stream()
                .filter(saldoMensal -> saldoMensal.getMes() == mes && saldoMensal.getDataCriacao().getYear() == ano)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Nenhum registro encontrado para o mês " + mes + " e ano " + ano));

        saldo.setFundos(saldoAtualizado.totalFundos());
        saldo.setDespesa(saldoAtualizado.totalDespesas());
        saldo.setTotal(saldoAtualizado.ganhoDoMes());

        return repository.save(saldo);
    }

    @Transactional
    public void deletarFundosEDespesasMensal(Long id) {
        FundosEDespesaMensal fundosEDespesaMensal = repository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("id:%s não encontrado",id))
        );
        System.out.println(fundosEDespesaMensal);
        repository.delete(fundosEDespesaMensal);
    }
}
