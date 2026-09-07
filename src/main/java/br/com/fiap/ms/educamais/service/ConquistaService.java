package br.com.fiap.ms.educamais.service;

import br.com.fiap.ms.educamais.dto.request.ConquistaRequestDTO;
import br.com.fiap.ms.educamais.dto.response.ConquistaResponseDTO;
import br.com.fiap.ms.educamais.entities.Conquista;
import br.com.fiap.ms.educamais.exceptions.DatabaseException;
import br.com.fiap.ms.educamais.exceptions.ResourceNotFoundException;
import br.com.fiap.ms.educamais.repository.ConquistaRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ConquistaService {

    @Autowired
    private ConquistaRepository conquistaRepository;

    @Autowired
    private UsuarioService usuarioService;

    @Transactional(readOnly = true)
    public List<ConquistaResponseDTO> findAll() {
        return conquistaRepository.findAll().stream()
                .map(ConquistaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<ConquistaResponseDTO> listarPorUsuario(Long usuarioId) {
        usuarioService.buscarUsuario(usuarioId);
        return conquistaRepository.findByUsuarioId(usuarioId).stream()
                .map(ConquistaResponseDTO::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConquistaResponseDTO findById(Long id) {
        return new ConquistaResponseDTO(buscarConquista(id));
    }

    @Transactional
    public ConquistaResponseDTO save(ConquistaRequestDTO requestDTO) {
        Conquista conquista = new Conquista();
        copiarDtoParaConquista(requestDTO, conquista);
        conquista = conquistaRepository.save(conquista);
        return new ConquistaResponseDTO(conquista);
    }

    @Transactional
    public ConquistaResponseDTO update(Long id, ConquistaRequestDTO requestDTO) {
        try {
            Conquista conquista = conquistaRepository.getReferenceById(id);
            copiarDtoParaConquista(requestDTO, conquista);
            conquista = conquistaRepository.save(conquista);
            return new ConquistaResponseDTO(conquista);
        } catch (EntityNotFoundException e) {
            throw new ResourceNotFoundException("Conquista não encontrada. ID: " + id);
        }
    }

    @Transactional
    public ConquistaResponseDTO desbloquear(Long id) {
        Conquista conquista = buscarConquista(id);
        if (Boolean.TRUE.equals(conquista.getDesbloqueada())) {
            throw new DatabaseException("Esta conquista já está desbloqueada");
        }
        conquista.setDesbloqueada(true);
        conquista = conquistaRepository.save(conquista);
        return new ConquistaResponseDTO(conquista);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    public void delete(Long id) {
        if (!conquistaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Conquista não encontrada. ID: " + id);
        }
        try {
            conquistaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DatabaseException("Não é possível excluir esta conquista");
        }
    }

    private Conquista buscarConquista(Long id) {
        return conquistaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conquista não encontrada. ID: " + id));
    }

    private void copiarDtoParaConquista(ConquistaRequestDTO requestDTO, Conquista conquista) {
        conquista.setTitulo(requestDTO.getTitulo());
        conquista.setDescricao(requestDTO.getDescricao());
        conquista.setTipo(requestDTO.getTipo());
        conquista.setDesbloqueada(requestDTO.getDesbloqueada());
        conquista.setUsuario(usuarioService.buscarUsuario(requestDTO.getUsuarioId()));
    }
}
