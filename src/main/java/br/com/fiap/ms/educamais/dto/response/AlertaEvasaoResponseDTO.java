package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.AlertaEvasao;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AlertaEvasaoResponseDTO {

    private Long id;
    private String titulo;
    private String descricao;
    private String acaoSugerida;
    private RiscoEvasao nivel;
    private String rotuloNivel;
    private Integer probabilidade;
    private UsuarioResumoDTO usuario;
    private CursoResumoDTO cursoRelacionado;

    public AlertaEvasaoResponseDTO(AlertaEvasao alerta) {
        this.id = alerta.getId();
        this.titulo = alerta.getTitulo();
        this.descricao = alerta.getDescricao();
        this.acaoSugerida = alerta.getAcaoSugerida();
        this.nivel = alerta.getNivel();
        this.rotuloNivel = alerta.getNivel().getRotulo();
        this.probabilidade = alerta.getProbabilidade();
        this.usuario = new UsuarioResumoDTO(alerta.getUsuario());
        this.cursoRelacionado = alerta.getCursoRelacionado() == null
                ? null
                : new CursoResumoDTO(alerta.getCursoRelacionado());
    }
}
