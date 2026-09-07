package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.AulaRequestDTO;
import br.com.fiap.ms.educamais.dto.request.CursoRequestDTO;
import br.com.fiap.ms.educamais.dto.request.ModuloRequestDTO;
import br.com.fiap.ms.educamais.dto.response.CursoResponseDTO;
import br.com.fiap.ms.educamais.entities.Aula;
import br.com.fiap.ms.educamais.entities.Curso;
import br.com.fiap.ms.educamais.entities.Modulo;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.CursoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.Comparator;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CursoService {

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> filtrarCursos(String busca, String categoria) {
        boolean temBusca = busca != null && !busca.isBlank();
        boolean temCategoria = categoria != null && !categoria.isBlank();

        List<Curso> cursos;
        if (temBusca && temCategoria) {
            cursos = cursoRepository.findByTituloContainingIgnoreCaseAndCategoriaIgnoreCase(busca.trim(), categoria.trim());
        } else if (temBusca) {
            cursos = cursoRepository.findByTituloContainingIgnoreCase(busca.trim());
        } else if (temCategoria) {
            cursos = cursoRepository.findByCategoriaIgnoreCase(categoria.trim());
        } else {
            cursos = cursoRepository.findAll();
        }

        return cursos.stream()
                .map(CursoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> listarCategorias() {
        return cursoRepository.findAll().stream()
                .map(Curso::getCategoria)
                .distinct()
                .sorted(Comparator.naturalOrder())
                .toList();
    }

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarObrigatorios() {
        return cursoRepository.findByObrigatorioTrue().stream()
                .map(CursoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CursoResponseDTO findById(Long id) {
        return new CursoResponseDTO(buscarCurso(id));
    }

    @Transactional
    public CursoResponseDTO save(CursoRequestDTO requestDTO) {
        Curso curso = new Curso();
        copiarDtoParaCurso(requestDTO, curso);
        curso = cursoRepository.save(curso);
        return new CursoResponseDTO(curso);
    }

    @Transactional
    public CursoResponseDTO update(Long id, CursoRequestDTO requestDTO) {
        try {
            Curso curso = cursoRepository.getReferenceById(id);
            curso.getModulos().clear();
            copiarDtoParaCurso(requestDTO, curso);
            curso = cursoRepository.save(curso);
            return new CursoResponseDTO(curso);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Curso não encontrado. ID: " + id);
        }
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!cursoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Curso não encontrado. ID: " + id);
        }
        try {
            cursoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir um curso com matrículas, salas ou alertas vinculados");
        }
    }

    @Transactional(readOnly = true)
    public Curso buscarCurso(Long id) {
        return cursoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Curso não encontrado. ID: " + id));
    }

    private void copiarDtoParaCurso(CursoRequestDTO requestDTO, Curso curso) {
        curso.setTitulo(requestDTO.getTitulo());
        curso.setCategoria(requestDTO.getCategoria());
        curso.setInstrutor(requestDTO.getInstrutor());
        curso.setDescricao(requestDTO.getDescricao());
        curso.setCargaHoraria(requestDTO.getCargaHoraria());
        curso.setObrigatorio(requestDTO.getObrigatorio());
        curso.setPrazo(requestDTO.getPrazo());

        for (ModuloRequestDTO moduloDTO : requestDTO.getModulos()) {
            Modulo modulo = new Modulo();
            modulo.setTitulo(moduloDTO.getTitulo());
            modulo.setOrdem(moduloDTO.getOrdem());
            modulo.setCurso(curso);

            for (AulaRequestDTO aulaDTO : moduloDTO.getAulas()) {
                Aula aula = new Aula();
                aula.setTitulo(aulaDTO.getTitulo());
                aula.setDuracaoMinutos(aulaDTO.getDuracaoMinutos());
                aula.setTipo(aulaDTO.getTipo());
                aula.setOrdem(aulaDTO.getOrdem());
                aula.setModulo(modulo);
                modulo.getAulas().add(aula);
            }

            curso.getModulos().add(modulo);
        }
    }
}
