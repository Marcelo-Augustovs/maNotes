package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter @Setter
public class Cliente {
    @Id
    @Column(name = "nickname")
    String nickname;
    @OneToMany
    @Column(name = "anotacoes")
    Anotacoes anotacoes;
    @OneToOne
    @Column(name = "fundosEDespesas")
    FundosEDespesas fundosEDespesas;
}
