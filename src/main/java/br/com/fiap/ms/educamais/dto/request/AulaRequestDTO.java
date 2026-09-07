package br.com.fiap.ms.educamais.dto.request;

import br.com.fiap.ms.educamais.entities.TipoAula;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AulaRequestDTO {

    @NotBlank(message = "Título da aula é obrigatório")
    @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
    @Schema(example = "Contaminação cruzada e áreas segregadas")
    private String titulo;

    @NotNull(message = "Duração é obrigatória")
    @Positive(message = "Duração deve ser maior que zero")
    @Schema(example = "18")
    private Integer duracaoMinutos;

    @NotNull(message = "Tipo da aula é obrigatório")
    @Schema(example = "VIDEO")
    private TipoAula tipo;

    @NotNull(message = "Ordem é obrigatória")
    @Positive(message = "Ordem deve ser maior que zero")
    @Schema(example = "1")
    private Integer ordem;
}
