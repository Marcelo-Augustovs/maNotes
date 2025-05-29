package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "despesa")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
public class Despesa implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "nome_conta")
    private String nomeDaConta;
    @Column(name = "valor_conta")
    private Double valorDaConta;

    @ManyToOne
    @JoinColumn(name = "usuario_id",referencedColumnName = "id")
    private Usuario usuario;

    @CreatedDate
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Column(name = "criado_por")
    @LastModifiedBy
    private String criadoPor;

    @Enumerated(EnumType.STRING)
    private StatusDaConta statusDaConta = StatusDaConta.PENDENTE;
    public enum StatusDaConta {
        PENDENTE, PAGO
    }

    @Override
    public String toString() {
        return "Despesa{" +
                "nomeDaConta='" + nomeDaConta + '\'' +
                ", valorDaConta=" + valorDaConta +
                ", dataModificacao=" + dataModificacao +
                ", statusDaConta='" + statusDaConta + '\'' +
                '}';
    }
}
