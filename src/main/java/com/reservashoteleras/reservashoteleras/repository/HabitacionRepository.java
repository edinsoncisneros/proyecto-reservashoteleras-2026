package com.reservashoteleras.reservashoteleras.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reservashoteleras.reservashoteleras.model.Habitacion;

public interface HabitacionRepository
        extends JpaRepository<Habitacion, Long> {

}