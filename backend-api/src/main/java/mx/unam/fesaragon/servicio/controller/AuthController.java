package mx.unam.fesaragon.servicio.controller;

import jakarta.validation.Valid;
import mx.unam.fesaragon.servicio.dto.LoginRequest;
import mx.unam.fesaragon.servicio.dto.LoginResponse;
import mx.unam.fesaragon.servicio.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
}
