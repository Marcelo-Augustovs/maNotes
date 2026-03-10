package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.FundosRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FundosService {
    private final FundosRepository fundosRepository;

    @Transactional
    public Fundos save(Fundos fundos){
        return fundosRepository.save(fundos);
    }

    @Transactional
    public Fundos updatePatchIncome(Long id, String newSource, Double newValor){
        Fundos updatedIncome = findIncomeById(id);

        applyPatchUpdate(updatedIncome,newSource,newValor);

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

    private void applyPatchUpdate(Fundos income, String incomeNewSource,Double incomeNewValor){

        if(incomeNewSource != null) {
            validateIncomeSource(incomeNewSource);
            income.setOrigemDoFundo(incomeNewSource);
        }

        if(incomeNewValor != null){
            validateIncomeValor(incomeNewValor);
            income.setValorRecebido(incomeNewValor);
        }
    }

    private void validateIncomeSource(String icomeSource){
        if (icomeSource.isEmpty()){
            throw new IllegalArgumentException("Adicione a origem da Renda");
        }
    }

    private void validateIncomeValor(Double incomeValor){
        if (incomeValor <= 0){
            throw new IllegalArgumentException("o valor da renda tem que ser um número maior que zero");
        }
    }
}
