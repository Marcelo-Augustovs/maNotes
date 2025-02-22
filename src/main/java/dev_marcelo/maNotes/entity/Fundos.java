package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "fundos")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
public class Fundos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "fundos_arecadado")
    private String origemDoFundo;
    @Column(name = "valor_recebido")
    private float valorRecebido;

    @ManyToOne
    @JoinColumn(name = "id_usuario",referencedColumnName = "id")
    private Usuario usuario;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Column(name = "criado_por")
    @LastModifiedBy
    private String criadoPor;
}
