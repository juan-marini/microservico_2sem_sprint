package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
public class SalaEstudoRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
    @Schema(example = "Revisão de BPF antes da auditoria")
    private String titulo;

    @NotBlank(message = "Mediador é obrigatório")
    @Size(min = 3, max = 100, message = "Mediador deve ter entre 3 e 100 caracteres")
    @Schema(example = "Dra. Helena Vasconcelos")
    private String mediador;

    @NotBlank(message = "Horário é obrigatório")
    @Size(min = 3, max = 60, message = "Horário deve ter entre 3 e 60 caracteres")
    @Schema(example = "Hoje, 19h00")
    private String horario;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Schema(example = "Encontro de 50 minutos para revisar os pontos críticos de contaminação cruzada.")
    private String descricao;

    @NotNull(message = "Número de participantes é obrigatório")
    @PositiveOrZero(message = "Participantes não pode ser negativo")
    @Schema(example = "8")
    private Integer participantes;

    @NotNull(message = "Capacidade é obrigatória")
    @Positive(message = "Capacidade deve ser maior que zero")
    @Schema(example = "12")
    private Integer capacidade;

    @NotNull(message = "Informe se a sala está ao vivo")
    @Schema(example = "false")
    private Boolean aoVivo;

    @Schema(example = "1")
    private Long cursoRelacionadoId;
}
