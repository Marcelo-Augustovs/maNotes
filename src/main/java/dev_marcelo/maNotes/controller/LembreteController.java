package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.LembreteDto;
import dev_marcelo.maNotes.service.LembreteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/calendario")
public class LembreteController {
    private final LembreteService lembreteService;

    @PostMapping
    public ResponseEntity<LembreteDto> criar(@RequestBody LembreteDto dto){
       lembreteService.create(dto);
       return ResponseEntity.status(201).body(dto);
    }
}
