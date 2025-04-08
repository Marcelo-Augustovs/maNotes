package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Notificacao;
import dev_marcelo.maNotes.infra.security.exceptions.ApiNotFoundException;
import dev_marcelo.maNotes.repository.NotificacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificacaoService {
    private final NotificacaoRepository repository;

    @Transactional
    public Notificacao create(Notificacao notificacao){
        return repository.save(notificacao);
    }

    @Transactional(readOnly = true)
    public List<Notificacao> buscarTodasNotificacao(){
        return repository.findAll();
    }

    @Transactional
    public void delete(long id) {
       Notificacao notificacao = repository.findById(id).orElseThrow(
               () -> new ApiNotFoundException("notificacao nao encontrada")
       );
       repository.delete(notificacao);
    }
}
