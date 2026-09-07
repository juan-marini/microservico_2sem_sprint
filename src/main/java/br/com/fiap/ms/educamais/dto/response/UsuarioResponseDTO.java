package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResponseDTO {

    private Long id;
    private String nome;
    private String primeiroNome;
    private String iniciais;
    private String cargo;
    private String area;
    private String matricula;
    private Integer nivel;
    private Integer xp;
    private Integer xpProximoNivel;
    private Double progressoNivel;
    private Integer ofensivaDias;
    private Integer cursosConcluidos;
    private Integer horasEstudo;

    public UsuarioResponseDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.primeiroNome = usuario.getPrimeiroNome();
        this.iniciais = usuario.getIniciais();
        this.cargo = usuario.getCargo();
        this.area = usuario.getArea();
        this.matricula = usuario.getMatricula();
        this.nivel = usuario.getNivel();
        this.xp = usuario.getXp();
        this.xpProximoNivel = usuario.getXpProximoNivel();
        this.progressoNivel = usuario.getProgressoNivel();
        this.ofensivaDias = usuario.getOfensivaDias();
        this.cursosConcluidos = usuario.getCursosConcluidos();
        this.horasEstudo = usuario.getHorasEstudo();
    }
}
