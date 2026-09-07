package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.MatriculaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.CursoResponseDTO;
import br.com.fiap.ms.educamais.dto.response.MatriculaResponseDTO;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import br.com.fiap.ms.educamais.service.MatriculaService;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/matriculas")
@Tag(name = "Matrículas", description = "Vínculo entre usuário e curso, com progresso e risco de evasão")
public class MatriculaController {

    @Autowired
    private MatriculaService matriculaService;

    @GetMapping
    @Operation(summary = "Lista todas as matrículas")
    public ResponseEntity<List<MatriculaResponseDTO>> findAll() {
        return ResponseEntity.ok(matriculaService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma matrícula pelo id")
    public ResponseEntity<MatriculaResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.findById(id));
    }

    @GetMapping("/{id}/curso")
    @Operation(summary = "Detalha o curso da matrícula marcando as aulas já concluídas por esse usuário")
    public ResponseEntity<CursoResponseDTO> detalharCurso(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.detalharCursoDaMatricula(id));
    }

    @GetMapping("/usuario/{usuarioId}")
    @Operation(summary = "Lista as matrículas de um usuário")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorUsuario(@PathVariable Long usuarioId) {
        return ResponseEntity.ok(matriculaService.listarPorUsuario(usuarioId));
    }

    @GetMapping("/usuario/{usuarioId}/risco/{risco}")
    @Operation(summary = "Lista as matrículas de um usuário filtrando por nível de risco")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorUsuarioERisco(@PathVariable Long usuarioId,
                                                                             @PathVariable RiscoEvasao risco) {
        return ResponseEntity.ok(matriculaService.listarPorUsuarioERisco(usuarioId, risco));
    }

    @PostMapping
    @Operation(summary = "Matricula um usuário em um curso")
    public ResponseEntity<MatriculaResponseDTO> matricular(@Valid @RequestBody MatriculaRequestDTO requestDTO) {
        MatriculaResponseDTO responseDTO = matriculaService.matricular(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PatchMapping("/{id}/aulas/{aulaId}/concluir")
    @Operation(summary = "Marca uma aula como concluída e recalcula o progresso da matrícula")
    public ResponseEntity<MatriculaResponseDTO> concluirAula(@PathVariable Long id, @PathVariable Long aulaId) {
        return ResponseEntity.ok(matriculaService.concluirAula(id, aulaId));
    }

    @PatchMapping("/{id}/aulas/{aulaId}/reabrir")
    @Operation(summary = "Desfaz a conclusão de uma aula e recalcula o progresso")
    public ResponseEntity<MatriculaResponseDTO> reabrirAula(@PathVariable Long id, @PathVariable Long aulaId) {
        return ResponseEntity.ok(matriculaService.reabrirAula(id, aulaId));
    }

    @PatchMapping("/{id}/risco/{risco}")
    @Operation(summary = "Atualiza o nível de risco de evasão da matrícula")
    public ResponseEntity<MatriculaResponseDTO> atualizarRisco(@PathVariable Long id,
                                                               @PathVariable RiscoEvasao risco) {
        return ResponseEntity.ok(matriculaService.atualizarRisco(id, risco));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui uma matrícula")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        matriculaService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
