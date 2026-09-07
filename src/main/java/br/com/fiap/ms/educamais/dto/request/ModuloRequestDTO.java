package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ModuloRequestDTO {

    @NotBlank(message = "Título do módulo é obrigatório")
    @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
    @Schema(example = "Fundamentos das Boas Práticas")
    private String titulo;

    @NotNull(message = "Ordem é obrigatória")
    @Positive(message = "Ordem deve ser maior que zero")
    @Schema(example = "1")
    private Integer ordem;

    @NotEmpty(message = "Módulo deve ter pelo menos uma aula")
    private List<@Valid AulaRequestDTO> aulas = new ArrayList<>();
}
