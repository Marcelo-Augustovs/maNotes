package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "despesa")
@Getter @Setter @NoArgsConstructor
public class Despesa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Float id;
    @Column(name = "nome_conta")
    private String nomeDaConta;
    @Column(name = "valor_conta")
    private float valorDaConta;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private StatusDaConta statusDaConta = StatusDaConta.PENDENTE;
    public enum StatusDaConta {
        PENDENTE, PAGO
    }

}
