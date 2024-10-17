package dev_marcelo.maNotes.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Despesa {
    @Id
    @Column(name = "nomeDaConta")
    String nomeDaConta;
    @Column(name = "valorDaConta")
    float valorDaConta;
    @Column(name = "statusDaConta")
    enum statusDaConta {
        PENDENTE, PAGO
    }

}
