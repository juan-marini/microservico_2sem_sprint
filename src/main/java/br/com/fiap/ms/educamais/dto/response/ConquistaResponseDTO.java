package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Conquista;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ConquistaResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String tipo;
    private Boolean desbloqueada;

    public ConquistaResponseDTO(Conquista conquista) {
        this.id = conquista.getId();
        this.titulo = conquista.getTitulo();
        this.descricao = conquista.getDescricao();
        this.tipo = conquista.getTipo();
        this.desbloqueada = conquista.getDesbloqueada();
    }
}
