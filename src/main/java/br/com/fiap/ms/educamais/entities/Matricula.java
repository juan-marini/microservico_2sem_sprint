package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_matricula", uniqueConstraints = @UniqueConstraint(columnNames = {"usuario_id", "curso_id"}))
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Matricula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    @ManyToOne(optional = false)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    @Column(name = "data_matricula", nullable = false)
    private LocalDate dataMatricula;

    @Column(name = "data_ultimo_acesso")
    private LocalDate dataUltimoAcesso;

    @Column(nullable = false)
    private Double progresso;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private RiscoEvasao risco;

    @OneToMany(mappedBy = "matricula", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AulaConcluida> aulasConcluidas = new ArrayList<>();

    public Integer getTotalAulas() {
        return curso == null ? 0 : curso.getTotalAulas();
    }

    public Integer getQuantidadeAulasConcluidas() {
        return aulasConcluidas.size();
    }

    public Integer getPercentual() {
        if (progresso == null) {
            return 0;
        }
        return (int) Math.round(progresso * 100);
    }

    public Boolean getConcluida() {
        return progresso != null && progresso >= 1.0;
    }

    public Boolean getNaoIniciada() {
        return progresso == null || progresso == 0.0;
    }

    public void calcularProgresso() {
        int total = getTotalAulas();
        if (total == 0) {
            this.progresso = 0.0;
            return;
        }
        this.progresso = (double) aulasConcluidas.size() / total;
    }

    public Aula getProximaAula() {
        Set<Long> concluidas = aulasConcluidas.stream()
                .map(item -> item.getAula().getId())
                .collect(Collectors.toSet());
        return curso.getAulasEmOrdem().stream()
                .filter(aula -> !concluidas.contains(aula.getId()))
                .findFirst()
                .orElse(null);
    }

    public boolean possuiAulaConcluida(Long aulaId) {
        return aulasConcluidas.stream()
                .anyMatch(item -> item.getAula().getId().equals(aulaId));
    }
}
