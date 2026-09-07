package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Aula;
import br.com.fiap.ms.educamais.entities.TipoAula;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AulaResponseDTO {

    private Long id;
    private String titulo;
    private Integer ordem;
    private Integer duracaoMinutos;
    private String duracaoFormatada;
    private TipoAula tipo;
    private String rotuloTipo;
    private Boolean concluida;

    public AulaResponseDTO(Aula aula) {
        this(aula, false);
    }

    public AulaResponseDTO(Aula aula, Boolean concluida) {
        this.id = aula.getId();
        this.titulo = aula.getTitulo();
        this.ordem = aula.getOrdem();
        this.duracaoMinutos = aula.getDuracaoMinutos();
        this.duracaoFormatada = aula.getDuracaoFormatada();
        this.tipo = aula.getTipo();
        this.rotuloTipo = aula.getTipo().getRotulo();
        this.concluida = concluida;
    }
}
