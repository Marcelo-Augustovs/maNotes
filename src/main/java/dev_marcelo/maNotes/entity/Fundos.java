package dev_marcelo.maNotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "fundos")
public class Fundos {
    @Id
    @Column(name = "fundosArecadado")
    String origemDoFundo;
    @Column(name = "valorRecebido")
    float valorRecebido;
}
