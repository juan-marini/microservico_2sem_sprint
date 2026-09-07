package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Conquista;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConquistaRepository extends JpaRepository<Conquista, Long> {

    List<Conquista> findByUsuarioId(Long usuarioId);

    List<Conquista> findByUsuarioIdAndDesbloqueadaTrue(Long usuarioId);
}
