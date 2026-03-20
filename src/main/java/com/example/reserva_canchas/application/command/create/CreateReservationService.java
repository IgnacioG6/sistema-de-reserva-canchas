package com.example.reserva_canchas.application.command.create;

import com.example.reserva_canchas.application.command.dto.CreateReservationCommand;
import com.example.reserva_canchas.application.command.dto.CreateReservationResult;
import com.example.reserva_canchas.domain.exception.FieldNotFoundException;
import com.example.reserva_canchas.domain.exception.ReservationConflictException;
import com.example.reserva_canchas.domain.exception.UserNotFoundException;
import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.Reservation;
import com.example.reserva_canchas.domain.model.User;
import com.example.reserva_canchas.domain.model.enums.ReservationDuration;
import com.example.reserva_canchas.domain.model.enums.ReservationStatus;
import com.example.reserva_canchas.domain.port.in.reservation.CreateReservationUseCase;
import com.example.reserva_canchas.domain.port.out.FieldRepositoryPort;
import com.example.reserva_canchas.domain.port.out.PaymentPort;
import com.example.reserva_canchas.domain.port.out.ReservationRepositoryPort;
import com.example.reserva_canchas.domain.port.out.UserRepositoryPort;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreateReservationService implements  CreateReservationUseCase {

    private final ReservationRepositoryPort reservationRepositoryPort;
    private final UserRepositoryPort userRepositoryPort;
    private final FieldRepositoryPort fieldRepositoryPort;
    private final PaymentPort paymentPort;

    @Override
    @Transactional
    public CreateReservationResult createReservation(CreateReservationCommand command) {
        User user = userRepositoryPort.findById(command.userId())
                .orElseThrow(()-> new UserNotFoundException(command.userId()));

        Field field = fieldRepositoryPort.findById(command.fieldId())
                .orElseThrow(()-> new FieldNotFoundException(command.fieldId()));

        validateAvailability(command.fieldId(), command.date(), command.startTime(), command.duration());

        BigDecimal price = command.duration().calculatePrice(field.getPrice());

        Reservation reservation = new Reservation(user,field,command.date(),command.startTime(),price,command.duration());

        Reservation savedReservation = reservationRepositoryPort.save(reservation);

        String link = paymentPort.createPayment(savedReservation);

        return new CreateReservationResult(savedReservation, link);
    }


    private void validateAvailability(Long fieldId, LocalDate date, LocalTime startTime, ReservationDuration duration) {
        List<Reservation> reservasExistentes = reservationRepositoryPort.findByFieldIdAndDate(fieldId, date);
        LocalTime endTime = startTime.plusMinutes(duration.getMinutes());

        boolean hayConflicto = reservasExistentes.stream()
                .anyMatch(reserva ->
                        startTime.isBefore(reserva.getEndTime()) && endTime.isAfter(reserva.getStartTime())
                );

        if (hayConflicto) {
            throw new ReservationConflictException("El horario ya está reservado");
        }
    }


}
