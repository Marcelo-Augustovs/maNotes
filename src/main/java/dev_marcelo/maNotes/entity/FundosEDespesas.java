package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "clientes_tem_fundos")
@EntityListeners(AuditingEntityListener.class)
public class FundosEDespesas {
    @ManyToOne
    Cliente cliente;
    @Column(name = "despesas")
    List<Despesa> despesas;
    @Column(name = "fundos")
    List<Fundos> fundos;


    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
}
