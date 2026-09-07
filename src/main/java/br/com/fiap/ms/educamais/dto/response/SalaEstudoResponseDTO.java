package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.SalaEstudo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SalaEstudoResponseDTO {

    private Long id;
    private String titulo;
    private String mediador;
    private String horario;
    private String descricao;
    private Integer participantes;
    private Integer capacidade;
    private Integer vagasRestantes;
    private Double ocupacao;
    private Boolean lotada;
    private Boolean aoVivo;
    private CursoResumoDTO cursoRelacionado;

    public SalaEstudoResponseDTO(SalaEstudo sala) {
        this.id = sala.getId();
        this.titulo = sala.getTitulo();
        this.mediador = sala.getMediador();
        this.horario = sala.getHorario();
        this.descricao = sala.getDescricao();
        this.participantes = sala.getParticipantes();
        this.capacidade = sala.getCapacidade();
        this.vagasRestantes = sala.getVagasRestantes();
        this.ocupacao = sala.getOcupacao();
        this.lotada = sala.getLotada();
        this.aoVivo = sala.getAoVivo();
        this.cursoRelacionado = sala.getCursoRelacionado() == null
                ? null
                : new CursoResumoDTO(sala.getCursoRelacionado());
    }
}
