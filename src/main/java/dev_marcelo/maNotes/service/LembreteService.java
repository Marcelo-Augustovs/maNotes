package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LembreteService {
    private final LembreteRepository repository;
    private final UsuarioService usuarioService;

    @Transactional
    public LembreteDto create(LembreteDto dto, long usuarioId){
        Lembrete lembretes = new Lembrete();
        lembretes.setNomeDoEvento(dto.nomeDoEvento());
        lembretes.setDiaMarcado(dto.diaMarcado());
        lembretes.setUsuario(usuarioService.buscarPorId(usuarioId));
        repository.save(lembretes);
        return dto;
    }

    @Transactional
    public Lembrete update(Long id,String nomeDoEvento){
       Lembrete lembretesAtualizado = repository.findById(id)
               .orElseThrow(() -> new ApiNotFoundException("evento nao encontrado"));
       if(lembretesAtualizado != null) lembretesAtualizado.setNomeDoEvento(nomeDoEvento);
       return lembretesAtualizado;
    }

    @Transactional
    public void remove(Long id){
        Lembrete lembretes = repository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Evento nao encontrado"));
        repository.delete(lembretes);
    }

    @Transactional(readOnly = true)
    public List<Lembrete> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Long getIdByName(String nomeDoEvento) {
        Lembrete evento = repository.findByNomeDoEvento(nomeDoEvento);
        return evento.getId();
    }
}
