package services;

import dao.BancoDados;
import dao.UsuarioDAO;
import entities.Participante;
import enuns.TipoUsuario;
import javax.swing.JOptionPane;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class CadastroParticipanteService {

    public boolean cadastrarParticipante(String nome, String email, String senha, LocalDate dataNascimento, String cpf) throws IOException {
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

            // Criar um novo participante
            Participante participante = new Participante();
            participante.setNomeCompleto(nome);
            participante.setEmail(email);
            participante.setSenha(senha);
            participante.setDataNascimento(dataNascimento);
            participante.setCpf(cpf);
            participante.setTipoUsuario(TipoUsuario.PARTICIPANTE); // Define o tipo de usuário

            // Adicionar o participante ao banco de dados
            usuarioDAO.adicionarUsuario(participante);
            JOptionPane.showMessageDialog(null, "Participante cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
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
}