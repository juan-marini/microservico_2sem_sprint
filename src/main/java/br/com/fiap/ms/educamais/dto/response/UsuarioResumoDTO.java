package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioResumoDTO {

    private Long id;
    private String nome;
    private String iniciais;
    private String cargo;

    public UsuarioResumoDTO(Usuario usuario) {
        this.id = usuario.getId();
        this.nome = usuario.getNome();
        this.iniciais = usuario.getIniciais();
        this.cargo = usuario.getCargo();
    }
}
