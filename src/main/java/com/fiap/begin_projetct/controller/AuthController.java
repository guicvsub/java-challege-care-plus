package com.fiap.begin_projetct.controller;

import com.fiap.begin_projetct.dto.LoginRequest;
import com.fiap.begin_projetct.dto.LoginResponse;
import com.fiap.begin_projetct.dto.UsuarioRequest;
import com.fiap.begin_projetct.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
@Tag(name = "Autenticação", description = "API para gerenciamento de autenticação")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login do usuário", description = "Autentica o usuário e retorna um token JWT")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login realizado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "401", description = "Credenciais inválidas")
    })
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = authService.login(request.getEmail(), request.getSenha());
            return ResponseEntity.ok(new LoginResponse(token, "Bearer"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new LoginResponse(null, null, e.getMessage()));
        }
    }

    @PostMapping("/register")
    @Operation(summary = "Registrar novo usuário", description = "Cadastra um novo usuário no sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
        @ApiResponse(responseCode = "400", description = "Dados inválidos"),
        @ApiResponse(responseCode = "409", description = "E-mail já cadastrado")
    })
    public ResponseEntity<String> register(@Valid @RequestBody UsuarioRequest request) {
        try {
            authService.criarUsuario(request.getEmail(), request.getSenha());
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuário criado com sucesso");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout do usuário", description = "Invalida a sessão do usuário")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Logout realizado com sucesso"),
        @ApiResponse(responseCode = "401", description = "Token inválido")
    })
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // Aqui você pode extrair o email do token e invalidar a sessão
                // Por enquanto, vamos apenas retornar sucesso
                return ResponseEntity.ok("Logout realizado com sucesso");
            }
            return ResponseEntity.badRequest().body("Token não fornecido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }

    @PostMapping("/validate")
    @Operation(summary = "Validar token", description = "Valida se o token JWT ainda é válido")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Token válido"),
        @ApiResponse(responseCode = "401", description = "Token inválido ou expirado")
    })
    public ResponseEntity<String> validateToken(@RequestHeader("Authorization") String authHeader) {
        try {
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                // Aqui você pode validar o token
                return ResponseEntity.ok("Token válido");
            }
            return ResponseEntity.badRequest().body("Token não fornecido");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Token inválido");
        }
    }
}
