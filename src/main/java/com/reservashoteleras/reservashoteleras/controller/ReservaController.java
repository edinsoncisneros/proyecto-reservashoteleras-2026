package com.reservashoteleras.reservashoteleras.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.reservashoteleras.reservashoteleras.model.Reserva;
import com.reservashoteleras.reservashoteleras.service.ReservaService;

@Controller
public class ReservaController {

    @Autowired
    private ReservaService service;

    @GetMapping("/reservas")
    public String listar(Model model) {
        model.addAttribute("reservas", service.listarReservas());
        return "index";
    }

    @GetMapping("/reservas/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("reserva", new Reserva());
        return "formulario";
    }

    @PostMapping("/reservas/guardar")
    public String guardar(@ModelAttribute Reserva reserva) {
        service.guardarReserva(reserva);
        return "redirect:/reservas";
    }

    @GetMapping("/reservas/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {
        model.addAttribute("reserva", service.obtenerPorId(id));
        return "formulario";
    }

    @GetMapping("/reservas/eliminar/{id}")
    public String eliminar(@PathVariable Long id) {
        service.eliminar(id);
        return "redirect:/reservas";
    }
}