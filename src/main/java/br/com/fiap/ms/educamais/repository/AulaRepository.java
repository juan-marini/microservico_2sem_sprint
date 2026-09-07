package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Aula;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AulaRepository extends JpaRepository<Aula, Long> {

    List<Aula> findByModuloIdOrderByOrdemAsc(Long moduloId);
}
