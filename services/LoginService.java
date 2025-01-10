package services;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Administrador;
import entities.Participante;
import entities.Usuario;
import enuns.TipoUsuario;
import userinterfaces.TelaPrincipalAdmin;
import userinterfaces.TelaPrincipalParticipante;

import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {

	public boolean logarUsuario(String email, String senha) throws IOException {
	    if (!validarCampos(email, senha)) {
	        return false;
	    }

	    Connection conn = null; // Declarar a conexão aqui
	    try {
	        conn = BancoDados.conectar();
	        if (conn == null) {
	            mostrarErro("Erro: Conexão com o banco de dados não foi estabelecida.");
	            return false;
	        }

	        Usuario usuario = obterUsuarioPorEmail(conn, email);
	        return verificarCredenciais(usuario, senha);
	    } catch (SQLException e) {
	        mostrarErro("Erro ao realizar login: " + e.getMessage());
	        return false;
	    } finally {
	        // Fechar a conexão no bloco finally
	        try {
	            if (conn != null) {
	                BancoDados.desconectar();
	            }
	        } catch (SQLException e) {
	            mostrarErro("Erro ao desconectar do banco de dados: " + e.getMessage());
	        }
	    }
	}

    private boolean validarCampos(String email, String senha) {
        if (isCampoVazio(email, "O campo de email não pode estar vazio.") ||
            isCampoVazio(senha, "O campo de senha não pode estar vazio.") ||
            !isEmailValido(email) ||
            !isSenhaValida(senha)) {
            return false;
        }
        return true;
    }

    private boolean isCampoVazio(String campo, String mensagemErro) {
        if (campo == null || campo.trim().isEmpty()) {
            mostrarErro(mensagemErro);
            return true;
        }
        return false;
    }

    private boolean isSenhaValida(String senha) {
        if (senha.length() < 6) {
            mostrarErro("A senha deve ter pelo menos 6 caracteres.");
            return false;
        }
        return true;
    }

    private Usuario obterUsuarioPorEmail(Connection conn, String email) throws SQLException {
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
        return usuarioDAO.getUsuarioPorEmail(email);
    }

    private boolean verificarCredenciais(Usuario usuario, String senha) {
        if (usuario == null) {
            mostrarErro("Usuário não encontrado. Verifique o email inserido.");
            return false; // Login falhou
        }

        if (usuario.getSenha().equals(senha)) {
            abrirTelaPrincipal(usuario);
            return true; // Login bem-sucedido
        } else {
            mostrarErro("Email ou senha incorretos.");
            return false; // Login falhou
        }
    }

    private void abrirTelaPrincipal(Usuario usuario) {
        if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
            Administrador admin = (Administrador) usuario; // Cast para Administrador
            TelaPrincipalAdmin telaAdmin = new TelaPrincipalAdmin(admin);
            telaAdmin.setVisible(true);
        } else if (usuario.getTipoUsuario() == TipoUsuario.PARTICIPANTE) {
            Participante participante = (Participante) usuario; // Cast para Administrador
            TelaPrincipalParticipante telaParticipante = new TelaPrincipalParticipante(participante);
            telaParticipante.setVisible(true);
        }
        
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(null, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private boolean isEmailValido(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        if (!email.matches(emailRegex)) {
            mostrarErro("O email fornecido não é válido."); // Mensagem de feedback para email inválido
        }
        return email.matches(emailRegex);
    }
}