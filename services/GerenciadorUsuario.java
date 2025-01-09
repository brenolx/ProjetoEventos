package services;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JOptionPane;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Usuario;
import userinterfaces.TelaCadastroAdmin;
import userinterfaces.TelaCadastroParticipante;

public class GerenciadorUsuario {

    public void abrirTelaCadastroAdmin() {
        TelaCadastroAdmin telaCadastroAdmin = new TelaCadastroAdmin();
        telaCadastroAdmin.setVisible(true);
    }

    public void abrirTelaCadastroParticipante() {
        TelaCadastroParticipante telaCadastroParticipante = new TelaCadastroParticipante();
        telaCadastroParticipante.setVisible(true);
    }

    public List<Usuario> listarUsuarios() throws SQLException, IOException {
        UsuarioDAO usuarioDAO = new UsuarioDAO(BancoDados.conectar());
        try {
            List<Usuario> usuarios = usuarioDAO.listarUsuarios();
            // Aqui você pode implementar a lógica para exibir os usuários em uma tabela ou lista
            for (Usuario usuario : usuarios) {
                System.out.println(usuario);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao listar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
		return null;
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
		if (sucesso) {
		    JOptionPane.showMessageDialog(null, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
		} else {
		    JOptionPane.showMessageDialog(null, "Erro ao atualizar usuário.", "Erro", JOptionPane.ERROR_MESSAGE);
		}
    }
}