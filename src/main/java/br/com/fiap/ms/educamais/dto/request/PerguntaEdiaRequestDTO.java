package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PerguntaEdiaRequestDTO {

    @NotNull(message = "Usuário é obrigatório")
    @Schema(example = "1")
    private Long usuarioId;

    @NotBlank(message = "Pergunta é obrigatória")
    @Size(min = 3, max = 500, message = "Pergunta deve ter entre 3 e 500 caracteres")
    @Schema(example = "Como evitar contaminação cruzada na área de pesagem?")
    private String pergunta;
}
