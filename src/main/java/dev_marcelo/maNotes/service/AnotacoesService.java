package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.infra.security.exceptions.AnotacaoNotFoundException;
import dev_marcelo.maNotes.repository.AnotacoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AnotacoesService {

    private final AnotacoesRepository anotacoesRepository;

    @Transactional
    public Anotacoes save(Anotacoes anotacoes){
        return anotacoesRepository.save(anotacoes);
    }

    @Transactional
    public Anotacoes updateText(Long id, String novoTexto){
        Anotacoes notes = anotacoesRepository.findById(id).orElseThrow(
                () -> new AnotacaoNotFoundException("Anotação não encontrada")
        );
        notes.setAnotacao(novoTexto);
        return notes;
    }

    @Transactional
    public Anotacoes delete(Long id){
        Anotacoes notes = anotacoesRepository.findById(id).orElseThrow(
                () -> new AnotacaoNotFoundException("Anotação não encontrada")
        );
        anotacoesRepository.deleteById(notes.getId());
        return notes;
    }

    @Transactional(readOnly = true)
    public List<Anotacoes> findAllAnotacoes() {
        List<Anotacoes> notes = new ArrayList<>();
        notes = anotacoesRepository.findAll();
        return notes;
    }
    @Transactional(readOnly = true)
    public Anotacoes findAnotacao(Long id) {
        return anotacoesRepository.findById(id).orElseThrow(
                () -> new AnotacaoNotFoundException("Anotação não encontrada")
        );
    }
}
