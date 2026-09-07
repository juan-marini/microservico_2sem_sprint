package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Table(name = "tb_aula")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Aula {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoAula tipo;

    @Column(nullable = false)
    private Integer ordem;

    @ManyToOne(optional = false)
    @JoinColumn(name = "modulo_id")
    private Modulo modulo;

    public String getDuracaoFormatada() {
        if (duracaoMinutos == null) {
            return "";
        }
        if (duracaoMinutos < 60) {
            return duracaoMinutos + " min";
        }
        int horas = duracaoMinutos / 60;
        int minutos = duracaoMinutos % 60;
        if (minutos == 0) {
            return horas + "h";
        }
        return horas + "h " + minutos + "min";
    }
}
