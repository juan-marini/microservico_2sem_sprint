package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.PerguntaEdiaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.MensagemEdiaResponseDTO;
import br.com.fiap.ms.educamais.dto.response.RespostaEdiaResponseDTO;
import br.com.fiap.ms.educamais.service.EdiaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/edia")
@Tag(name = "EdIA", description = "Tutora com busca no material oficial e regra de zero alucinação")
public class EdiaController {

    @Autowired
    private EdiaService ediaService;

    @PostMapping("/perguntas")
    @Operation(summary = "Envia uma pergunta à EdIA e recebe a resposta com a fonte no material")
    public ResponseEntity<RespostaEdiaResponseDTO> responder(@Valid @RequestBody PerguntaEdiaRequestDTO requestDTO) {
        return ResponseEntity.ok(ediaService.responder(requestDTO));
    }

    @GetMapping("/historico/usuario/{usuarioId}")
    @Operation(summary = "Lista o histórico de conversa de um usuário em ordem cronológica")
    public ResponseEntity<List<MensagemEdiaResponseDTO>> listarHistorico(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(ediaService.listarHistorico(usuarioId));
    }

    @DeleteMapping("/historico/usuario/{usuarioId}")
    @Operation(summary = "Apaga o histórico de conversa de um usuário")
    public ResponseEntity<Void> limparHistorico(@PathVariable Long usuarioId) {
        ediaService.limparHistorico(usuarioId);
        return ResponseEntity.noContent().build();
    }
}
