package br.com.fiap.ms.educamais.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_usuario")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nome;

    @Column(nullable = false, length = 100)
    private String cargo;

    @Column(nullable = false, length = 100)
    private String area;

    @Column(nullable = false, unique = true, length = 20)
    private String matricula;

    @Column(nullable = false)
    private Integer nivel;

    @Column(nullable = false)
    private Integer xp;

    @Column(name = "xp_proximo_nivel", nullable = false)
    private Integer xpProximoNivel;

    @Column(name = "ofensiva_dias", nullable = false)
    private Integer ofensivaDias;

    @Column(name = "cursos_concluidos", nullable = false)
    private Integer cursosConcluidos;

    @Column(name = "horas_estudo", nullable = false)
    private Integer horasEstudo;

    public String getIniciais() {
        if (nome == null || nome.isBlank()) {
            return "";
        }
        String[] partes = nome.trim().split("\\s+");
        if (partes.length == 1) {
            return partes[0].substring(0, 1).toUpperCase();
        }
        String primeira = partes[0].substring(0, 1);
        String ultima = partes[partes.length - 1].substring(0, 1);
        return (primeira + ultima).toUpperCase();
    }

    public String getPrimeiroNome() {
        if (nome == null || nome.isBlank()) {
            return "";
        }
        return nome.trim().split("\\s+")[0];
    }

    public Double getProgressoNivel() {
        if (xpProximoNivel == null || xpProximoNivel == 0) {
            return 0.0;
        }
        double progresso = (double) xp / xpProximoNivel;
        return Math.min(progresso, 1.0);
    }
}
