package com.fiap.begin_projetct.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fiap.begin_projetct.model.Paciente;
import com.fiap.begin_projetct.service.PacienteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PacienteController.class)
@DisplayName("Integration Tests for PacienteController")
class PacienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PacienteService pacienteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Paciente paciente;
    private Paciente pacienteExistente;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new JavaTimeModule());

        paciente = Paciente.builder()
                .nome("João Silva")
                .cpf("123.456.789-00")
                .email("joao.silva@email.com")
                .telefone("(11) 98765-4321")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .convenio("Unimed")
                .build();

        pacienteExistente = Paciente.builder()
                .id(1L)
                .nome("Maria Souza")
                .cpf("987.654.321-00")
                .email("maria.souza@email.com")
                .telefone("(11) 12345-6789")
                .dataNascimento(LocalDate.of(1985, 8, 20))
                .convenio("Amil")
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    @DisplayName("Should return 200 and list all patients when GET /api/pacientes is called")
    void listarTodos_ShouldReturn200AndPatientList() throws Exception {
        // Arrange
        List<Paciente> pacientes = Arrays.asList(pacienteExistente);
        when(pacienteService.listarTodos()).thenReturn(pacientes);

        // Act & Assert
        mockMvc.perform(get("/api/pacientes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].nome").value("Maria Souza"))
                .andExpect(jsonPath("$[0].cpf").value("987.654.321-00"));
    }

    @Test
    @DisplayName("Should return 200 and patient when GET /api/pacientes/{id} is called with valid ID")
    void buscarPorId_ShouldReturn200AndPatient_WhenValidId() throws Exception {
        // Arrange
        when(pacienteService.buscarPorId(1L)).thenReturn(Optional.of(pacienteExistente));

        // Act & Assert
        mockMvc.perform(get("/api/pacientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria Souza"))
                .andExpect(jsonPath("$.cpf").value("987.654.321-00"));
    }

    @Test
    @DisplayName("Should return 404 when GET /api/pacientes/{id} is called with invalid ID")
    void buscarPorId_ShouldReturn404_WhenInvalidId() throws Exception {
        // Arrange
        when(pacienteService.buscarPorId(999L)).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/pacientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 200 and patient when GET /api/pacientes/cpf/{cpf} is called with valid CPF")
    void buscarPorCpf_ShouldReturn200AndPatient_WhenValidCpf() throws Exception {
        // Arrange
        when(pacienteService.buscarPorCpf("987.654.321-00")).thenReturn(Optional.of(pacienteExistente));

        // Act & Assert
        mockMvc.perform(get("/api/pacientes/cpf/987.654.321-00"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.cpf").value("987.654.321-00"));
    }

    @Test
    @DisplayName("Should return 404 when GET /api/pacientes/cpf/{cpf} is called with invalid CPF")
    void buscarPorCpf_ShouldReturn404_WhenInvalidCpf() throws Exception {
        // Arrange
        when(pacienteService.buscarPorCpf("111.111.111-11")).thenReturn(Optional.empty());

        // Act & Assert
        mockMvc.perform(get("/api/pacientes/cpf/111.111.111-11"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 201 and created patient when POST /api/pacientes is called with valid data")
    void criar_ShouldReturn201AndPatient_WhenValidData() throws Exception {
        // Arrange
        Paciente savedPaciente = Paciente.builder()
                .id(2L)
                .nome(paciente.getNome())
                .cpf(paciente.getCpf())
                .email(paciente.getEmail())
                .telefone(paciente.getTelefone())
                .dataNascimento(paciente.getDataNascimento())
                .convenio(paciente.getConvenio())
                .build();

        when(pacienteService.salvar(any(Paciente.class))).thenReturn(savedPaciente);

        // Act & Assert
        mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(2))
                .andExpect(jsonPath("$.nome").value("João Silva"))
                .andExpect(jsonPath("$.cpf").value("123.456.789-00"));
    }

    @Test
    @DisplayName("Should return 400 when POST /api/pacientes is called with invalid data")
    void criar_ShouldReturn400_WhenInvalidData() throws Exception {
        // Arrange
        Paciente invalidPaciente = Paciente.builder()
                .nome("") // Empty name
                .cpf("") // Empty CPF
                .email("invalid-email") // Invalid email
                .telefone("")
                .build();

        // Act & Assert
        mockMvc.perform(post("/api/pacientes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPaciente)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("Validation failed"));
    }

    @Test
    @DisplayName("Should return 200 and updated patient when PUT /api/pacientes/{id} is called with valid data")
    void atualizar_ShouldReturn200AndUpdatedPatient_WhenValidData() throws Exception {
        // Arrange
        Paciente updatedData = Paciente.builder()
                .nome("Maria Souza Atualizada")
                .cpf("987.654.321-00")
                .email("maria.atualizada@email.com")
                .telefone("(11) 99999-8888")
                .dataNascimento(LocalDate.of(1985, 8, 20))
                .convenio("Bradesco")
                .build();

        Paciente updatedPaciente = Paciente.builder()
                .id(1L)
                .nome("Maria Souza Atualizada")
                .cpf("987.654.321-00")
                .email("maria.atualizada@email.com")
                .telefone("(11) 99999-8888")
                .dataNascimento(LocalDate.of(1985, 8, 20))
                .convenio("Bradesco")
                .build();

        when(pacienteService.atualizar(eq(1L), any(Paciente.class))).thenReturn(updatedPaciente);

        // Act & Assert
        mockMvc.perform(put("/api/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedData)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.nome").value("Maria Souza Atualizada"))
                .andExpect(jsonPath("$.email").value("maria.atualizada@email.com"));
    }

    @Test
    @DisplayName("Should return 400 when PUT /api/pacientes/{id} is called with invalid data")
    void atualizar_ShouldReturn400_WhenInvalidData() throws Exception {
        // Arrange
        Paciente invalidPaciente = Paciente.builder()
                .nome("")
                .cpf("")
                .email("invalid-email")
                .telefone("")
                .build();

        // Act & Assert
        mockMvc.perform(put("/api/pacientes/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPaciente)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.error").value("Bad Request"));
    }

    @Test
    @DisplayName("Should return 404 when PUT /api/pacientes/{id} is called with invalid ID")
    void atualizar_ShouldReturn404_WhenInvalidId() throws Exception {
        // Arrange
        when(pacienteService.atualizar(eq(999L), any(Paciente.class)))
                .thenThrow(new RuntimeException("Paciente não encontrado"));

        // Act & Assert
        mockMvc.perform(put("/api/pacientes/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(paciente)))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should return 204 when DELETE /api/pacientes/{id} is called with valid ID")
    void deletar_ShouldReturn204_WhenValidId() throws Exception {
        // Act & Assert
        mockMvc.perform(delete("/api/pacientes/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should return 404 when DELETE /api/pacientes/{id} is called with invalid ID")
    void deletar_ShouldReturn404_WhenInvalidId() throws Exception {
        // Arrange
        when(pacienteService.deletar(999L))
                .thenThrow(new RuntimeException("Paciente não encontrado"));

        // Act & Assert
        mockMvc.perform(delete("/api/pacientes/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should handle CORS preflight requests")
    void cors_ShouldHandlePreflightRequests() throws Exception {
        // Act & Assert
        mockMvc.perform(options("/api/pacientes")
                        .header("Origin", "http://localhost:3000")
                        .header("Access-Control-Request-Method", "POST")
                        .header("Access-Control-Request-Headers", "Content-Type"))
                .andExpect(status().isOk());
    }
}
