package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Lembrete;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LembreteRepository extends JpaRepository<Lembrete,Long> {
}
