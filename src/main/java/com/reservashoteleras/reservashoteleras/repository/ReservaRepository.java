package com.reservashoteleras.reservashoteleras.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.reservashoteleras.reservashoteleras.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long> {

}