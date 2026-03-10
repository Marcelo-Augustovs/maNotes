package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.entity.Lembrete;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.LembreteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class LembreteService {
    private final LembreteRepository repository;
    private final UsuarioService usuarioService;

    @Transactional
    public LembreteDto create(LembreteDto reminderDto, long userId){
        Lembrete reminder = new Lembrete();
        Usuario loggedUser = usuarioService.buscarPorId(userId);


        mapDtoToEntity(reminder, reminderDto,loggedUser);

        repository.save(reminder);
        return reminderDto;
    }

    @Transactional
    public Lembrete update(Long id,String eventName){
        Lembrete reminder = findReminderById(id);

        applyPatchUpdate(reminder,eventName);

        return reminder;
    }

    @Transactional
    public void remove(Long id){
        Lembrete lembrete = findReminderById(id);
        repository.delete(lembrete);
    }

    @Transactional(readOnly = true)
    public Page<Lembrete> findAll(
            @PageableDefault(sort = "id")Pageable pageable
            ) {
        return repository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Long getIdByName(String nomeDoEvento) {
        Lembrete evento = repository.findByNomeDoEvento(nomeDoEvento);
        return evento.getId();
    }

    private Lembrete findReminderById(Long id){
    return  repository.findById(id)
                .orElseThrow(() -> new ApiNotFoundException("Evento nao encontrado"));
    }

    private void validateEventName(String event){
        if (event.isBlank()){
            throw new IllegalArgumentException("Event invalido");
        }
    }

    private void applyPatchUpdate(Lembrete reminder, String eventName){
        if(eventName != null)
            validateEventName(eventName);
        reminder.setNomeDoEvento(eventName);
    }

    private void mapDtoToEntity(Lembrete reminder, LembreteDto reminderDto, Usuario loggedUser){
        String eventName = reminderDto.nomeDoEvento();
        LocalDate chosenDay = reminderDto.diaMarcado();


        reminder.setNomeDoEvento(eventName);
        reminder.setDiaMarcado(chosenDay);
        reminder.setUsuario(loggedUser);
    }
}
