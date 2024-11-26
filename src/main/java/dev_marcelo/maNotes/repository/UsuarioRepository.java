package dev_marcelo.maNotes.repository;

import dev_marcelo.maNotes.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario,Float> {
    Usuario findByLogin(String username);
}
