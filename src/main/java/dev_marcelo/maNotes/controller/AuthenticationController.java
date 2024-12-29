package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.dto.AuthenticationDto;
import dev_marcelo.maNotes.dto.RegisterDto;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UsuarioRepository repository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        System.out.println(" UsernamePassword:" + usernamePassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("Auth:"+ auth);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody @Valid RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encyptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUsuario = new Usuario(data.login(),encyptedPassword,data.role());

        this.repository.save(newUsuario);

        return ResponseEntity.ok().build();
    }
}
