package com.example.reserva_canchas.infrastructure.controller;

import com.example.reserva_canchas.infrastructure.dto.request.LoginRequestDTO;
import com.example.reserva_canchas.infrastructure.dto.response.AuthResponseDTO;
import com.example.reserva_canchas.infrastructure.security.JwtService;
import com.example.reserva_canchas.infrastructure.security.UserDetailsAdapter;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        UserDetailsAdapter userDetails = (UserDetailsAdapter) authentication.getPrincipal();
        String token = jwtService.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponseDTO(
                token,
                userDetails.getUsername(),
                userDetails.getUser().getRole().name()
        ));
    }
}