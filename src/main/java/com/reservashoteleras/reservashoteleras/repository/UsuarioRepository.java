package com.reservashoteleras.reservashoteleras.repository;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.reservashoteleras.reservashoteleras.model.Usuario;
public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {

    Optional<Usuario> findByCorreo(String correo);

}
