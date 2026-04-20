import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class TesteHashAdmin {
    public static void main(String[] args) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        
        String senha = "Admin@123";
        String hashBanco = "$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.";
        
        // Gerar novo hash para comparação
        String novoHash = encoder.encode(senha);
        
        System.out.println("=== DEBUG HASH ADMIN ===");
        System.out.println("Senha: " + senha);
        System.out.println("Hash no banco: " + hashBanco);
        System.out.println("Novo hash gerado: " + novoHash);
        System.out.println("Tamanho hash banco: " + hashBanco.length());
        System.out.println("Tamanho novo hash: " + novoHash.length());
        System.out.println();
        
        // Testar se o hash do banco corresponde à senha
        boolean matches = encoder.matches(senha, hashBanco);
        System.out.println("Hash do banco corresponde à senha: " + matches);
        
        // Testar se o novo hash corresponde
        boolean novoMatches = encoder.matches(senha, novoHash);
        System.out.println("Novo hash corresponde à senha: " + novoMatches);
        
        // Verificar se os hashes são iguais
        System.out.println("Hashes são idênticos: " + hashBanco.equals(novoHash));
        
        if (!matches) {
            System.out.println();
            System.out.println("=== SOLUÇÃO ===");
            System.out.println("UPDATE usuarios SET senha = '" + novoHash + "' WHERE email = 'admin@careplus.com';");
        }
    }
}
