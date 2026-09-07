package br.com.fiap.ms.educamais.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
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
public class CursoRequestDTO {

    @NotBlank(message = "Título é obrigatório")
    @Size(min = 3, max = 150, message = "Título deve ter entre 3 e 150 caracteres")
    @Schema(example = "Boas Práticas de Fabricação (BPF)")
    private String titulo;

    @NotBlank(message = "Categoria é obrigatória")
    @Size(min = 3, max = 60, message = "Categoria deve ter entre 3 e 60 caracteres")
    @Schema(example = "Regulatório")
    private String categoria;

    @NotBlank(message = "Instrutor é obrigatório")
    @Size(min = 3, max = 100, message = "Instrutor deve ter entre 3 e 100 caracteres")
    @Schema(example = "Dra. Helena Vasconcelos")
    private String instrutor;

    @NotBlank(message = "Descrição é obrigatória")
    @Size(min = 10, max = 500, message = "Descrição deve ter entre 10 e 500 caracteres")
    @Schema(example = "Requisitos de BPF aplicados à produção de medicamentos, conforme RDC 658/2022.")
    private String descricao;

    @NotNull(message = "Carga horária é obrigatória")
    @Positive(message = "Carga horária deve ser maior que zero")
    @Schema(example = "12")
    private Integer cargaHoraria;

    @NotNull(message = "Informe se o curso é obrigatório")
    @Schema(example = "true")
    private Boolean obrigatorio;

    @Schema(example = "2026-12-15")
    private LocalDate prazo;

    @NotEmpty(message = "Curso deve ter pelo menos um módulo")
    private List<@Valid ModuloRequestDTO> modulos = new ArrayList<>();
}
