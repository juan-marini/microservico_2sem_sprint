package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Aula;
import br.com.fiap.ms.educamais.entities.Matricula;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MatriculaResponseDTO {

    private Long id;
    private UsuarioResumoDTO usuario;
    private CursoResumoDTO curso;
    private LocalDate dataMatricula;
    private LocalDate dataUltimoAcesso;
    private Double progresso;
    private Integer percentual;
    private Boolean concluida;
    private Boolean naoIniciada;
    private RiscoEvasao risco;
    private String rotuloRisco;
    private Integer totalAulas;
    private Integer aulasConcluidas;
    private AulaResponseDTO proximaAula;

    public MatriculaResponseDTO(Matricula matricula) {
        this.id = matricula.getId();
        this.usuario = new UsuarioResumoDTO(matricula.getUsuario());
        this.curso = new CursoResumoDTO(matricula.getCurso());
        this.dataMatricula = matricula.getDataMatricula();
        this.dataUltimoAcesso = matricula.getDataUltimoAcesso();
        this.progresso = matricula.getProgresso();
        this.percentual = matricula.getPercentual();
        this.concluida = matricula.getConcluida();
        this.naoIniciada = matricula.getNaoIniciada();
        this.risco = matricula.getRisco();
        this.rotuloRisco = matricula.getRisco().getRotulo();
        this.totalAulas = matricula.getTotalAulas();
        this.aulasConcluidas = matricula.getQuantidadeAulasConcluidas();
        Aula proxima = matricula.getProximaAula();
        this.proximaAula = proxima == null ? null : new AulaResponseDTO(proxima, false);
    }
}
