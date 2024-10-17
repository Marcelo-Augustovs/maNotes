package dev_marcelo.maNotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Usuario {
    @Id
    @Column(name = "login")
    String login;
    @Column(name = "nome")
    String nome;
    @Column(name = "senha")
    String senha;
}
