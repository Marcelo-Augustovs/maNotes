package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Notificacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificacaoRepository extends JpaRepository<Notificacao,Long> {
}
