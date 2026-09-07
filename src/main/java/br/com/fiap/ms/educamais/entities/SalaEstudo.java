package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_sala_estudo")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class SalaEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(nullable = false, length = 100)
    private String mediador;

    @Column(nullable = false, length = 60)
    private String horario;

    @Column(nullable = false, length = 500)
    private String descricao;

    @Column(nullable = false)
    private Integer participantes;

    @Column(nullable = false)
    private Integer capacidade;

    @Column(name = "ao_vivo", nullable = false)
    private Boolean aoVivo;

    @ManyToOne
    @JoinColumn(name = "curso_relacionado_id")
    private Curso cursoRelacionado;

    public Boolean getLotada() {
        return participantes >= capacidade;
    }

    public Integer getVagasRestantes() {
        int vagas = capacidade - participantes;
        return Math.max(vagas, 0);
    }

    public Double getOcupacao() {
        if (capacidade == null || capacidade == 0) {
            return 0.0;
        }
        double ocupacao = (double) participantes / capacidade;
        return Math.min(ocupacao, 1.0);
    }
}
