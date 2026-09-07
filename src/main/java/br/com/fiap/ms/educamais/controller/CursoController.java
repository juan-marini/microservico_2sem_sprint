package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.CursoRequestDTO;
import br.com.fiap.ms.educamais.dto.response.CursoResponseDTO;
import br.com.fiap.ms.educamais.service.CursoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/cursos")
@Tag(name = "Cursos", description = "Catálogo de trilhas de treinamento com módulos e aulas")
public class CursoController {

    @Autowired
    private CursoService cursoService;

    @GetMapping
    @Operation(summary = "Lista os cursos, com filtro opcional por texto e categoria")
    public ResponseEntity<List<CursoResponseDTO>> filtrar(
            @RequestParam(required = false) String busca,
            @RequestParam(required = false) String categoria) {
        return ResponseEntity.ok(cursoService.filtrarCursos(busca, categoria));
    }

    @GetMapping("/categorias")
    @Operation(summary = "Lista as categorias distintas existentes no catálogo")
    public ResponseEntity<List<String>> listarCategorias() {
        return ResponseEntity.ok(cursoService.listarCategorias());
    }

    @GetMapping("/obrigatorios")
    @Operation(summary = "Lista apenas os treinamentos obrigatórios")
    public ResponseEntity<List<CursoResponseDTO>> listarObrigatorios() {
        return ResponseEntity.ok(cursoService.listarObrigatorios());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um curso pelo id, com módulos e aulas")
    public ResponseEntity<CursoResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(cursoService.findById(id));
    }

    @PostMapping
    @Operation(summary = "Cadastra um curso com seus módulos e aulas")
    public ResponseEntity<CursoResponseDTO> save(@Valid @RequestBody CursoRequestDTO requestDTO) {
        CursoResponseDTO responseDTO = cursoService.save(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um curso e substitui seus módulos e aulas")
    public ResponseEntity<CursoResponseDTO> update(@PathVariable Long id,
                                                   @Valid @RequestBody CursoRequestDTO requestDTO) {
        return ResponseEntity.ok(cursoService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um curso")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        cursoService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
