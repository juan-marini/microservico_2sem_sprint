package br.com.fiap.ms.educamais.controller;

import br.com.fiap.ms.educamais.dto.request.UsuarioRequestDTO;
import br.com.fiap.ms.educamais.dto.response.UsuarioResponseDTO;
import br.com.fiap.ms.educamais.service.UsuarioService;
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
@RequestMapping("/api/v1/usuarios")
@Tag(name = "Usuários", description = "Cadastro e consulta dos colaboradores da plataforma")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    @Operation(summary = "Lista todos os usuários")
    public ResponseEntity<List<UsuarioResponseDTO>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um usuário pelo id")
    public ResponseEntity<UsuarioResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @GetMapping("/matricula/{matricula}")
    @Operation(summary = "Busca um usuário pela matrícula corporativa")
    public ResponseEntity<UsuarioResponseDTO> findByMatricula(@PathVariable String matricula) {
        return ResponseEntity.ok(usuarioService.findByMatricula(matricula));
    }

    @PostMapping
    @Operation(summary = "Cadastra um novo usuário")
    public ResponseEntity<UsuarioResponseDTO> save(@Valid @RequestBody UsuarioRequestDTO requestDTO) {
        UsuarioResponseDTO responseDTO = usuarioService.save(requestDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        return ResponseEntity.created(uri).body(responseDTO);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza os dados cadastrais de um usuário")
    public ResponseEntity<UsuarioResponseDTO> update(@PathVariable Long id,
                                                     @Valid @RequestBody UsuarioRequestDTO requestDTO) {
        return ResponseEntity.ok(usuarioService.update(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Exclui um usuário")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
