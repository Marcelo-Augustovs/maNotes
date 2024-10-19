package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes_tem_fundos")
@EntityListeners(AuditingEntityListener.class)
public class FundosEDespesas  {
    @Id
    @Column(name = "id")
    private Float id;
    @ManyToOne
    @JoinColumn(name = "id_cliente")
    Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "id_despesa")
    Despesa despesas;
    @JoinColumn(name = "id_fundos")
    Fundos fundos;


    @CreatedDate
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;
    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDateTime dataModificacao;
}
