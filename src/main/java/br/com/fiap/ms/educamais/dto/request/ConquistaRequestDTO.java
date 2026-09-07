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
public class ConquistaRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 100, message = "Título deve ter entre 3 e 100 caracteres")
    @Schema(example = "Sequência de 7 dias")
    private String titulo;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 5, max = 300, message = "Descrição deve ter entre 5 e 300 caracteres")
    @Schema(example = "Estudou por sete dias seguidos sem quebrar a ofensiva.")
    private String descricao;

    @NotBlank(message = "Tipo é obrigatório")
    @Size(min = 3, max = 60, message = "Tipo deve ter entre 3 e 60 caracteres")
    @Schema(example = "Ofensiva")
    private String tipo;

    @NotNull(message = "Informe se a conquista está desbloqueada")
    @Schema(example = "true")
    private Boolean desbloqueada;

    @NotNull(message = "Usuário é obrigatório")
    @Schema(example = "1")
    private Long usuarioId;
}
