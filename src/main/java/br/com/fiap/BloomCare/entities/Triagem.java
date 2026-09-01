package br.com.fiap.BloomCare.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(of = "id")
@Entity
@Table(name = "tb_triagem")
public class Triagem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate dataTriagem;

    @Column(nullable = false)
    private Boolean aptaDoacao;

    @ManyToOne
    @JoinColumn(name = "nutriz_id", nullable = false)
    private Nutriz nutriz;
}
