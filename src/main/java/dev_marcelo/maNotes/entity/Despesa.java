package dev_marcelo.maNotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "despesa")
public class Despesa {
    @Id
    @Column(name = "nomeDaConta")
    String nomeDaConta;
    @Column(name = "valorDaConta")
    float valorDaConta;
    enum statusDaConta {
        PENDENTE, PAGO
    }

}
