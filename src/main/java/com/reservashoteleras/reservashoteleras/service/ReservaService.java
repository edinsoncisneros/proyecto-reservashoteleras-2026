package com.reservashoteleras.reservashoteleras.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.reservashoteleras.reservashoteleras.model.Reserva;
import com.reservashoteleras.reservashoteleras.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository repository;

    public List<Reserva> listarReservas() {
        return repository.findAll();
    }

    public void guardarReserva(Reserva reserva) {
        repository.save(reserva);
    }

    public Reserva obtenerPorId(Long id) {
        return repository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        repository.deleteById(id);
    }
}