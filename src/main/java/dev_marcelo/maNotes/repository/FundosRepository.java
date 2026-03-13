package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Fundos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface FundosRepository extends JpaRepository<Fundos,Long> {
    Fundos findByOrigemDoFundo(String fundos);
    List<Fundos> findByDataModificacaoBetween(LocalDate start, LocalDate end);
}
