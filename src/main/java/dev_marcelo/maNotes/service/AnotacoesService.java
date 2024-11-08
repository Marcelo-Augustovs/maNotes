package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.repository.AnotacoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Anotacoes updateText(Float id, String novoTexto){
        Anotacoes notes = anotacoesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Anotação não encontrada")
        );
        notes.setAnotacao(novoTexto);
        return notes;
    }

    @Transactional
    public Anotacoes delete(Float id){
        Anotacoes notes = anotacoesRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Anotação não encontrada")
        );
        anotacoesRepository.deleteById(notes.getId());
        return notes;
    }
}
