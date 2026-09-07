package br.com.fiap.ms.educamais.repository;

import br.com.fiap.ms.educamais.entities.Usuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByMatricula(String matricula);

    boolean existsByMatricula(String matricula);
}
