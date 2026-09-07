package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.AulaConcluida;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaConcluidaRepository extends JpaRepository<AulaConcluida, Long> {

    List<AulaConcluida> findByMatriculaId(Long matriculaId);

    Optional<AulaConcluida> findByMatriculaIdAndAulaId(Long matriculaId, Long aulaId);

    boolean existsByMatriculaIdAndAulaId(Long matriculaId, Long aulaId);
}
