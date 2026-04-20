package com.fiap.begin_projetct.service;

import com.fiap.begin_projetct.model.Usuario;
import com.fiap.begin_projetct.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final SessionService sessionService;

    public String login(String email, String senha) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        
        if (usuarioOpt.isEmpty()) {
            throw new RuntimeException("Usuário não encontrado");
        }

        Usuario usuario = usuarioOpt.get();
        
        if (!passwordEncoder.matches(senha, usuario.getSenha())) {
            throw new RuntimeException("Senha incorreta");
        }

        // Gerar novo token
        String token = jwtService.generateToken(email);
        
        // Atualizar dados da sessão
        LocalDateTime agora = LocalDateTime.now();
        usuarioRepository.atualizarUltimoAcesso(usuario.getId(), agora);
        
        return token;
    }

    public void criarUsuario(String email, String senha) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("E-mail já cadastrado");
        }

        Usuario usuario = new Usuario();
        usuario.setEmail(email);
        usuario.setSenha(passwordEncoder.encode(senha));
        usuario.setSessaoAtiva(false);
        
        usuarioRepository.save(usuario);
    }

    public boolean validarSessao(String email) {
        return sessionService.validarSessao(email);
    }

    public void logout(String email) {
        sessionService.logout(email);
    }

    public void limparSessoesExpiradas() {
        sessionService.limparSessoesExpiradas();
    }
}
