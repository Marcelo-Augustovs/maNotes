package dev_marcelo.maNotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;

@Entity
@Table(name = "fundos")
public class Fundos implements Serializable {
    @Id
    private Float id;
    @Column(name = "fundosArecadado")
    private String origemDoFundo;
    @Column(name = "valorRecebido")
    private float valorRecebido;
}
