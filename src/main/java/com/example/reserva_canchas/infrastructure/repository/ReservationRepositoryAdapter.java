package com.example.reserva_canchas.infrastructure.repository;

import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import com.example.reserva_canchas.infrastructure.entity.ReservationEntity;
import com.example.reserva_canchas.infrastructure.mapper.ReservationMapper;
import com.example.reserva_canchas.infrastructure.repository.interfaces.IReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ReservationRepositoryAdapter implements ReservationRepositoryPort {

    private final IReservationRepository reservationRepository;


    @Override
    public Reservation save(Reservation reservation) {
        ReservationEntity saved = reservationRepository.save(ReservationMapper.toEntity(reservation));

        return ReservationMapper.toDomain(saved);
    }

    @Override
    public Optional<Reservation> findById(Long id) {
        return reservationRepository.findById(id).map(ReservationMapper::toDomain);
    }

    @Override
    public List<Reservation> findAll() {
        return reservationRepository.findAll().stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return reservationRepository.findByUserId(userId).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByDate(LocalDate date) {
        return reservationRepository.findByDate(date).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> findByFieldIdAndDate(Long fieldId, LocalDate date) {
        return reservationRepository.findByField_IdAndDate(fieldId,date).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }

    @Override
    public List<Reservation> getReservationsByLocationId(Long locationId) {
        return reservationRepository.findAllByLocationId(locationId).stream()
                .map(ReservationMapper::toDomain)
                .toList();
    }
}
