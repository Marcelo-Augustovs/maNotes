package dev_marcelo.maNotes.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FundosEDespesasService {

    private final FundosRepository fundosRepository;
    private final DespesaRepository despesaRepository;
    private final UsuarioRepository usuarioRepository;
    private final FundosEDespesasRepository repository;

    @Transactional
    public FundosEDespesas salvar(long id,FundosEDespesasDto dto){
        Fundos fundos = fundosRepository.findByOrigemDoFundo(dto.fundos());
        Despesa despesa = despesaRepository.findByNomeDaConta(dto.despesa());
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow( () -> new UsuarioNotFoundException(String.format("usuario id %d não encontrado",id))
                );
        return repository.save(new FundosEDespesas(dto.mes(),usuario,despesa,fundos));
    }
}
