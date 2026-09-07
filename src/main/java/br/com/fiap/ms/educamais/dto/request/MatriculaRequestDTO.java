package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaRequestDTO {

    @NotNull(message = "Usuário é obrigatório")
    @Schema(example = "1")
    private Long usuarioId;

    @NotNull(message = "Curso é obrigatório")
    @Schema(example = "1")
    private Long cursoId;
}
