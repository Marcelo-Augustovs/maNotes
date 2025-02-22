package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.FundosEDespesasDto;
import dev_marcelo.maNotes.entity.FundosEDespesas;
import dev_marcelo.maNotes.service.FundosEDespesasService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/saldo")
public class FundosEDespesasController {

    private final FundosEDespesasService fundosEDespesasService;




}
