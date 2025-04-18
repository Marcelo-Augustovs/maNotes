package dev_marcelo.maNotes.dto.mapper;

import dev_marcelo.maNotes.dto.UsuarioLoginDto;
import dev_marcelo.maNotes.dto.autenticacao.UsuarioSenhaDto;
import dev_marcelo.maNotes.entity.Usuario;
import org.modelmapper.ModelMapper;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioSenhaDto dto){
        return new ModelMapper().map(dto, Usuario.class);
    }

    public static Usuario toUsuario(UsuarioLoginDto dto){
        return new ModelMapper().map(dto, Usuario.class);
    }
}
