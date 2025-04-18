package dev_marcelo.maNotes.dto.mapper;

import dev_marcelo.maNotes.dto.anotacoes.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.anotacoes.AnotacoesResponseDto;
import dev_marcelo.maNotes.entity.Anotacoes;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor
public class AnotacoesMapper {

    public static Anotacoes toAnotacoes(AnotacoesCreateDto dto){
        return new ModelMapper().map(dto,Anotacoes.class);
    }

    public static AnotacoesResponseDto toDto(Anotacoes anotacoes){
        return new ModelMapper().map(anotacoes, AnotacoesResponseDto.class);
    }

    public static List<AnotacoesResponseDto> toListDto(List<Anotacoes> listaDeAnotacao) {
        return listaDeAnotacao.stream().map(notes -> toDto(notes)).collect(Collectors.toList());
    }
}
