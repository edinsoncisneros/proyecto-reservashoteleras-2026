package com.reservashoteleras.reservashoteleras.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reservashoteleras.reservashoteleras.model.Habitacion;
import com.reservashoteleras.reservashoteleras.repository.HabitacionRepository;

@Service
public class HabitacionService {

    @Autowired
    private HabitacionRepository repository;

    public List<Habitacion> listar() {

        return repository.findAll();

    }

    public void guardar(Habitacion habitacion) {

        repository.save(habitacion);

    }

    public Habitacion obtener(Long id) {

        return repository.findById(id).orElse(null);

    }


    public Habitacion buscarPorId(Long id) {

        return repository.findById(id).orElse(null);

    }

    public void eliminar(Long id) {

        repository.deleteById(id);

    }

}
