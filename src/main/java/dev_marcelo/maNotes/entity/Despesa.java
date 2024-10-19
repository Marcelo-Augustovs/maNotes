package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "despesa")
public class Despesa implements Serializable {
    @Id
    @Column(name = "id")
    private Float id;
    @Column(name = "nomeDaConta")
    private String nomeDaConta;
    @Column(name = "valorDaConta")
    private float valorDaConta;
    @Enumerated(EnumType.STRING)
    private StatusDaConta statusDaConta;
    enum StatusDaConta {
        PENDENTE, PAGO
    }

}
