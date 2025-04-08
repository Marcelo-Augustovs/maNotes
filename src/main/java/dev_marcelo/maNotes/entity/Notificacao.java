package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "notificacao")
@NoArgsConstructor @Getter
public class Notificacao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "titulo")
    private String titulo;
    @Column(name = "descricao")
    private String descricao;
    @Column(name = "horario")
    private LocalDateTime dataHora;

    public Notificacao(String titulo, String descricao, LocalDateTime dataHora) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.dataHora = dataHora;
    }
}
