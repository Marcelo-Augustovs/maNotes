package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "cliente")
@Getter @Setter
public class Cliente implements Serializable {

    @Column(name = "nickname")
    String nickname;
    @OneToOne
    @JoinColumn(name = "usuario",nullable = false)
    Usuario usuario;
}
