package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.MensagemEdia;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensagemEdiaRepository extends JpaRepository<MensagemEdia, Long> {

    List<MensagemEdia> findByUsuarioIdOrderByDataHoraAsc(Long usuarioId);

    void deleteByUsuarioId(Long usuarioId);
}
