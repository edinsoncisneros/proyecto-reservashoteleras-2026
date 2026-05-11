package com.reservashoteleras.reservashoteleras.service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {

    private static final List<String> EXTENSIONES =
            Arrays.asList("jpg", "jpeg", "png", "webp");

    private static final Path UPLOADS =
            Paths.get(System.getProperty("user.dir"),
                    "src", "main", "resources",
                    "static", "uploads");

    public String guardar(MultipartFile archivo) {

        if (archivo == null || archivo.isEmpty()) {
            return null;
        }

        String nombre = archivo.getOriginalFilename();
        String extension = obtenerExtension(nombre);

        if (!EXTENSIONES.contains(extension)) {
            throw new RuntimeException("Formato inválido");
        }

        String nuevoNombre =
                UUID.randomUUID().toString() + "." + extension;

        try {

            Files.createDirectories(UPLOADS);

            Path destino = UPLOADS.resolve(nuevoNombre);

            Files.copy(
                    archivo.getInputStream(),
                    destino,
                    StandardCopyOption.REPLACE_EXISTING);

        } catch (IOException e) {
            throw new RuntimeException("Error guardando imagen");
        }

        return nuevoNombre;
    }

    public void eliminar(String nombre) {

        if (nombre == null || nombre.isBlank()) {
            return;
        }

        try {

            Path archivo = UPLOADS.resolve(nombre);

            Files.deleteIfExists(archivo);

        } catch (IOException e) {

        }
    }

    private String obtenerExtension(String nombre) {

        if (nombre == null) {
            return "";
        }

        int idx = nombre.lastIndexOf('.');

        if (idx < 0) {
            return "";
        }

        return nombre.substring(idx + 1)
                .toLowerCase(Locale.ROOT);
    }
}