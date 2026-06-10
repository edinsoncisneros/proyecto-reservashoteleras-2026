package com.reservashoteleras.reservashoteleras.controller;

import com.reservashoteleras.reservashoteleras.model.Habitacion;
import com.reservashoteleras.reservashoteleras.service.FileStorageService;
import com.reservashoteleras.reservashoteleras.service.HabitacionService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/habitaciones")
public class HabitacionController {

    @Autowired
    private HabitacionService service;

    @Autowired
    private FileStorageService fileStorageService;

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "habitaciones",
                service.listar());

        return "habitaciones";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "habitacion",
                new Habitacion());

        return "habitacion-form";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute Habitacion habitacion,
            BindingResult result,
            @RequestParam(value = "archivoImagen", required = false)
            MultipartFile archivoImagen) {

        if (result.hasErrors()) {

            return "habitacion-form";
        }

        if (archivoImagen != null &&
                !archivoImagen.isEmpty()) {

            String nombreImagen =
                    fileStorageService.guardar(
                            archivoImagen);

            habitacion.setImagen(nombreImagen);
        }

        service.guardar(habitacion);

        return "redirect:/habitaciones";
    }

    @GetMapping("/editar/{id}")
    public String editar(
            @PathVariable Long id,
            Model model) {

        Habitacion habitacion =
                service.buscarPorId(id);

        if (habitacion == null) {

            return "redirect:/habitaciones";
        }

        model.addAttribute(
                "habitacion",
                habitacion);

        return "editar_habitacion";
    }

    @PostMapping("/actualizar")
    public String actualizar(
            @ModelAttribute Habitacion habitacion,
            @RequestParam(value = "archivoImagen", required = false)
            MultipartFile archivoImagen) {

        Habitacion habitacionExistente =
                service.buscarPorId(habitacion.getId());

        if (archivoImagen == null ||
                archivoImagen.isEmpty()) {

            habitacion.setImagen(
                    habitacionExistente.getImagen());
        }

        else {

            String nombreImagen =
                    fileStorageService.guardar(
                            archivoImagen);

            habitacion.setImagen(nombreImagen);
        }

        service.guardar(habitacion);

        return "redirect:/habitaciones";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id) {

        service.eliminar(id);

        return "redirect:/habitaciones";
    }

}