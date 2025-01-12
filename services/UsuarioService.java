package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;
import userinterfaces.TelaCadastroAdmin;
import userinterfaces.TelaCadastroParticipante;

public class UsuarioService {

    public void abrirTelaCadastroAdmin() {
        TelaCadastroAdmin telaCadastroAdmin = new TelaCadastroAdmin();
        telaCadastroAdmin.setVisible(true);
    }

    public void abrirTelaCadastroParticipante() {
        TelaCadastroParticipante telaCadastroParticipante = new TelaCadastroParticipante();
        telaCadastroParticipante.setVisible(true);
    }

    public List<Usuario> listarUsuarios() throws SQLException, IOException {
        Connection conn = null;
        List<Usuario> usuarios = new ArrayList<>();
        try {
            conn = BancoDados.conectar(); // Ensure this method connects properly
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            usuarios = usuarioDAO.listarUsuarios(); // Ensure this method returns a valid list
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar usuários: " + e.getMessage(), e);
        } finally {
            if (conn != null) {
                BancoDados.desconectar();
            }
        }
        return usuarios; // Ensure this is not null
    }
    
    public Usuario getUsuarioPorId(int usuarioId) throws SQLException, IOException {
        Connection conn = null;
        Usuario usuario = null;
        try {
            // Conectar ao banco de dados
            conn = BancoDados.conectar();
            if (conn == null) {
                throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
            }

            // Criar uma instância do UsuarioDAO
            UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
            // Chamar o método getUsuarioPorId
            usuario = usuarioDAO.getUsuarioPorId(usuarioId);
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar usuário pelo ID: " + e.getMessage(), e);
        } finally {
            // Desconectar do banco de dados
            if (conn != null) {
                BancoDados.desconectar();
            }
        }
        return usuario; // Retorna o usuário encontrado
    }

    public void excluirUsuario(int usuarioId) throws SQLException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO(BancoDados.conectar());
        boolean sucesso = usuarioDAO.removerUsuario(usuarioId);
		if (sucesso) {
		    JOptionPane.showMessageDialog(null, "Usuário excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    JOptionPane.showMessageDialog(null, "Erro ao excluir usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }

    public void atualizarUsuario(Usuario usuario) throws SQLException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO(BancoDados.conectar());
        boolean sucesso = usuarioDAO.atualizarUsuario(usuario);
		if (!sucesso) {
		    JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
}