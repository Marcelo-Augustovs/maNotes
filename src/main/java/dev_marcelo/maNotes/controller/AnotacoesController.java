package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.AnotacoesCreateDto;
import dev_marcelo.maNotes.dto.AnotacoesResponseDto;
import dev_marcelo.maNotes.dto.mapper.AnotacoesMapper;
import dev_marcelo.maNotes.entity.Anotacoes;
import dev_marcelo.maNotes.service.AnotacoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/anotacoes")
public class AnotacoesController {
    private final AnotacoesService anotacoesService;

    public ResponseEntity<AnotacoesResponseDto> create(AnotacoesCreateDto dto){
        Anotacoes anotacoes = AnotacoesMapper.toAnotacoes(dto);
        anotacoesService.save(anotacoes);
        return ResponseEntity.status(201).body(AnotacoesMapper.toDto(anotacoes));
    }

}
