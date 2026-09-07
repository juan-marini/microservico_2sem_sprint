package br.com.fiap.ms.educamais.dto.request;

import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AlertaEvasaoRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
    @Schema(example = "Ritmo de estudo caiu em Farmacovigilância")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Schema(example = "Você não acessa o curso há 9 dias e o prazo de conclusão é em 12 dias.")
    private String descricao;

    @NotBlank(message = "Ação sugerida é obrigatória")
    @Size(min = 5, max = 300, message = "Ação sugerida deve ter entre 5 e 300 caracteres")
    @Schema(example = "Retome pelo Módulo 2, que leva 22 minutos e destrava as duas aulas seguintes.")
    private String acaoSugerida;

    @NotNull(message = "Nível do alerta é obrigatório")
    @Schema(example = "ALTO")
    private RiscoEvasao nivel;

    @NotNull(message = "Probabilidade é obrigatória")
    @PositiveOrZero(message = "Probabilidade não pode ser negativa")
    @Max(value = 100, message = "Probabilidade não pode passar de 100")
    @Schema(example = "78")
    private Integer probabilidade;

    @NotNull(message = "Usuário é obrigatório")
    @Schema(example = "1")
    private Long usuarioId;

    @Schema(example = "2")
    private Long cursoRelacionadoId;
}
