package br.com.senac.api.repositorios;
import br.com.senac.api.entidades.Usuarios;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UsuarioRepositorio extends JpaRepository<Usuarios,Long>{Optional<Usuarios>
findByEmail(String email);
    boolean existsByEmail(String email);
}
