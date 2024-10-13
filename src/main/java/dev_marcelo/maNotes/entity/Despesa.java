package dev_marcelo.maNotes.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity

public class Despesa {
    @Id
    String nomeDaConta;
    float valorDaConta;

    enum statusDaConta {
        PENDENTE, PAGO
    }

    public void adicionarDespesa(float valorDaDespesa, String nomeDaConta){
       this.nomeDaConta = nomeDaConta;
       this.valorDaConta = valorDaDespesa;
    }


}
