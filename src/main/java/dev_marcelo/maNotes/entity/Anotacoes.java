package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "clientes_anotaçoes")
@EntityListeners(AuditingEntityListener.class)
public class Anotacoes implements Serializable {
    @Id
    @Column(name = "id")
    private Float id;
    @Column(name = "anotação")
    private String anotacoes;

    @ManyToOne
    @JoinColumn(name = "id_cliente",nullable = false)
    private Cliente cliente;

    @LastModifiedDate
    @Column(name = "data_ultima_modificação")
    private LocalDateTime dataUltimaModificacao;

}
