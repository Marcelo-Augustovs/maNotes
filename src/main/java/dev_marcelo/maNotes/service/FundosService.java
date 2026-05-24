package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.FundosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
public class FundosService {
    private final FundosRepository fundosRepository;

    @Transactional
    public Fundos save(Fundos fundos){
        return fundosRepository.save(fundos);
    }

    @Transactional
    public Fundos updatePatchIncome(
            Long id,
            String newSource,
            Double newValor,
            String newcategoria,
            LocalDate newdataModificacao,
            String newpagamento){
        Fundos updatedIncome = findIncomeById(id);

        applyPatchUpdate(updatedIncome,newSource,newValor,newcategoria,newdataModificacao,newpagamento);

        return updatedIncome;
    }

    @Transactional(readOnly = true)
    public Fundos findIncomeById(Long id){
        return fundosRepository.findById(id)
                .orElseThrow( () -> new ApiNotFoundException(String.format("fundo id:%s não encontrado",id)));
    }

    @Transactional(readOnly = true)
    public Page<Fundos> findAllFundos(Pageable pageable){
        return fundosRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long id){
        Fundos fundos = findIncomeById(id);
        fundosRepository.delete(fundos);
    }

    private void applyPatchUpdate(Fundos income, String incomeNewSource,Double incomeNewValor,String incomeNewCategoria,LocalDate incomeNewDataModificacao, String IncomeNewPagamento){

        if(incomeNewSource != null) {
            validateIncomeString(incomeNewSource);
            income.setOrigemDoFundo(incomeNewSource);
        }

        if(incomeNewValor != null){
            validateIncomeValor(incomeNewValor);
            income.setValorRecebido(incomeNewValor);
        }

        if(incomeNewCategoria != null){
            validateIncomeString(incomeNewCategoria);
            income.setCategoria(incomeNewCategoria);
        }

        if(IncomeNewPagamento != null){
            validateIncomeString(IncomeNewPagamento);
            income.setPagamento(IncomeNewPagamento);
        }

        if(incomeNewDataModificacao != null){
            income.setDataModificacao(incomeNewDataModificacao);
        }
    }
    private void validateIncomeString(String icomeString){
        if (icomeString.isEmpty()){
            throw new IllegalArgumentException("O(s) campo(s) não podem ser vazio(s)");
        }
    }

    private void validateIncomeValor(Double incomeValor){
        if (incomeValor <= 0){
            throw new IllegalArgumentException("o valor da renda tem que ser um número maior que zero");
        }
    }

    public Page<Fundos> findFundos(String start, String end, Pageable pageable){

        if (start != null && end != null) {
            LocalDate startDate = LocalDate.parse(start);
            LocalDate endDate = LocalDate.parse(end);

            return fundosRepository.findByDataModificacaoBetween(startDate, endDate, pageable);
        }

        return fundosRepository.findAll(pageable);
    }
}
