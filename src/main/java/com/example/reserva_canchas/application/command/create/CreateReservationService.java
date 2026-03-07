package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.domain.exception.FieldNotFoundException;
import com.example.reserva_canchas.domain.exception.ReservationConflictException;
import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.model.enums.ReservationStatus;
import com.example.reserva_canchas.domain.port.in.reservation.CreateReservationUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class CreateReservationService implements  CreateReservationUseCase {

    private final ReservationRepositoryPort reservationRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final FieldRepositoryPort fieldRepositoryPort;

    public CreateReservationService(ReservationRepositoryPort reservationRepositoryPort, UserRepositoryPort userRepositoryPort, FieldRepositoryPort fieldRepositoryPort) {
        this.reservationRepositoryPort = reservationRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.fieldRepositoryPort = fieldRepositoryPort;
    }

    @Override
    public Reservation create(Long userId,Long fieldId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        User user = userRepositoryPort.findById(userId)
                .orElseThrow(()-> new UserNotFoundException(userId));

        Field field = fieldRepositoryPort.findById(fieldId)
                .orElseThrow(()-> new FieldNotFoundException(fieldId));

        validateAvailability(fieldId, date, startTime,endTime);

        BigDecimal price = getFinalPrice(startTime, endTime, field);

        Reservation reservation = new Reservation(user,field,date,startTime,endTime,
                ReservationStatus.PENDING, price, LocalDateTime.now());

        return reservationRepositoryPort.save(reservation);
    }


    private BigDecimal getFinalPrice(LocalTime startTime, LocalTime endTime, Field field){
        long minutos = Duration.between(startTime, endTime).toMinutes();

        BigDecimal horas = BigDecimal.valueOf(minutos)
                .divide(BigDecimal.valueOf(60), 2, RoundingMode.HALF_UP);

        return field.getPrice().multiply(horas);
    }

    private void validateAvailability(Long fieldId, LocalDate date, LocalTime startTime, LocalTime endTime) {
        List<Reservation> reservasExistentes = reservationRepositoryPort.findByFieldIdAndDate(fieldId, date);

        boolean hayConflicto = reservasExistentes.stream()
                .anyMatch(reserva ->
                        startTime.isBefore(reserva.getEndTime()) && endTime.isAfter(reserva.getStartTime())
                );

        if (hayConflicto) {
            throw new ReservationConflictException("El horario ya está reservado");
        }
    }


}
