package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;

    @Transactional
    public Despesa salvar(Despesa despesa){
        return  despesaRepository.save(despesa);
    }
    @Transactional(readOnly = true)
    public Despesa findByNomeDaConta(String nomeDaConta){
        return despesaRepository.findByNomeDaConta(nomeDaConta);
    }
    @Transactional(readOnly = true)
    public Despesa findByValorDaConta(Float valorDaConta){
        return despesaRepository.findByValorDaConta(valorDaConta);
    }
    @Transactional(readOnly = true)
    public List<Despesa> getAllDespesas(){
        return despesaRepository.findAll();
    }


    @Transactional(readOnly = true)
    public Despesa findExpenseById(Long id) {
        return despesaRepository.findById(id)
                .orElseThrow( () -> new ApiNotFoundException("despensa não encontrada"));
    }

    @Transactional
    public void payExpense(Long id) {
       Despesa despesa = findExpenseById(id);
       despesa.setStatusDaConta(Despesa.StatusDaConta.PAGO);
    }

    @Transactional(readOnly = true)
    public Page<Despesa> findAllDespesas(Pageable pageable){
       return despesaRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Despesa despesa) {
        despesaRepository.delete(despesa);
    }

    @Transactional
    public Despesa updateDespesa(Long id, DespesaDto updatedExpense) {
        Despesa despesa =  findExpenseById(id);

        applyPatchUpdate(updatedExpense,despesa);

        return despesa;
    }

    private void validateExpenseName(String nome) {
        if (nome.isBlank()) {
            throw new IllegalArgumentException("Nome inválido");
        }
    }

    private void validateExpenseValor(Double valor) {
        if (valor < 0) {
            throw new IllegalArgumentException("Valor inválido");
        }
    }

    private void applyPatchUpdate(DespesaDto updatedExpense, Despesa despesa){
       String updateExpenseName = updatedExpense.getNomeDaConta();
       Double updatedExpenseValor = updatedExpense.getValorDaConta();
       String updatedCategory = updatedExpense.getCategory();
       String updatedPayment = updatedExpense.getPayment();
       LocalDate updatedData = updatedExpense.getDataModificacao();

        if (updateExpenseName != null) {
            validateExpenseName(updateExpenseName);
            despesa.setNomeDaConta(updateExpenseName);
        }
        if (updatedExpenseValor != null) {
            validateExpenseValor(updatedExpenseValor);
            despesa.setValorDaConta(updatedExpenseValor);
        }
        if (updatedCategory != null) {
            validateExpenseName(updatedCategory);
            despesa.setCategory(updatedCategory);
        }
        if (updatedPayment != null) {
            validateExpenseName(updatedPayment);
            despesa.setPayment(updatedPayment);
        }
        if (updatedData != null) {
            despesa.setDataModificacao(updatedData);
        }
    }

    public Page<Despesa> findDespesas(String start, String end, Pageable pageable) {
        {

            if (start != null && end != null) {
                LocalDate startDate = LocalDate.parse(start);
                LocalDate endDate = LocalDate.parse(end);

                return despesaRepository.findByDataModificacaoBetween(startDate, endDate, pageable);
            }

            return despesaRepository.findAll(pageable);
        }
    }
}
