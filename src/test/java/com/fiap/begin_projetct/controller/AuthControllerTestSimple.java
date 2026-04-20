package com.fiap.begin_projetct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fiap.begin_projetct.dto.LoginRequest;
import com.fiap.begin_projetct.dto.LoginResponse;
import com.fiap.begin_projetct.service.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class AuthControllerTestSimple {

    private AuthService authService;
    private AuthController authController;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        authService = Mockito.mock(AuthService.class);
        authController = new AuthController(authService);
        objectMapper = new ObjectMapper();
    }

    // CT01 - Login válido
    @Test
    @DisplayName("CT01 - Login válido deve retornar JWT e status 200")
    void loginValido_DeveRetornarJwtEStatus200() {
        LoginRequest request = new LoginRequest();
        request.setEmail("usuario@teste.com");
        request.setSenha("Senha@123");

        when(authService.login("usuario@teste.com", "Senha@123"))
                .thenReturn("jwt-token-valido");

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("jwt-token-valido", response.getBody().getToken());
        assertEquals("Bearer", response.getBody().getType());
    }

    // CT02 - Email inválido (formato) - Note: validação de formato é feita pelo Bean Validation
    @Test
    @DisplayName("CT02 - Email inválido deve retornar 401 (usuário não encontrado)")
    void loginComEmailInvalido_DeveRetornar401() {
        LoginRequest request = new LoginRequest();
        request.setEmail("email-invalido");
        request.setSenha("Senha@123");

        when(authService.login("email-invalido", "Senha@123"))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody().getError());
    }

    // CT03 - Email não cadastrado
    @Test
    @DisplayName("CT03 - Email não cadastrado deve retornar 401")
    void loginComEmailNaoCadastrado_DeveRetornar401() {
        LoginRequest request = new LoginRequest();
        request.setEmail("naoexiste@teste.com");
        request.setSenha("Senha@123");

        when(authService.login("naoexiste@teste.com", "Senha@123"))
                .thenThrow(new RuntimeException("Usuário não encontrado"));

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Usuário não encontrado", response.getBody().getError());
    }

    // CT04 - Senha incorreta
    @Test
    @DisplayName("CT04 - Senha incorreta deve retornar 401")
    void loginComSenhaIncorreta_DeveRetornar401() {
        LoginRequest request = new LoginRequest();
        request.setEmail("usuario@teste.com");
        request.setSenha("senhaErrada");

        when(authService.login("usuario@teste.com", "senhaErrada"))
                .thenThrow(new RuntimeException("Senha incorreta"));

        ResponseEntity<LoginResponse> response = authController.login(request);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Senha incorreta", response.getBody().getError());
    }

    // Teste adicional - Registro válido
    @Test
    @DisplayName("Registro válido deve retornar 201")
    void registrarUsuarioValido_DeveRetornar201() {
        com.fiap.begin_projetct.dto.UsuarioRequest request = new com.fiap.begin_projetct.dto.UsuarioRequest();
        request.setEmail("novo@teste.com");
        request.setSenha("Senha@123");

        Mockito.doNothing().when(authService).criarUsuario(any(String.class), any(String.class));

        ResponseEntity<String> response = authController.register(request);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Usuário criado com sucesso", response.getBody());
    }

    // Teste adicional - Registro com email já existente
    @Test
    @DisplayName("Registro com email já existente deve retornar 400")
    void registrarUsuarioExistente_DeveRetornar400() {
        com.fiap.begin_projetct.dto.UsuarioRequest request = new com.fiap.begin_projetct.dto.UsuarioRequest();
        request.setEmail("usuario@teste.com");
        request.setSenha("Senha@123");

        Mockito.doThrow(new RuntimeException("E-mail já cadastrado"))
                .when(authService).criarUsuario("usuario@teste.com", "Senha@123");

        ResponseEntity<String> response = authController.register(request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("E-mail já cadastrado", response.getBody());
    }
}
