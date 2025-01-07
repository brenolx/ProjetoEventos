package services;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;
import enuns.TipoUsuario;
import userinterfaces.TelaPrincipalAdmin;

import javax.swing.JOptionPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {

    public boolean logarUsuario(String email, String senha) throws IOException {
        // Validação de campos vazios
        if (email == null || email.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo de email não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (senha == null || senha.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "O campo de senha não pode estar vazio.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validação de formato de email
        if (!isEmailValido(email)) {
            JOptionPane.showMessageDialog(null, "O email fornecido não é válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Validação de comprimento da senha
        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(null, "A senha deve ter pelo menos 6 caracteres.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        Connection conn = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                JOptionPane.showMessageDialog(null, "Erro: Conexão com o banco de dados não foi estabelecida.", "Erro de Conexão", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar uma instância do UsuarioDAO
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            // Verificar se o usuário existe e obter os dados
            Usuario usuario = usuarioDAO.getUsuarioPorEmail(email);
            System.out.println(usuario.getNomeCompleto());

            // Verificar se o usuário foi encontrado e se a senha está correta
            if (usuario != null && usuario.getSenha().equals(senha)) {
            	
                // Verificar se o usuário é um administrador
                if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                	
                    // Abrir a tela de gerenciamento de eventos
                    TelaPrincipalAdmin telaAdmin = new TelaPrincipalAdmin();
                    telaAdmin.setNome(usuario.getNomeCompleto());
                    telaAdmin.setVisible(true);
                    
                } else if (usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
                	
                }
                return true; // Login bem-sucedido
            } else {
                JOptionPane.showMessageDialog(null, "Email ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                return false; // Login falhou
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false em caso de erro
        } finally {
            // Desconectar do banco de dados
            try {
                if (conn != null) {
                    BancoDados.desconectar();
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Método para validar o formato do email
    private boolean isEmailValido(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }
}