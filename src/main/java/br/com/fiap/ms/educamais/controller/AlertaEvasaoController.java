package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.AlertaEvasaoRequestDTO;
import br.com.fiap.ms.educamais.dto.response.AlertaEvasaoResponseDTO;
import br.com.fiap.ms.educamais.service.AlertaEvasaoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/alertas")
@Tag(name = "Alertas de evasão", description = "Alertas preditivos com probabilidade de abandono e ação sugerida")
public class AlertaEvasaoController {

    @Autowired
    private AlertaEvasaoService alertaEvasaoService;

    @GetMapping
    @Operation(summary = "Lista todos os alertas de evasão")
    public ResponseEntity<List<AlertaEvasaoResponseDTO>> findAll() {
        return ResponseEntity.ok(alertaEvasaoService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um alerta pelo id")
    public ResponseEntity<AlertaEvasaoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(alertaEvasaoService.findById(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista os alertas de um usuário")
    public ResponseEntity<List<AlertaEvasaoResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(alertaEvasaoService.listarPorUsuario(usuarioId));
    }

    @PostMapping
    @Operation(summary = "Registra um alerta de evasão")
    public ResponseEntity<AlertaEvasaoResponseDTO> save(@Valid @RequestBody AlertaEvasaoRequestDTO requestDTO) {
        AlertaEvasaoResponseDTO responseDTO = alertaEvasaoService.save(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um alerta de evasão")
    public ResponseEntity<AlertaEvasaoResponseDTO> update(@PathVariable Long id,
                                                          @Valid @RequestBody AlertaEvasaoRequestDTO requestDTO) {
        return ResponseEntity.ok(alertaEvasaoService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um alerta de evasão")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        alertaEvasaoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
