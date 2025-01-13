package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "fundos_despesas")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
public class FundosEDespesas  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Float id;
    @Column
    private String mes;
    @ManyToOne
    @JoinColumn(name = "id_usuario",nullable = false)
    private Usuario usuario;
    @ManyToOne
    @JoinColumn(name = "id_despesa",nullable = false)
    private Despesa despesas;
    @JoinColumn(name = "id_fundos",nullable = false)
    private Fundos fundos;


    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
}
