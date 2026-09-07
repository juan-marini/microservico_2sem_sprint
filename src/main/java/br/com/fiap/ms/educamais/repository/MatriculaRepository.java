package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Matricula;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    List<Matricula> findByUsuarioId(Long usuarioId);

    Optional<Matricula> findByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);

    boolean existsByUsuarioIdAndCursoId(Long usuarioId, Long cursoId);

    List<Matricula> findByUsuarioIdAndRisco(Long usuarioId, RiscoEvasao risco);

    List<Matricula> findByCursoId(Long cursoId);
}
