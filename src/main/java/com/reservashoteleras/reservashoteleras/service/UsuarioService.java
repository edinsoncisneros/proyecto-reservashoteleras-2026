package com.reservashoteleras.reservashoteleras.service;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reservashoteleras.reservashoteleras.model.Usuario;
import com.reservashoteleras.reservashoteleras.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public void guardarUsuario(Usuario usuario) {

        repository.save(usuario);

    }

    public Usuario buscarPorCorreo(String correo) {

        Optional<Usuario> optional =
                repository.findByCorreo(correo);

        if (optional.isPresent()) {

            return optional.get();

        }
        return null;
    }

}