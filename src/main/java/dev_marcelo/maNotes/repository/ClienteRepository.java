package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente,String> {

}
