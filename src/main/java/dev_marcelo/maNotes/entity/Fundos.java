package dev_marcelo.maNotes.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "fundos")
@Getter @Setter @NoArgsConstructor
public class Fundos implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Float id;
    @Column(name = "fundos_arecadado")
    private String origemDoFundo;
    @Column(name = "valor_recebido")
    private float valorRecebido;
}
