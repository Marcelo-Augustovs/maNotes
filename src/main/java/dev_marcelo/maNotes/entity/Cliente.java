package dev_marcelo.maNotes.entity;

import dev_marcelo.maNotes.util.Calcular;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Cliente {
    @Id
    @Column(name = "nickname")
    String nickname;
    Calcular balance;
    @OneToMany
    Anotacoes anotacoes;
    FundosEDespesas fundosEDespesas;
}
