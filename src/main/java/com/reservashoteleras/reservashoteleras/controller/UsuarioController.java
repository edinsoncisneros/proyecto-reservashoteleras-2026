package com.reservashoteleras.reservashoteleras.controller;
import com.reservashoteleras.reservashoteleras.model.Usuario;
import com.reservashoteleras.reservashoteleras.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/login")
    public String login() {

        return "login";
    }

    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {

        model.addAttribute(
                "usuario",
                new Usuario());

        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(
            @Valid @ModelAttribute Usuario usuario,
            BindingResult result,
            Model model) {

        if (result.hasErrors()) {

            return "registro";
        }

        Usuario existente =
                usuarioService.buscarPorCorreo(
                        usuario.getCorreo());

        if (existente != null) {

            model.addAttribute(
                    "error",
                    "El correo ya está registrado");

            return "registro";
        }

        usuarioService.guardarUsuario(usuario);

        return "redirect:/login";
    }

    @PostMapping("/login")
    public String iniciarSesion(
            @RequestParam String correo,
            @RequestParam String password,
            Model model,
            HttpSession session) {

        Usuario usuario =
                usuarioService.buscarPorCorreo(correo);

        if (usuario != null) {


            if (usuario.getPassword()
                    .equals(password)) {

                session.setAttribute(
                        "usuarioLogueado",
                        usuario);
                return "redirect:/reservas";
            }
        }

        model.addAttribute(
                "error",
                "Correo o contraseña incorrectos");

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/login";
    }

}