package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
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


    @GetMapping("/{id}")
    public ResponseEntity<FieldResponseDTO>  getFieldById(@PathVariable Long id){
        Field field = getFieldUseCase.getFieldById(id);

        return ResponseEntity.ok(FieldMapper.toResponse(field));

    }

    @GetMapping("/types/{type}")
    public ResponseEntity<List<FieldResponseDTO>>  getAllFieldsByType(@PathVariable TypeField type){

        List<FieldResponseDTO> fields = getFieldUseCase.getFieldsByType(type).stream().map(FieldMapper::toResponse).toList();

        return ResponseEntity.ok(fields);
    }


    @PutMapping("/{id}/desactivate")
    public ResponseEntity<Void> deactivate (@PathVariable Long id){
        updateActiveUseCase.updateActive(id, false);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    public ResponseEntity<Void> activate (@PathVariable Long id){
        updateActiveUseCase.updateActive(id, true);
        return ResponseEntity.noContent().build();
    }







}
