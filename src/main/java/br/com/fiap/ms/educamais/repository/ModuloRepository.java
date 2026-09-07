package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Modulo;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuloRepository extends JpaRepository<Modulo, Long> {

    List<Modulo> findByCursoIdOrderByOrdemAsc(Long cursoId);
}
