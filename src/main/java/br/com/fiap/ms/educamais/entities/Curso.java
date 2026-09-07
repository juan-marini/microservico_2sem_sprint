package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;
import jakarta.persistence.Table;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_curso")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 60)
    private String categoria;

    @Column(nullable = false, length = 100)
    private String instrutor;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(name = "carga_horaria", nullable = false)
    private Integer cargaHoraria;

    @Column(nullable = false)
    private Boolean obrigatorio;

    private LocalDate prazo;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("ordem ASC")
    private List<Modulo> modulos = new ArrayList<>();

    public Integer getTotalAulas() {
        return modulos.stream()
                .mapToInt(Modulo::getTotalAulas)
                .sum();
    }

    public Integer getTotalModulos() {
        return modulos.size();
    }

    public List<Aula> getAulasEmOrdem() {
        return modulos.stream()
                .flatMap(modulo -> modulo.getAulas().stream())
                .toList();
    }
}
