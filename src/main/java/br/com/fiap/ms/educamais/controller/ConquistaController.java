package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.ConquistaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.ConquistaResponseDTO;
import br.com.fiap.ms.educamais.service.ConquistaService;
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
@RequestMapping("/api/v1/conquistas")
@Tag(name = "Conquistas", description = "Gamificação do educando, com conquistas bloqueadas e desbloqueadas")
public class ConquistaController {

    @Autowired
    private ConquistaService conquistaService;

    @GetMapping
    @Operation(summary = "Lista todas as conquistas")
    public ResponseEntity<List<ConquistaResponseDTO>> findAll() {
        return ResponseEntity.ok(conquistaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma conquista pelo id")
    public ResponseEntity<ConquistaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(conquistaService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista as conquistas de um usuário")
    public ResponseEntity<List<ConquistaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(conquistaService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Cria uma conquista para um usuário")
    public ResponseEntity<ConquistaResponseDTO> save(@Valid @RequestBody ConquistaRequestDTO requestDTO) {
        ConquistaResponseDTO responseDTO = conquistaService.save(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma conquista")
    public ResponseEntity<ConquistaResponseDTO> update(@PathVariable Long id,
                                                       @Valid @RequestBody ConquistaRequestDTO requestDTO) {
        return ResponseEntity.ok(conquistaService.update(id, requestDTO));
    }

    @PatchMapping("/{id}/desbloquear")
    @Operation(summary = "Desbloqueia uma conquista ainda não conquistada")
    public ResponseEntity<ConquistaResponseDTO> desbloquear(@PathVariable Long id) {
        return ResponseEntity.ok(conquistaService.desbloquear(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma conquista")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        conquistaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
