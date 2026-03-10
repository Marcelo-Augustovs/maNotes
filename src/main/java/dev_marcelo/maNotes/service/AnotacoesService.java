package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.AnotacoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnotacoesService {

    private final AnotacoesRepository anotacoesRepository;

    @Transactional
    public Anotacoes save(Anotacoes anotacoes){
        validateText(anotacoes.getAnotacao());
        return anotacoesRepository.save(anotacoes);
    }

    @Transactional
    public Anotacoes updateText(Long id, String newText){
        validateText(newText);
        Anotacoes notes = this.findNotesById(id);
        notes.setAnotacao(newText);

        return notes;
    }

    @Transactional
    public Anotacoes delete(Long id){
        Anotacoes notes = this.findNotesById(id);
        anotacoesRepository.deleteById(id);
        return notes;
    }

    @Transactional(readOnly = true)
    public List<Anotacoes> findAllAnotacoes() {
        return anotacoesRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Anotacoes findNotesById(Long id) {
        return anotacoesRepository.findById(id).orElseThrow(
                () -> new ApiNotFoundException("Anotação não encontrada")
        );
    }
    private void validateText(String text){
        if(text == null || text.isBlank()) {
            throw new IllegalArgumentException("Texto inválido");
        }
    }

}
