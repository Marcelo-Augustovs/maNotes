package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.infra.security.exceptions.LembretesNotFoundException;
import dev_marcelo.maNotes.repository.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LembreteService {
    private final LembreteRepository repository;

    @Transactional
    public LembreteDto create(LembreteDto dto){
        Lembrete lembretes = new Lembrete();
        lembretes.setNomeDoEvento(dto.nomeDoEvento());
        lembretes.setDiaMarcado(dto.diaMarcado());
        repository.save(lembretes);
        return dto;
    }

    @Transactional
    public Lembrete update(Long id,String nomeDoEvento){
       Lembrete lembretesAtualizado = repository.findById(id)
               .orElseThrow(() -> new LembretesNotFoundException("evento nao encontrado"));
       if(lembretesAtualizado != null) lembretesAtualizado.setNomeDoEvento(nomeDoEvento);
       return lembretesAtualizado;
    }

    @Transactional
    public void remove(Long id){
        Lembrete lembretes = repository.findById(id)
                .orElseThrow(() -> new LembretesNotFoundException("Evento nao encontrado"));
        repository.delete(lembretes);
    }

    @Transactional(readOnly = true)
    public List<Lembrete> findAll() {
        return repository.findAll();
    }

    public Long getIdByName(String nomeDoEvento) {
        Lembrete evento = repository.findByNomeDoEvento(nomeDoEvento);
        return evento.getId();
    }
}
