package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.port.in.field.CreateFieldUseCase;
import com.example.reserva_canchas.domain.port.in.field.GetFieldUseCase;
import com.example.reserva_canchas.domain.port.in.field.UpdateActiveUseCase;
import com.example.reserva_canchas.infrastructure.dto.request.CreateFieldRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.FieldResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.FieldMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.web.servlet.function.ServerResponse.status;

@RestController
@RequestMapping("/fields")
@RequiredArgsConstructor
public class FieldController {

    private final CreateFieldUseCase createFieldUseCase;
    private final GetFieldUseCase getFieldUseCase;
    private final UpdateActiveUseCase updateActiveUseCase;

    @PostMapping
    public ResponseEntity<FieldResponseDTO>  create(@Valid @RequestBody CreateFieldRequestDTO fieldDto){

        Field field = createFieldUseCase.create(
                fieldDto.name(),
                fieldDto.type(),
                fieldDto.idLocation(),
                fieldDto.price());

        return ResponseEntity.status(HttpStatus.CREATED).body(FieldMapper.toResponse(field));

    }


    @GetMapping
    public ResponseEntity<List<FieldResponseDTO>>  getAllFields(){

        List<FieldResponseDTO> fields = getFieldUseCase.getFields().stream().map(FieldMapper::toResponse).toList();

        return ResponseEntity.ok(fields);

    }



}
