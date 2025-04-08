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
    private Double valorRecebido;

    @ManyToOne
    @JoinColumn(name = "usuarios_id")
    private Usuario usuario;

    @CreatedDate
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Column(name = "criado_por")
    @LastModifiedBy
    private String criadoPor;

    @Override
    public String toString() {
        return "Fundos{" +
                ", origemDoFundo='" + origemDoFundo + '\'' +
                ", valorRecebido=" + valorRecebido +
                ", dataModificacao=" + dataModificacao +
                '}';
    }
}
