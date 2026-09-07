package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Curso;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CursoRepository extends JpaRepository<Curso, Long> {

    List<Curso> findByTituloContainingIgnoreCase(String titulo);

    List<Curso> findByCategoriaIgnoreCase(String categoria);

    List<Curso> findByTituloContainingIgnoreCaseAndCategoriaIgnoreCase(String titulo, String categoria);

    List<Curso> findByObrigatorioTrue();
}
