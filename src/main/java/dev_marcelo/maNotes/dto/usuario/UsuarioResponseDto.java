package dev_marcelo.maNotes.dto.usuario;

import dev_marcelo.maNotes.entity.Usuario;

public record UsuarioResponseDto(Long id, String username, Usuario.Role role) {
}
