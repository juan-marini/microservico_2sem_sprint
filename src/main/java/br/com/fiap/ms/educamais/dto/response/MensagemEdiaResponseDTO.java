package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.AutorMensagem;
import br.com.fiap.ms.educamais.entities.MensagemEdia;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MensagemEdiaResponseDTO {

    private Long id;
    private String texto;
    private AutorMensagem autor;
    private String rotuloAutor;
    private String fonte;
    private Boolean possuiFonte;
    private LocalDateTime dataHora;

    public MensagemEdiaResponseDTO(MensagemEdia mensagem) {
        this.id = mensagem.getId();
        this.texto = mensagem.getTexto();
        this.autor = mensagem.getAutor();
        this.rotuloAutor = mensagem.getAutor().getRotulo();
        this.fonte = mensagem.getFonte();
        this.possuiFonte = mensagem.getPossuiFonte();
        this.dataHora = mensagem.getDataHora();
    }
}
