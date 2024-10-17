package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;

@Entity
public class Cliente {
    @Id
    @Column(name = "nickname")
    String nickname;
    @OneToMany
    Anotacoes anotacoes;
    @OneToOne
    FundosEDespesas fundosEDespesas;
}
