package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.SalaEstudoRequestDTO;
import br.com.fiap.ms.educamais.dto.response.SalaEstudoResponseDTO;
import br.com.fiap.ms.educamais.entities.Curso;
import br.com.fiap.ms.educamais.entities.SalaEstudo;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.CursoRepository;
import br.com.fiap.ms.educamais.repository.SalaEstudoRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SalaEstudoService {

    @Autowired
    private SalaEstudoRepository salaEstudoRepository;

    @Autowired
    private CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<SalaEstudoResponseDTO> findAll() {
        return salaEstudoRepository.findAll().stream()
                .map(SalaEstudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<SalaEstudoResponseDTO> listarAoVivo() {
        return salaEstudoRepository.findByAoVivoTrue().stream()
                .map(SalaEstudoResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public SalaEstudoResponseDTO findById(Long id) {
        return new SalaEstudoResponseDTO(buscarSala(id));
    }

    @Transactional
    public SalaEstudoResponseDTO save(SalaEstudoRequestDTO requestDTO) {
        SalaEstudo sala = new SalaEstudo();
        copiarDtoParaSala(requestDTO, sala);
        sala = salaEstudoRepository.save(sala);
        return new SalaEstudoResponseDTO(sala);
    }

    @Transactional
    public SalaEstudoResponseDTO update(Long id, SalaEstudoRequestDTO requestDTO) {
        try {
            SalaEstudo sala = salaEstudoRepository.getReferenceById(id);
            copiarDtoParaSala(requestDTO, sala);
            sala = salaEstudoRepository.save(sala);
            return new SalaEstudoResponseDTO(sala);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Sala de estudo não encontrada. ID: " + id);
        }
    }

    @Transactional
    public SalaEstudoResponseDTO confirmarParticipacao(Long id) {
        SalaEstudo sala = buscarSala(id);
        if (Boolean.TRUE.equals(sala.getLotada())) {
            throw new DatabaseException("Sala lotada. Capacidade máxima de " + sala.getCapacidade() + " participantes");
        }
        sala.setParticipantes(sala.getParticipantes() + 1);
        sala = salaEstudoRepository.save(sala);
        return new SalaEstudoResponseDTO(sala);
    }

    @Transactional
    public SalaEstudoResponseDTO cancelarParticipacao(Long id) {
        SalaEstudo sala = buscarSala(id);
        if (sala.getParticipantes() == 0) {
            throw new DatabaseException("Não há participantes para remover nesta sala");
        }
        sala.setParticipantes(sala.getParticipantes() - 1);
        sala = salaEstudoRepository.save(sala);
        return new SalaEstudoResponseDTO(sala);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!salaEstudoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sala de estudo não encontrada. ID: " + id);
        }
        try {
            salaEstudoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir esta sala de estudo");
        }
    }

    private SalaEstudo buscarSala(Long id) {
        return salaEstudoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sala de estudo não encontrada. ID: " + id));
    }

    private void copiarDtoParaSala(SalaEstudoRequestDTO requestDTO, SalaEstudo sala) {
        if (requestDTO.getParticipantes() > requestDTO.getCapacidade()) {
            throw new DatabaseException("Número de participantes não pode ser maior que a capacidade da sala");
        }
        sala.setTitulo(requestDTO.getTitulo());
        sala.setMediador(requestDTO.getMediador());
        sala.setHorario(requestDTO.getHorario());
        sala.setDescricao(requestDTO.getDescricao());
        sala.setParticipantes(requestDTO.getParticipantes());
        sala.setCapacidade(requestDTO.getCapacidade());
        sala.setAoVivo(requestDTO.getAoVivo());

        if (requestDTO.getCursoRelacionadoId() == null) {
            sala.setCursoRelacionado(null);
            return;
        }
        Curso curso = cursoRepository.findById(requestDTO.getCursoRelacionadoId())
                .orElseThrow(() -> new DatabaseException(
                        "Não foi possível salvar a sala. Curso inexistente. ID: " + requestDTO.getCursoRelacionadoId()));
        sala.setCursoRelacionado(curso);
    }
}
