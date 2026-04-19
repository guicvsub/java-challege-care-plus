package com.fiap.begin_projetct.service;

import com.fiap.begin_projetct.exception.PacienteAlreadyExistsException;
import com.fiap.begin_projetct.exception.PacienteNotFoundException;
import com.fiap.begin_projetct.model.Paciente;
import com.fiap.begin_projetct.repository.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Tests for PacienteService")
class PacienteServiceTest {

    @Mock
    private PacienteRepository pacienteRepository;

    @InjectMocks
    private PacienteService pacienteService;

    private Paciente paciente;
    private Paciente pacienteExistente;

    @BeforeEach
    void setUp() {
        paciente = Paciente.builder()
                .id(null)
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
    @DisplayName("Should return all patients when listarTodos is called")
    void listarTodos_ShouldReturnAllPatients() {
        // Arrange
        List<Paciente> pacientes = Arrays.asList(pacienteExistente);
        when(pacienteRepository.findAll()).thenReturn(pacientes);

        // Act
        List<Paciente> result = pacienteService.listarTodos();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(pacienteExistente.getNome(), result.get(0).getNome());
        verify(pacienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should return patient when buscarPorId is called with valid ID")
    void buscarPorId_ShouldReturnPatient_WhenValidId() {
        // Arrange
        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteExistente));

        // Act
        Optional<Paciente> result = pacienteService.buscarPorId(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pacienteExistente.getNome(), result.get().getNome());
        verify(pacienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should return empty when buscarPorId is called with invalid ID")
    void buscarPorId_ShouldReturnEmpty_WhenInvalidId() {
        // Arrange
        when(pacienteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<Paciente> result = pacienteService.buscarPorId(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(pacienteRepository, times(1)).findById(999L);
    }

    @Test
    @DisplayName("Should return patient when buscarPorCpf is called with valid CPF")
    void buscarPorCpf_ShouldReturnPatient_WhenValidCpf() {
        // Arrange
        when(pacienteRepository.findByCpf("987.654.321-00")).thenReturn(Optional.of(pacienteExistente));

        // Act
        Optional<Paciente> result = pacienteService.buscarPorCpf("987.654.321-00");

        // Assert
        assertTrue(result.isPresent());
        assertEquals(pacienteExistente.getCpf(), result.get().getCpf());
        verify(pacienteRepository, times(1)).findByCpf("987.654.321-00");
    }

    @Test
    @DisplayName("Should return empty when buscarPorCpf is called with invalid CPF")
    void buscarPorCpf_ShouldReturnEmpty_WhenInvalidCpf() {
        // Arrange
        when(pacienteRepository.findByCpf("111.111.111-11")).thenReturn(Optional.empty());

        // Act
        Optional<Paciente> result = pacienteService.buscarPorCpf("111.111.111-11");

        // Assert
        assertFalse(result.isPresent());
        verify(pacienteRepository, times(1)).findByCpf("111.111.111-11");
    }

    @Test
    @DisplayName("Should save patient when salvar is called with valid data")
    void salvar_ShouldSavePatient_WhenValidData() {
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

        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(false);
        when(pacienteRepository.existsByEmail(paciente.getEmail())).thenReturn(false);
        when(pacienteRepository.save(any(Paciente.class))).thenReturn(savedPaciente);

        // Act
        Paciente result = pacienteService.salvar(paciente);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals(paciente.getNome(), result.getNome());
        verify(pacienteRepository, times(1)).existsByCpf(paciente.getCpf());
        verify(pacienteRepository, times(1)).existsByEmail(paciente.getEmail());
        verify(pacienteRepository, times(1)).save(paciente);
    }

    @Test
    @DisplayName("Should throw PacienteAlreadyExistsException when salvar is called with existing CPF")
    void salvar_ShouldThrowException_WhenCpfExists() {
        // Arrange
        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(true);

        // Act & Assert
        PacienteAlreadyExistsException exception = assertThrows(
                PacienteAlreadyExistsException.class,
                () -> pacienteService.salvar(paciente)
        );

        assertEquals("Já existe um paciente com este CPF", exception.getMessage());
        verify(pacienteRepository, times(1)).existsByCpf(paciente.getCpf());
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw PacienteAlreadyExistsException when salvar is called with existing email")
    void salvar_ShouldThrowException_WhenEmailExists() {
        // Arrange
        when(pacienteRepository.existsByCpf(paciente.getCpf())).thenReturn(false);
        when(pacienteRepository.existsByEmail(paciente.getEmail())).thenReturn(true);

        // Act & Assert
        PacienteAlreadyExistsException exception = assertThrows(
                PacienteAlreadyExistsException.class,
                () -> pacienteService.salvar(paciente)
        );

        assertEquals("Já existe um paciente com este e-mail", exception.getMessage());
        verify(pacienteRepository, times(1)).existsByCpf(paciente.getCpf());
        verify(pacienteRepository, times(1)).existsByEmail(paciente.getEmail());
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should update patient when atualizar is called with valid ID and data")
    void atualizar_ShouldUpdatePatient_WhenValidIdAndData() {
        // Arrange
        Paciente updatedData = Paciente.builder()
                .nome("João Silva Atualizado")
                .cpf("123.456.789-00")
                .email("joao.atualizado@email.com")
                .telefone("(11) 99999-8888")
                .dataNascimento(LocalDate.of(1990, 5, 15))
                .convenio("Bradesco")
                .build();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteExistente));
        when(pacienteRepository.existsByCpf(updatedData.getCpf())).thenReturn(false);
        when(pacienteRepository.existsByEmail(updatedData.getEmail())).thenReturn(false);
        when(pacienteRepository.save(any(Paciente.class))).thenAnswer(invocation -> {
            Paciente p = invocation.getArgument(0);
            p.setId(1L);
            return p;
        });

        // Act
        Paciente result = pacienteService.atualizar(1L, updatedData);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("João Silva Atualizado", result.getNome());
        assertEquals("joao.atualizado@email.com", result.getEmail());
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).save(any(Paciente.class));
    }

    @Test
    @DisplayName("Should throw PacienteNotFoundException when atualizar is called with invalid ID")
    void atualizar_ShouldThrowException_WhenInvalidId() {
        // Arrange
        when(pacienteRepository.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> pacienteService.atualizar(999L, paciente)
        );

        assertEquals("Paciente não encontrado", exception.getMessage());
        verify(pacienteRepository, times(1)).findById(999L);
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should throw PacienteAlreadyExistsException when atualizar is called with existing CPF from another patient")
    void atualizar_ShouldThrowException_WhenCpfExistsInAnotherPatient() {
        // Arrange
        Paciente updatedData = Paciente.builder()
                .nome("Maria Souza Atualizada")
                .cpf("111.111.111-11") // Different CPF
                .email("maria.souza@email.com") // Same email
                .build();

        when(pacienteRepository.findById(1L)).thenReturn(Optional.of(pacienteExistente));
        when(pacienteRepository.existsByCpf("111.111.111-11")).thenReturn(true);

        // Act & Assert
        PacienteAlreadyExistsException exception = assertThrows(
                PacienteAlreadyExistsException.class,
                () -> pacienteService.atualizar(1L, updatedData)
        );

        assertEquals("Já existe um paciente com este CPF", exception.getMessage());
        verify(pacienteRepository, times(1)).findById(1L);
        verify(pacienteRepository, times(1)).existsByCpf("111.111.111-11");
        verify(pacienteRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should delete patient when deletar is called with valid ID")
    void deletar_ShouldDeletePatient_WhenValidId() {
        // Arrange
        when(pacienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(pacienteRepository).deleteById(1L);

        // Act
        assertDoesNotThrow(() -> pacienteService.deletar(1L));

        // Assert
        verify(pacienteRepository, times(1)).existsById(1L);
        verify(pacienteRepository, times(1)).deleteById(1L);
    }

    @Test
    @DisplayName("Should throw PacienteNotFoundException when deletar is called with invalid ID")
    void deletar_ShouldThrowException_WhenInvalidId() {
        // Arrange
        when(pacienteRepository.existsById(999L)).thenReturn(false);

        // Act & Assert
        PacienteNotFoundException exception = assertThrows(
                PacienteNotFoundException.class,
                () -> pacienteService.deletar(999L)
        );

        assertEquals("Paciente não encontrado", exception.getMessage());
        verify(pacienteRepository, times(1)).existsById(999L);
        verify(pacienteRepository, never()).deleteById(any());
    }
}
