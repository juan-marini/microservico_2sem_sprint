package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 100, message = "Nome deve ter entre 3 e 100 caracteres")
    @Schema(example = "Marina Ribeiro Costa")
    private String nome;

    @NotBlank(message = "Cargo é obrigatório")
    @Size(min = 3, max = 100, message = "Cargo deve ter entre 3 e 100 caracteres")
    @Schema(example = "Analista de Qualidade Pleno")
    private String cargo;

    @NotBlank(message = "Área é obrigatória")
    @Size(min = 2, max = 100, message = "Área deve ter entre 2 e 100 caracteres")
    @Schema(example = "Garantia da Qualidade")
    private String area;

    @NotBlank(message = "Matrícula é obrigatória")
    @Size(min = 4, max = 20, message = "Matrícula deve ter entre 4 e 20 caracteres")
    @Schema(example = "EF204815")
    private String matricula;
}
