package dev_marcelo.maNotes.dto.mapper;

import dev_marcelo.maNotes.dto.DespesaDto;
import dev_marcelo.maNotes.entity.Despesa;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;

@NoArgsConstructor
public class DespesaMapper {

    public static Despesa toDespesa(DespesaDto dto){
        return new ModelMapper().map(dto,Despesa.class);
    }

    public static DespesaDto toDto(Despesa despesa){
        return new ModelMapper().map(despesa, DespesaDto.class);
    }
}
