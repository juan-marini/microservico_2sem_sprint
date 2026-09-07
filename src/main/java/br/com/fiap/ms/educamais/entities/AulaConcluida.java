package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_aula_concluida", uniqueConstraints = @UniqueConstraint(columnNames = {"matricula_id", "aula_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class AulaConcluida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToOne(optional = false)
    @JoinColumn(name = "aula_id")
    private Aula aula;

    @Column(name = "data_conclusao", nullable = false)
    private LocalDateTime dataConclusao;
}
