package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.fundos_despesa.BalancoMensalDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.entity.FundosEDespesaMensal;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.DespesaRepository;
import dev_marcelo.maNotes.repository.FundosEDespesaMensalRepository;
import dev_marcelo.maNotes.repository.FundosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundosEDespesasService {

    private final FundosRepository fundosRepository;
    private final DespesaRepository despesaRepository;
    private final FundosEDespesaMensalRepository repository;

    @Transactional
    public FundosEDespesaMensal criar(int mes, Integer year){
        //if year is null, return localDate.now
        year = validateYear(year);

        BalancoMensalDto balancoMensalDto = calculateMonthlyBalance(mes,year);

        FundosEDespesaMensal fundosEDespesaMensal = createRelatorioMensal(balancoMensalDto);

        return repository.save(fundosEDespesaMensal);
    }

    @Transactional
    public BalancoMensalDto calculateMonthlyBalance(int mes, int ano) {
        List<Fundos> MonthIncomes = findMonthlyIcomes(mes,ano);
        List<Despesa> MonthExpenses = findMonthlyExpenses(mes,ano);

        double totalIncomes = calculateMonthlyIncomesTotal(MonthIncomes);
        double totalExpenses = calculateMonthlyExpensesTotal(MonthExpenses);
        double totalBalance = totalIncomes - totalExpenses;

        return new BalancoMensalDto(mes,totalIncomes,totalExpenses, totalBalance);
    }

    @Transactional(readOnly = true)
    public List<FundosEDespesaMensal> findAll() {
       return repository.findAll();
    }

    @Transactional
    public FundosEDespesaMensal atualizarMes(int mes, int ano) {
        BalancoMensalDto newBalance = calculateMonthlyBalance(mes, ano);

        FundosEDespesaMensal saldo = updateMonthExtract(newBalance,ano);

        return repository.save(saldo);
    }

    @Transactional
    public void deletarFundosEDespesasMensal(Long id) {
        FundosEDespesaMensal fundosEDespesaMensal = repository.findById(id).orElseThrow(
                () -> new ApiNotFoundException(String.format("id:%s não encontrado",id))
        );
        repository.delete(fundosEDespesaMensal);
    }

    private int validateYear(Integer year){
        if (year == null || year == 0){
         return year = LocalDate.now().getYear();
        }
        return year;
    }

    private List<Fundos> findMonthlyIcomes(int month, int year){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Fundos> incomeMonth = fundosRepository.findByDataModificacaoBetween(start, end);

        return incomeMonth;
    }

    private List<Despesa> findMonthlyExpenses(int month, int year){
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());

        List<Despesa> expenseMonth = despesaRepository.findByDataModificacaoBetween(start, end);

        return expenseMonth;
    }

    private Double calculateMonthlyIncomesTotal(List<Fundos> incomeMonth){
        double sumMonthIncome = incomeMonth.stream()
                .map(fundos -> fundos.getValorRecebido())
                .reduce(0.00,Double::sum);

        return sumMonthIncome;
    }

    private Double calculateMonthlyExpensesTotal(List<Despesa> MonthExpense){
        double sumMonthExpense = MonthExpense.stream()
                .map(expenses -> expenses.getValorDaConta())
                .reduce(0.00,Double::sum);

        return sumMonthExpense;
    }

    private FundosEDespesaMensal createRelatorioMensal(BalancoMensalDto balancoMensalDto){
        FundosEDespesaMensal relatorioMensal = new FundosEDespesaMensal();

        relatorioMensal.setMes(balancoMensalDto.mes());
        relatorioMensal.setFundos(balancoMensalDto.totalFundos());
        relatorioMensal.setDespesa(balancoMensalDto.totalDespesas());
        relatorioMensal.setTotal(balancoMensalDto.ganhoDoMes());

        return relatorioMensal;
    }

    private FundosEDespesaMensal updateMonthExtract(BalancoMensalDto newBalance, int year){
        int month = newBalance.mes();

        FundosEDespesaMensal monthExtract = monthExtract(month,year);

        monthExtract.setFundos(newBalance.totalFundos());
        monthExtract.setDespesa(newBalance.totalDespesas());
        monthExtract.setTotal(newBalance.ganhoDoMes());

        return monthExtract;
    }

    private FundosEDespesaMensal monthExtract(int month, int year){
        FundosEDespesaMensal monthExtract = repository.findAll().stream()
                .filter(saldoMensal -> saldoMensal.getMes() == month && saldoMensal.getDataCriacao().getYear() == year)
                .findFirst()
                .orElseThrow(() -> new ApiNotFoundException("Nenhum registro encontrado para o mês " + month + " e ano " + year));

        return monthExtract;
    }
}
