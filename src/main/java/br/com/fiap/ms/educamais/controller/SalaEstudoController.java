package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.SalaEstudoRequestDTO;
import br.com.fiap.ms.educamais.dto.response.SalaEstudoResponseDTO;
import br.com.fiap.ms.educamais.service.SalaEstudoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/salas-estudo")
@Tag(name = "Salas de estudo", description = "Encontros síncronos mediados, com controle de lotação")
public class SalaEstudoController {

    @Autowired
    private SalaEstudoService salaEstudoService;

    @GetMapping
    @Operation(summary = "Lista todas as salas de estudo")
    public ResponseEntity<List<SalaEstudoResponseDTO>> findAll() {
        return ResponseEntity.ok(salaEstudoService.findAll());
    }

    @GetMapping("/ao-vivo")
    @Operation(summary = "Lista apenas as salas que estão ao vivo")
    public ResponseEntity<List<SalaEstudoResponseDTO>> listarAoVivo() {
        return ResponseEntity.ok(salaEstudoService.listarAoVivo());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma sala pelo id")
    public ResponseEntity<SalaEstudoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(salaEstudoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma sala de estudo")
    public ResponseEntity<SalaEstudoResponseDTO> save(@Valid @RequestBody SalaEstudoRequestDTO requestDTO) {
        SalaEstudoResponseDTO responseDTO = salaEstudoService.save(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados de uma sala")
    public ResponseEntity<SalaEstudoResponseDTO> update(@PathVariable Long id,
                                                        @Valid @RequestBody SalaEstudoRequestDTO requestDTO) {
        return ResponseEntity.ok(salaEstudoService.update(id, requestDTO));
    }

    @PatchMapping("/{id}/confirmar-participacao")
    @Operation(summary = "Confirma participação na sala, recusando quando estiver lotada")
    public ResponseEntity<SalaEstudoResponseDTO> confirmarParticipacao(@PathVariable Long id) {
        return ResponseEntity.ok(salaEstudoService.confirmarParticipacao(id));
    }

    @PatchMapping("/{id}/cancelar-participacao")
    @Operation(summary = "Cancela uma participação e libera a vaga")
    public ResponseEntity<SalaEstudoResponseDTO> cancelarParticipacao(@PathVariable Long id) {
        return ResponseEntity.ok(salaEstudoService.cancelarParticipacao(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma sala de estudo")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        salaEstudoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
