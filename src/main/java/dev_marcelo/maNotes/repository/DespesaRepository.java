package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Despesa;
import dev_marcelo.maNotes.entity.Fundos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DespesaRepository extends JpaRepository<Despesa,Long> {

    Despesa findByNomeDaConta(String nomeDaConta);

    Despesa findByValorDaConta(Float valorDaConta);

    List<Despesa> findByDataModificacaoBetween(LocalDate start, LocalDate end);
}
