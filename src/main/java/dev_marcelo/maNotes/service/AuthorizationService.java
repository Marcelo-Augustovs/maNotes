package dev_marcelo.maNotes.service;

import dev_marcelo.maNotes.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UsuarioRepository usuarioRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository.findByLogin(username);
    }
}
