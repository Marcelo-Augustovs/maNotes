package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "usuario_anotacoes")
@EntityListeners(AuditingEntityListener.class)
@Getter @Setter @NoArgsConstructor
public class Anotacoes implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Float id;
    @Column(name = "anotacao")
    private String anotacao;

    @ManyToOne
    @JoinColumn(name = "id_usuario")
    private Usuario usuario;

    @LastModifiedDate
    @Column(name = "data_modificacao")
    private LocalDate dataModificacao;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Anotacoes anotacoes = (Anotacoes) o;
        return Objects.equals(id, anotacoes.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
