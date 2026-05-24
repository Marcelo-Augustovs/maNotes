package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.AnotacoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnotacoesService {

    private final AnotacoesRepository anotacoesRepository;
    private final UsuarioService usuarioService;

    @Transactional
    public Anotacoes save(Anotacoes anotacoes, Usuario usuario){
        anotacoes.setUsuario(usuarioService.findUserById(usuario.getId()));
        createPosition(anotacoes);
        return anotacoesRepository.save(anotacoes);
    }

    @Transactional
    public Anotacoes updateNotes(Long id, String newText, String newTitle){
      Anotacoes notes = this.findNotesById(id);
      String oldText = notes.getNotes();
      String oldTitle = notes.getTitle();

      String text =  updateTextOrReturnOld(oldText, newText);
      String title = updateTextOrReturnOld(oldTitle, newTitle);

      notes.setTitle(title);
      notes.setNotes(text);

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
    private String updateTextOrReturnOld(String text, String newText){
        if(newText == null || newText.isBlank()) {
            return text;
        }
        return newText;
    }

    private void validatePosition(Integer position) {
        if (position == null) {
            throw new IllegalArgumentException("posição inválida");
        }
    }

    private void createPosition(Anotacoes anotacoes){
        try {
            Integer position = anotacoesRepository.findAll().size();
            anotacoes.setPosition(position + 1);
        } catch (Exception e) {
            anotacoes.setPosition(1);
        }
    }
}
