package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "clientes_anotaçoes")
@EntityListeners(AuditingEntityListener.class)
public class Anotacoes {
    @Column(name = "anotação")
    private String anotacoes;

    @ManyToOne
    Cliente cliente;

    @CreatedDate
    @LastModifiedDate
    @Column(name = "data_criação_modificação")
    private LocalDateTime dataCriacaoEModificacao;

}
