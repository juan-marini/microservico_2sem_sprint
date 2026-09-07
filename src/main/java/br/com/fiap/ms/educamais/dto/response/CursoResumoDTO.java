package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Curso;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CursoResumoDTO {

    private Long id;
    private String titulo;
    private String categoria;
    private Boolean obrigatorio;

    public CursoResumoDTO(Curso curso) {
        this.id = curso.getId();
        this.titulo = curso.getTitulo();
        this.categoria = curso.getCategoria();
        this.obrigatorio = curso.getObrigatorio();
    }
}
