package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.AlertaEvasaoRequestDTO;
import br.com.fiap.ms.educamais.dto.response.AlertaEvasaoResponseDTO;
import br.com.fiap.ms.educamais.entities.AlertaEvasao;
import br.com.fiap.ms.educamais.entities.Curso;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.AlertaEvasaoRepository;
import br.com.fiap.ms.educamais.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertaEvasaoService {

    @Autowired
    private AlertaEvasaoRepository alertaEvasaoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<AlertaEvasaoResponseDTO> findAll() {
        return alertaEvasaoRepository.findAll().stream()
                .map(AlertaEvasaoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<AlertaEvasaoResponseDTO> listarPorUsuario(Long usuarioId) {
        usuarioService.buscarUsuario(usuarioId);
        return alertaEvasaoRepository.findByUsuarioId(usuarioId).stream()
                .map(AlertaEvasaoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public AlertaEvasaoResponseDTO findById(Long id) {
        return new AlertaEvasaoResponseDTO(buscarAlerta(id));
    }

    @Transactional
    public AlertaEvasaoResponseDTO save(AlertaEvasaoRequestDTO requestDTO) {
        AlertaEvasao alerta = new AlertaEvasao();
        copiarDtoParaAlerta(requestDTO, alerta);
        alerta = alertaEvasaoRepository.save(alerta);
        return new AlertaEvasaoResponseDTO(alerta);
    }

    @Transactional
    public AlertaEvasaoResponseDTO update(Long id, AlertaEvasaoRequestDTO requestDTO) {
        try {
            AlertaEvasao alerta = alertaEvasaoRepository.getReferenceById(id);
            copiarDtoParaAlerta(requestDTO, alerta);
            alerta = alertaEvasaoRepository.save(alerta);
            return new AlertaEvasaoResponseDTO(alerta);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Alerta não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!alertaEvasaoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Alerta não encontrado. ID: " + id);
        }
        try {
            alertaEvasaoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir este alerta");
        }
    }

    private AlertaEvasao buscarAlerta(Long id) {
        return alertaEvasaoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Alerta não encontrado. ID: " + id));
    }

    private void copiarDtoParaAlerta(AlertaEvasaoRequestDTO requestDTO, AlertaEvasao alerta) {
        alerta.setTitulo(requestDTO.getTitulo());
        alerta.setDescricao(requestDTO.getDescricao());
        alerta.setAcaoSugerida(requestDTO.getAcaoSugerida());
        alerta.setNivel(requestDTO.getNivel());
        alerta.setProbabilidade(requestDTO.getProbabilidade());
        alerta.setUsuario(usuarioService.buscarUsuario(requestDTO.getUsuarioId()));

        if (requestDTO.getCursoRelacionadoId() == null) {
            alerta.setCursoRelacionado(null);
            return;
        }
        Curso curso = cursoRepository.findById(requestDTO.getCursoRelacionadoId())
                .orElseThrow(() -> new DatabaseException(
                        "Não foi possível salvar o alerta. Curso inexistente. ID: " + requestDTO.getCursoRelacionadoId()));
        alerta.setCursoRelacionado(curso);
    }
}
