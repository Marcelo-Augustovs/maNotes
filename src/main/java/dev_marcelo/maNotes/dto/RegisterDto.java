package dev_marcelo.maNotes.dto;

import dev_marcelo.maNotes.entity.Usuario;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull @NotBlank String login, @NotNull @NotBlank String password, Usuario.Role role) {
}
