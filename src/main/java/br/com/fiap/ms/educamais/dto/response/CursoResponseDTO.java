package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Curso;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursoResponseDTO {

    private Long id;
    private String titulo;
    private String categoria;
    private String instrutor;
    private String descricao;
    private Integer cargaHoraria;
    private Boolean obrigatorio;
    private LocalDate prazo;
    private Integer totalModulos;
    private Integer totalAulas;
    private List<ModuloResponseDTO> modulos;

    public CursoResponseDTO(Curso curso) {
        this(curso, Set.of());
    }

    public CursoResponseDTO(Curso curso, Set<Long> idsAulasConcluidas) {
        this.id = curso.getId();
        this.titulo = curso.getTitulo();
        this.categoria = curso.getCategoria();
        this.instrutor = curso.getInstrutor();
        this.descricao = curso.getDescricao();
        this.cargaHoraria = curso.getCargaHoraria();
        this.obrigatorio = curso.getObrigatorio();
        this.prazo = curso.getPrazo();
        this.totalModulos = curso.getTotalModulos();
        this.totalAulas = curso.getTotalAulas();
        this.modulos = curso.getModulos().stream()
                .map(modulo -> new ModuloResponseDTO(modulo, idsAulasConcluidas))
                .toList();
    }
}
