package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Despesa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DespesaRepository extends JpaRepository<Despesa,String> {

    Despesa findByNomeDaConta(String nomeDaConta);

    Despesa findByValorDaConta(String valorDaConta);
}
