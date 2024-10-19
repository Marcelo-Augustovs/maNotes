package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Getter @Setter
public class Usuario implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Float id;
    @Column(name = "login",nullable = false,length = 100)
    private String login;
    @Column(name = "senha",nullable = false, length = 50)
    private String senha;
    @Enumerated
    @Column(name = "role",nullable = false,length = 25)
    private Role role = Role.user;

    enum Role{
        user
    }
}
