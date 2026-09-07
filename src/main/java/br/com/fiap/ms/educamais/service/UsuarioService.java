package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.UsuarioRequestDTO;
import br.com.fiap.ms.educamais.dto.response.UsuarioResponseDTO;
import br.com.fiap.ms.educamais.entities.Usuario;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional(readOnly = true)
    public List<UsuarioResponseDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(UsuarioResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findById(Long id) {
        return new UsuarioResponseDTO(buscarUsuario(id));
    }

    @Transactional(readOnly = true)
    public UsuarioResponseDTO findByMatricula(String matricula) {
        Usuario usuario = usuarioRepository.findByMatricula(matricula)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. Matrícula: " + matricula));
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO save(UsuarioRequestDTO requestDTO) {
        if (usuarioRepository.existsByMatricula(requestDTO.getMatricula())) {
            throw new DatabaseException("Já existe usuário com a matrícula " + requestDTO.getMatricula());
        }
        Usuario usuario = new Usuario();
        copiarDtoParaUsuario(requestDTO, usuario);
        usuario.setNivel(1);
        usuario.setXp(0);
        usuario.setXpProximoNivel(500);
        usuario.setOfensivaDias(0);
        usuario.setCursosConcluidos(0);
        usuario.setHorasEstudo(0);
        usuario = usuarioRepository.save(usuario);
        return new UsuarioResponseDTO(usuario);
    }

    @Transactional
    public UsuarioResponseDTO update(Long id, UsuarioRequestDTO requestDTO) {
        try {
            Usuario usuario = usuarioRepository.getReferenceById(id);
            copiarDtoParaUsuario(requestDTO, usuario);
            usuario = usuarioRepository.save(usuario);
            return new UsuarioResponseDTO(usuario);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Usuário não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuário não encontrado. ID: " + id);
        }
        try {
            usuarioRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir um usuário com matrículas, alertas ou conquistas vinculados");
        }
    }

    @Transactional(readOnly = true)
    public Usuario buscarUsuario(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado. ID: " + id));
    }

    private void copiarDtoParaUsuario(UsuarioRequestDTO requestDTO, Usuario usuario) {
        usuario.setNome(requestDTO.getNome());
        usuario.setCargo(requestDTO.getCargo());
        usuario.setArea(requestDTO.getArea());
        usuario.setMatricula(requestDTO.getMatricula());
    }
}
