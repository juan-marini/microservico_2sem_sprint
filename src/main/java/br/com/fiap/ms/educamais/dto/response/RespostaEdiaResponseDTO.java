package br.com.fiap.ms.educamais.dto.response;

import br.com.fiap.ms.educamais.entities.MensagemEdia;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RespostaEdiaResponseDTO {

    private MensagemEdiaResponseDTO pergunta;
    private MensagemEdiaResponseDTO resposta;

    public RespostaEdiaResponseDTO(MensagemEdia pergunta, MensagemEdia resposta) {
        this.pergunta = new MensagemEdiaResponseDTO(pergunta);
        this.resposta = new MensagemEdiaResponseDTO(resposta);
    }
}
