package dev_marcelo.maNotes.dto;

import dev_marcelo.maNotes.entity.Usuario;

public record RegisterDto(String login, String password, Usuario.Role role) {
}
