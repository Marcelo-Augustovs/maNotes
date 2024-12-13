package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Despesa findAcountById(Float id) {
        return despesaRepository.findById(id)
                .orElseThrow( () -> new RuntimeException("despensa não encontrada"));
    }

    @Transactional
    public void pagarContar(String nomeDaConta) {
       Despesa despesa = despesaRepository.findByNomeDaConta(nomeDaConta);
       despesa.setStatusDaConta(Despesa.StatusDaConta.PAGO);
    }

    @Transactional(readOnly = true)
    public List<Despesa> findAllDespesas(){
       return despesaRepository.findAll();
    }

    @Transactional
    public void delete(Despesa despesa) {
        despesaRepository.delete(despesa);
    }

    @Transactional
    public Despesa updateDespesa(Float id, DespesaDto dto) {
      Despesa despesa = despesaRepository.findById(id).orElseThrow(
              () -> new RuntimeException("despesa não encontrada")
      );

      despesa.setNomeDaConta(
        (dto.getNomeDaConta() == null || dto.getNomeDaConta().isEmpty())
            ? despesa.getNomeDaConta()
            : dto.getNomeDaConta() );

        despesa.setValorDaConta(
                (dto.getValorDaConta() == null || dto.getValorDaConta() < 0)
                        ? despesa.getValorDaConta()
                        : dto.getValorDaConta() );
        return despesa;
    }
}
