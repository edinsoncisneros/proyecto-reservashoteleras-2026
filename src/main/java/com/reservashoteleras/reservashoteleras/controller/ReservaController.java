package com.reservashoteleras.reservashoteleras.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.reservashoteleras.reservashoteleras.model.Reserva;
import com.reservashoteleras.reservashoteleras.service.FileStorageService;
import com.reservashoteleras.reservashoteleras.service.ReservaService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
public class ReservaController {

    @Autowired
    private ReservaService service;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping("/reservas")
    public String listar(Model model) {

        model.addAttribute(
                "reservas",
                service.listarReservas());

        return "index";
    }

    @GetMapping("/reservas/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "reserva",
                new Reserva());

        return "formulario";
    }

    @PostMapping("/reservas/guardar")
    public String guardar(
            @Valid @ModelAttribute Reserva reserva,
            BindingResult result,
            @RequestParam(value = "archivoImagen",
                    required = false)
            MultipartFile archivoImagen,
            Model model,
            RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "formulario";
        }

        if (archivoImagen != null &&
                !archivoImagen.isEmpty()) {

            String imagenAnterior = null;

            if (reserva.getId() != null) {

                Reserva existente =
                        service.obtenerPorId(reserva.getId());

                if (existente != null) {
                    imagenAnterior =
                            existente.getImagen();
                }
            }

            String nombreImagen =
                    fileStorageService.guardar(archivoImagen);

            reserva.setImagen(nombreImagen);

            if (imagenAnterior != null) {
                fileStorageService.eliminar(imagenAnterior);
            }
        }

        service.guardarReserva(reserva);

        redirectAttributes.addFlashAttribute(
                "mensajeExito",
                "Reserva guardada correctamente");

        return "redirect:/reservas";
    }

    @GetMapping("/reservas/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "reserva",
                service.obtenerPorId(id));

        return "formulario";
    }

    @GetMapping("/reservas/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes redirectAttributes) {

        Reserva reserva =
                service.obtenerPorId(id);

        if (reserva != null &&
                reserva.getImagen() != null) {

            fileStorageService
                    .eliminar(reserva.getImagen());
        }

        service.eliminar(id);

        redirectAttributes.addFlashAttribute(
                "mensajeExito",
                "Reserva eliminada");

        return "redirect:/reservas";
    }
}