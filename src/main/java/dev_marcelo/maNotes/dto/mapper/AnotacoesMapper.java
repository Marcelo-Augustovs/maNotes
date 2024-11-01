package dev_marcelo.maNotes.dto.mapper;

import dev_marcelo.maNotes.dto.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.entity.Anotacoes;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class AnotacoesMapper {

    public static Anotacoes toAnotacoes(AnotacoesCreateDto dto){
        return new ModelMapper().map(dto,Anotacoes.class);
    }

    public static AnotacoesResponseDto toDto(Anotacoes anotacoes){
        return new ModelMapper().map(anotacoes, AnotacoesResponseDto.class);
    }

}
