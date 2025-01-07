package services;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;
import enuns.TipoUsuario;
import userinterfaces.TelaGerenciamentoEventos;
import javax.swing.JOptionPane;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class LoginService {

    public boolean logarUsuario(String email, String senha) throws IOException {
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

            // Verificar se o usuário foi encontrado e se a senha está correta
            if (usuario != null && usuario.getSenha().equals(senha)) {
                // Verificar se o usuário é um administrador
                if (usuario.getTipoUsuario() == TipoUsuario.ADMINISTRADOR) {
                    // Abrir a tela de gerenciamento de eventos
                    TelaGerenciamentoEventos telaAdmin = new TelaGerenciamentoEventos();
                    telaAdmin.setVisible(true);
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
}