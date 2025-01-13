package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Fundos;
import dev_marcelo.maNotes.infra.security.exceptions.FundosNotFoundException;
import dev_marcelo.maNotes.repository.FundosRepository;
import lombok.RequiredArgsConstructor;
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
    public Fundos updateValor(Float id,String origemDoFundo,Float valorRecebido){
        Fundos fundosAtualizados = fundosRepository.findById(id)
                .orElseThrow(
                () -> new RuntimeException("Fundo não encontrado"));
        if(fundosAtualizados != null) fundosAtualizados.setOrigemDoFundo(origemDoFundo);
        fundosAtualizados.setValorRecebido(valorRecebido);
        return fundosAtualizados;
    }

    @Transactional(readOnly = true)
    public Fundos findById(Float id){
        return fundosRepository.findById(id)
                .orElseThrow( () -> new FundosNotFoundException(String.format("fundo id:%s não encontrado",id)));
    }

    @Transactional(readOnly = true)
    public List<Fundos> findAllFundos(){
        return fundosRepository.findAll();
    }

    @Transactional
    public void delete(Float id){
        Fundos fundos = fundosRepository.findById(id).orElseThrow(
                () -> new RuntimeException(String.format("fundo id:%s não encontrado",id))
        );
        fundosRepository.delete(fundos);
    }
}
