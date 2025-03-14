package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "fundo_despesa_mesal")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
public class FundosEDespesaMensal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column
    private int mes;
    @Column
    private Double fundos;
    @Column
    private Double Despesa;
    @Column
    private Double total;

    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @Column(name = "criado_por")
    @LastModifiedBy
    private String criadoPor;
}
