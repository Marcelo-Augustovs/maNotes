package dev_marcelo.maNotes.service;

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
    @Transactional
    public Despesa findByNomeDaConta(String nomeDaConta){
        return despesaRepository.findByNomeDaConta(nomeDaConta);
    }
    @Transactional
    public Despesa findByValorDaConta(Float valorDaConta){
        return despesaRepository.findByValorDaConta(valorDaConta);
    }
    @Transactional
    public List<Despesa> getAllDespesas(){
        return despesaRepository.findAll();
    }


}
