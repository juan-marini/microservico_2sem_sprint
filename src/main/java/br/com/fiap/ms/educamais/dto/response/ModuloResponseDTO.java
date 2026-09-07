package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Modulo;
import java.util.List;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ModuloResponseDTO {

    private Long id;
    private String titulo;
    private Integer ordem;
    private Integer totalAulas;
    private Integer duracaoMinutos;
    private Integer aulasConcluidas;
    private Boolean concluido;
    private List<AulaResponseDTO> aulas;

    public ModuloResponseDTO(Modulo modulo) {
        this(modulo, Set.of());
    }

    public ModuloResponseDTO(Modulo modulo, Set<Long> idsAulasConcluidas) {
        this.id = modulo.getId();
        this.titulo = modulo.getTitulo();
        this.ordem = modulo.getOrdem();
        this.totalAulas = modulo.getTotalAulas();
        this.duracaoMinutos = modulo.getDuracaoMinutos();
        this.aulas = modulo.getAulas().stream()
                .map(aula -> new AulaResponseDTO(aula, idsAulasConcluidas.contains(aula.getId())))
                .toList();
        this.aulasConcluidas = (int) this.aulas.stream()
                .filter(AulaResponseDTO::getConcluida)
                .count();
        this.concluido = this.totalAulas > 0 && this.aulasConcluidas.equals(this.totalAulas);
    }
}
