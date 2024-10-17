package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.entity.Cliente;
import dev_marcelo.maNotes.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ClienteService {
    private final ClienteRepository clienteRepository;

    @Transactional
    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    @Transactional
    public Cliente mudarNickname(String antigoNickname,String novoNickname){
       Cliente cliente = clienteRepository.findById(antigoNickname).orElseThrow();
       cliente.setNickname(novoNickname);
       return clienteRepository.save(cliente);
    }


}
