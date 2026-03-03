package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.domain.model.Field;
import com.example.reserva_canchas.domain.model.enums.TypeField;
import com.example.reserva_canchas.domain.port.in.field.CreateFieldUseCase;
import com.example.reserva_canchas.domain.port.in.field.GetFieldUseCase;
import com.example.reserva_canchas.domain.port.in.field.UpdateActiveUseCase;
import com.example.reserva_canchas.infrastructure.dto.request.CreateFieldRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.FieldResponseDTO;
import com.example.reserva_canchas.infrastructure.mapper.FieldMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fields")
@RequiredArgsConstructor
@Tag(name = "Canchas", description = "Endpoints para gestión de canchas de pádel")
public class FieldController {

    private final CreateFieldUseCase createFieldUseCase;
    private final GetFieldUseCase getFieldUseCase;
    private final UpdateActiveUseCase updateActiveUseCase;

    @PostMapping
    @Operation(summary = "Crear cancha", description = "Crea una nueva cancha. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Cancha creada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<FieldResponseDTO>  create(@Valid @RequestBody CreateFieldRequestDTO fieldDto){

        Field field = createFieldUseCase.create(
                fieldDto.name(),
                fieldDto.type(),
                fieldDto.price());

        return ResponseEntity.status(HttpStatus.CREATED).body(FieldMapper.toResponse(field));

    }


    @GetMapping
    @Operation(summary = "Listar canchas", description = "Retorna todas las canchas disponibles")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista obtenida"),
            @ApiResponse(responseCode = "403", description = "Sin permisos")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<List<FieldResponseDTO>> getAllFields(){

        List<FieldResponseDTO> fields = getFieldUseCase.getFields().stream().map(FieldMapper::toResponse).toList();

        return ResponseEntity.ok(fields);

    }


    @GetMapping("/{id}")
    @Operation(summary = "Obtener cancha por ID", description = "Retorna una cancha específica")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cancha encontrada"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Cancha no encontrada")
    })
    public ResponseEntity<FieldResponseDTO>  getFieldById(@PathVariable Long id){
        Field field = getFieldUseCase.getFieldById(id);

        return ResponseEntity.ok(FieldMapper.toResponse(field));

    }

    @PutMapping("/{id}/desactivate")
    @Operation(summary = "Activar cancha", description = "Activa una cancha desactivada. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cancha activada"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Cancha no encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> deactivate (@PathVariable Long id){
        updateActiveUseCase.updateActive(id, false);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/activate")
    @Operation(summary = "Desactivar cancha", description = "Desactiva una cancha activa. Solo ADMIN")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cancha desactivada"),
            @ApiResponse(responseCode = "403", description = "Sin permisos"),
            @ApiResponse(responseCode = "404", description = "Cancha no encontrada")
    })
    @SecurityRequirement(name = "bearerAuth")
    public ResponseEntity<Void> activate (@PathVariable Long id){
        updateActiveUseCase.updateActive(id, true);
        return ResponseEntity.noContent().build();
    }







}
