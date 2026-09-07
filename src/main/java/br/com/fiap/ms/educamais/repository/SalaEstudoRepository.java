package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.SalaEstudo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SalaEstudoRepository extends JpaRepository<SalaEstudo, Long> {

    List<SalaEstudo> findByAoVivoTrue();

    List<SalaEstudo> findByCursoRelacionadoId(Long cursoId);
}
