package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.repository.DespesaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DespesaService {
    private final DespesaRepository despesaRepository;
    
}
