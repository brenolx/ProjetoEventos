package services;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Administrador;
import enuns.TipoUsuario;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroAdminService {

    public boolean cadastrarAdmin(String nome, String email, String senha, String cargo, LocalDate dataContratacao) throws IOException {
        // Verificar se todos os campos obrigatórios estão preenchidos
        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            senha == null || senha.trim().isEmpty() ||
            cargo == null || cargo.trim().isEmpty() ||
            dataContratacao == null) {
            
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false se algum campo obrigatório não estiver preenchido
        }

        // Validar o formato do email
        if (!isEmailValido(email)) {
            JOptionPane.showMessageDialog(null, "O email fornecido não é válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false se o email for inválido
        }

        // Validar o comprimento da senha
        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(null, "A senha deve ter pelo menos 6 caracteres.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false se a senha for muito curta
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
            // Verificar se o email já existe
            if (!usuarioDAO.validarEmail(email)) {
                JOptionPane.showMessageDialog(null, "Email já cadastrado.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Criar um novo administrador
            Administrador admin = new Administrador();
            admin.setNomeCompleto(nome);
            admin.setEmail(email);
            admin.setSenha(senha);
            admin.setCargo(cargo);
            admin.setDataContratacao(dataContratacao); // Data já está no formato LocalDate
            admin.setTipoUsuario(TipoUsuario.ADMINISTRADOR); // Define o tipo de usuário

            // Adicionar o administrador ao banco de dados
            usuarioDAO.adicionarUsuario(admin);
            JOptionPane.showMessageDialog(null, "Administrador cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
            return true; // Cadastro bem-sucedido
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar cadastro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
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