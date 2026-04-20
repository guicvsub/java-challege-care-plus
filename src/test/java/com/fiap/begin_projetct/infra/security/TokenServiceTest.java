package com.fiap.begin_projetct.infra.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TokenServiceTest {

    @Autowired
    private TokenService tokenService;

    @Test
    void deveGerarTokenValido() {
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret-key");
        String login = "test@example.com";
        String token = tokenService.gerarToken(login);
        
        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    void deveExtrairSubjectDoToken() {
        ReflectionTestUtils.setField(tokenService, "secret", "test-secret-key");
        String login = "test@example.com";
        String token = tokenService.gerarToken(login);
        
        String subject = tokenService.getSubject(token);
        
        assertEquals(login, subject);
    }

    @Test
    void deveLancarExcecaoParaTokenInvalido() {
        String tokenInvalido = "token-invalido";
        
        assertThrows(RuntimeException.class, () -> {
            tokenService.getSubject(tokenInvalido);
        });
    }
}
