package dev_marcelo.maNotes.controller;

import dev_marcelo.maNotes.infra.security.TokenService;
import dev_marcelo.maNotes.dto.AuthenticationDto;
import dev_marcelo.maNotes.dto.LoginResponseDto;
import dev_marcelo.maNotes.dto.RegisterDto;
import dev_marcelo.maNotes.entity.Usuario;
import dev_marcelo.maNotes.infra.security.exceptions.ErrorMessage;
import dev_marcelo.maNotes.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Autowired
    private TokenService tokenService;

    @Operation(summary = "Autenticar na API",description = "Recurso de autenticar na API",
            responses = {
                    @ApiResponse(responseCode = "200",description = "Autenticação realizada com sucesso e retorno de um bearer token",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = AuthenticationDto.class))),
                    @ApiResponse(responseCode = "400", description = "Credenciais inválidas",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Campo(s) Inválido(s)",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDto data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.login(),data.password());
        System.out.println(" UsernamePassword:" + usernamePassword);
        var auth = this.authenticationManager.authenticate(usernamePassword);
        System.out.println("Auth:"+ auth);

        var token = tokenService.generateToken( (Usuario) auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDto(token));
    }

    @Operation(summary = "Criar um novo usuário",description = "Recurso para criar um novo usuário",
            responses = {
                    @ApiResponse(responseCode = "201",description = "Recurso criado com sucesso",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = RegisterDto.class))),
                    @ApiResponse(responseCode = "422", description = "Recurso não processado por dados de entrada invalidos",
                            content = @Content(mediaType = "application/json",schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping("/register")
    public ResponseEntity Register(@RequestBody @Valid RegisterDto data){
        if(this.repository.findByLogin(data.login()) != null) return ResponseEntity.badRequest().build();

        String encyptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Usuario newUsuario = new Usuario(data.login(),encyptedPassword,data.role());

        this.repository.save(newUsuario);

        return ResponseEntity.ok().build();
    }
}
