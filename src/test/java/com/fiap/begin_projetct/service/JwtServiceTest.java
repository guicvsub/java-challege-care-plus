package com.fiap.begin_projetct.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestPropertySource(properties = {
    "jwt.secret=testSecretKeyForTesting123456789",
    "jwt.expiration=900000" // 15 minutos
})
class JwtServiceTest {

    private JwtService jwtService;
    private String testEmail = "test@example.com";
    private String testSecret = "testSecretKeyForTesting123456789";

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
        // Usar reflection para setar os valores privados para teste
        try {
            var secretField = JwtService.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(jwtService, testSecret);

            var expirationField = JwtService.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(jwtService, 900000L);
        } catch (Exception e) {
            throw new RuntimeException("Failed to setup test", e);
        }
    }

    // CT12 - Token válido
    @Test
    @DisplayName("CT12 - Token válido deve permitir acesso ao endpoint protegido")
    void tokenValido_DevePermitirAcesso() {
        String token = jwtService.generateToken(testEmail);
        
        assertNotNull(token, "Token não deveria ser nulo");
        assertFalse(token.isEmpty(), "Token não deveria ser vazio");
        
        String extractedEmail = jwtService.extractEmail(token);
        assertEquals(testEmail, extractedEmail, "Email extraído deveria ser igual ao original");
        
        Boolean isValid = jwtService.validateToken(token, testEmail);
        assertTrue(isValid, "Token válido deveria ser validado com sucesso");
    }

    // CT13 - Token ausente
    @Test
    @DisplayName("CT13 - Token ausente deve retornar 401")
    void tokenAusente_DeveRetornar401() {
        String token = null;
        
        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(token);
        }, "Token ausente deveria lançar exceção");
    }

    // CT14 - Token inválido (assinatura errada)
    @Test
    @DisplayName("CT14 - Token inválido com assinatura errada deve ser rejeitado")
    void tokenInvalidoAssinaturaErrada_DeveSerRejeitado() {
        // Criar token com assinatura diferente
        SecretKey wrongKey = Keys.hmacShaKeyFor("wrongSecretKey123456789012345678".getBytes());
        
        String invalidToken = Jwts.builder()
                .setSubject(testEmail)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 900000))
                .signWith(wrongKey, SignatureAlgorithm.HS256)
                .compact();

        assertThrows(Exception.class, () -> {
            jwtService.extractEmail(invalidToken);
        }, "Token com assinatura inválida deveria lançar exceção");
        
        Boolean isValid = jwtService.validateToken(invalidToken, testEmail);
        assertFalse(isValid, "Token com assinatura inválida não deveria ser válido");
    }

    // CT15 - Token expirado (15 min)
    @Test
    @DisplayName("CT15 - Token expirado deve retornar 401")
    void tokenExpirado_DeveRetornar401() throws InterruptedException {
        // Criar serviço com expiração curta para teste
        JwtService shortLivedJwtService = new JwtService();
        try {
            var secretField = JwtService.class.getDeclaredField("secret");
            secretField.setAccessible(true);
            secretField.set(shortLivedJwtService, testSecret);

            var expirationField = JwtService.class.getDeclaredField("expiration");
            expirationField.setAccessible(true);
            expirationField.set(shortLivedJwtService, 100L); // 100ms
        } catch (Exception e) {
            throw new RuntimeException("Failed to setup test", e);
        }

        String token = shortLivedJwtService.generateToken(testEmail);
        
        // Esperar expiração
        Thread.sleep(150);
        
        Boolean isValid = shortLivedJwtService.validateToken(token, testEmail);
        assertFalse(isValid, "Token expirado não deveria ser válido");
        
        // Verificar se está expirado
        assertThrows(Exception.class, () -> {
            shortLivedJwtService.extractEmail(token);
        }, "Token expirado deveria lançar exceção ao extrair email");
    }

    // Testes adicionais para validação de expiração de sessão
    @Test
    @DisplayName("Sessão inativa por mais de 1 minuto deve ser expirada")
    void sessaoInativaPorMaisDe1Minuto_DeveSerExpirada() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime sessaoAntiga = agora.minusMinutes(2); // 2 minutos atrás
        
        Boolean isExpired = jwtService.isSessionExpired(sessaoAntiga);
        assertTrue(isExpired, "Sessão inativa por mais de 1 minuto deveria estar expirada");
    }

    @Test
    @DisplayName("Sessão ativa há menos de 1 minuto não deve ser expirada")
    void sessaoAtivaMenosDe1Minuto_NaoDeveSerExpirada() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime sessaoRecente = agora.minusSeconds(30); // 30 segundos atrás
        
        Boolean isExpired = jwtService.isSessionExpired(sessaoRecente);
        assertFalse(isExpired, "Sessão ativa há menos de 1 minuto não deveria estar expirada");
    }

    @Test
    @DisplayName("Sessão com mais de 5 minutos deve ser expirada")
    void sessaoComMaisDe5Minutos_DeveSerExpirada() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime sessaoAntiga = agora.minusMinutes(6); // 6 minutos atrás
        
        Boolean isExpired = jwtService.isSessionExpired(sessaoAntiga);
        assertTrue(isExpired, "Sessão com mais de 5 minutos deveria estar expirada");
    }

    @Test
    @DisplayName("Token próximo da expiração deve ser identificado")
    void tokenProximoExpiracao_DeveSerIdentificado() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime tokenExpiracao = agora.plusSeconds(30); // 30 segundos
        
        Boolean isNearExpiration = jwtService.isTokenNearExpiration(tokenExpiracao);
        assertTrue(isNearExpiration, "Token próximo da expiração deveria ser identificado");
    }

    @Test
    @DisplayName("Token longe da expiração não deve ser identificado como próximo")
    void tokenLongeExpiracao_NaoDeveSerIdentificado() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime tokenExpiracao = agora.plusMinutes(10); // 10 minutos
        
        Boolean isNearExpiration = jwtService.isTokenNearExpiration(tokenExpiracao);
        assertFalse(isNearExpiration, "Token longe da expiração não deveria ser identificado como próximo");
    }

    @Test
    @DisplayName("Data de expiração deve ser calculada corretamente")
    void dataExpiracao_DeveSerCalculadaCorretamente() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime expiracao = jwtService.getExpirationDate();
        
        // Verificar se está aproximadamente 15 minutos no futuro
        LocalDateTime esperado = agora.plusMinutes(15);
        
        assertTrue(expiracao.isAfter(esperado.minusSeconds(5)) && 
                  expiracao.isBefore(esperado.plusSeconds(5)), 
                  "Data de expiração deveria ser aproximadamente 15 minutos no futuro");
    }
}
