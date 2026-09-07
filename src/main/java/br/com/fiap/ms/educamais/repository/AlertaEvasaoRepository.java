package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.AlertaEvasao;
import br.com.fiap.ms.educamais.entities.RiscoEvasao;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlertaEvasaoRepository extends JpaRepository<AlertaEvasao, Long> {

    List<AlertaEvasao> findByUsuarioId(Long usuarioId);

    List<AlertaEvasao> findByNivel(RiscoEvasao nivel);
}
