package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.MatriculaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.CursoResponseDTO;
import br.com.fiap.ms.educamais.dto.response.MatriculaResponseDTO;
import br.com.fiap.ms.educamais.entities.Aula;
import br.com.fiap.ms.educamais.entities.AulaConcluida;
import br.com.fiap.ms.educamais.entities.Curso;
import br.com.fiap.ms.educamais.entities.Matricula;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import br.com.fiap.ms.educamais.entities.Usuario;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.AulaRepository;
import br.com.fiap.ms.educamais.repository.MatriculaRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MatriculaService {

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AulaRepository aulaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private CursoService cursoService;

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> findAll() {
        return matriculaRepository.findAll().stream()
                .map(MatriculaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorUsuario(Long usuarioId) {
        usuarioService.buscarUsuario(usuarioId);
        return matriculaRepository.findByUsuarioId(usuarioId).stream()
                .map(MatriculaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<MatriculaResponseDTO> listarPorUsuarioERisco(Long usuarioId, RiscoEvasao risco) {
        usuarioService.buscarUsuario(usuarioId);
        return matriculaRepository.findByUsuarioIdAndRisco(usuarioId, risco).stream()
                .map(MatriculaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public MatriculaResponseDTO findById(Long id) {
        return new MatriculaResponseDTO(buscarMatricula(id));
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO detalharCursoDaMatricula(Long id) {
        Matricula matricula = buscarMatricula(id);
        Set<Long> idsConcluidas = matricula.getAulasConcluidas().stream()
                .map(item -> item.getAula().getId())
                .collect(Collectors.toSet());
        return new CursoResponseDTO(matricula.getCurso(), idsConcluidas);
    }

    @Transactional
    public MatriculaResponseDTO matricular(MatriculaRequestDTO requestDTO) {
        Usuario usuario = usuarioService.buscarUsuario(requestDTO.getUsuarioId());
        Curso curso = cursoService.buscarCurso(requestDTO.getCursoId());

        if (matriculaRepository.existsByUsuarioIdAndCursoId(usuario.getId(), curso.getId())) {
            throw new DatabaseException("Usuário já está matriculado no curso " + curso.getTitulo());
        }

        Matricula matricula = new Matricula();
        matricula.setUsuario(usuario);
        matricula.setCurso(curso);
        matricula.setDataMatricula(LocalDate.now());
        matricula.setDataUltimoAcesso(LocalDate.now());
        matricula.setProgresso(0.0);
        matricula.setRisco(RiscoEvasao.BAIXO);
        matricula = matriculaRepository.save(matricula);
        return new MatriculaResponseDTO(matricula);
    }

    @Transactional
    public MatriculaResponseDTO concluirAula(Long matriculaId, Long aulaId) {
        Matricula matricula = buscarMatricula(matriculaId);
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new ResourceNotFoundException("Aula não encontrada. ID: " + aulaId));

        if (!aula.getModulo().getCurso().getId().equals(matricula.getCurso().getId())) {
            throw new DatabaseException("A aula informada não pertence ao curso desta matrícula");
        }
        if (matricula.possuiAulaConcluida(aulaId)) {
            throw new DatabaseException("Esta aula já foi concluída nesta matrícula");
        }

        AulaConcluida aulaConcluida = new AulaConcluida();
        aulaConcluida.setMatricula(matricula);
        aulaConcluida.setAula(aula);
        aulaConcluida.setDataConclusao(LocalDateTime.now());
        matricula.getAulasConcluidas().add(aulaConcluida);

        matricula.calcularProgresso();
        matricula.setDataUltimoAcesso(LocalDate.now());
        matricula = matriculaRepository.save(matricula);
        return new MatriculaResponseDTO(matricula);
    }

    @Transactional
    public MatriculaResponseDTO reabrirAula(Long matriculaId, Long aulaId) {
        Matricula matricula = buscarMatricula(matriculaId);
        boolean removida = matricula.getAulasConcluidas()
                .removeIf(item -> item.getAula().getId().equals(aulaId));

        if (!removida) {
            throw new ResourceNotFoundException("Esta aula não consta como concluída na matrícula " + matriculaId);
        }

        matricula.calcularProgresso();
        matricula.setDataUltimoAcesso(LocalDate.now());
        matricula = matriculaRepository.save(matricula);
        return new MatriculaResponseDTO(matricula);
    }

    @Transactional
    public MatriculaResponseDTO atualizarRisco(Long id, RiscoEvasao risco) {
        Matricula matricula = buscarMatricula(id);
        matricula.setRisco(risco);
        matricula = matriculaRepository.save(matricula);
        return new MatriculaResponseDTO(matricula);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!matriculaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Matrícula não encontrada. ID: " + id);
        }
        try {
            matriculaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir esta matrícula");
        }
    }

    private Matricula buscarMatricula(Long id) {
        return matriculaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Matrícula não encontrada. ID: " + id));
    }
}
