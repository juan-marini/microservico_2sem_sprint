package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_modulo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Modulo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @OneToMany(mappedBy = "modulo", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<Aula> aulas = new ArrayList<>();

    public Integer getTotalAulas() {
        return aulas.size();
    }

    public Integer getAulasConcluidas() {
        return (int) aulas.stream()
                .filter(aula -> Boolean.TRUE.equals(aula.getConcluida()))
                .count();
    }

    public Double getProgresso() {
        if (aulas.isEmpty()) {
            return 0.0;
        }
        return (double) getAulasConcluidas() / aulas.size();
    }

    public Boolean getConcluido() {
        return !aulas.isEmpty() && getAulasConcluidas().equals(aulas.size());
    }

    public Integer getDuracaoMinutos() {
        return aulas.stream()
                .mapToInt(Aula::getDuracaoMinutos)
                .sum();
    }
}
