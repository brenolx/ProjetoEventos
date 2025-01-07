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
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class CadastroParticipanteService {

    public boolean cadastrarParticipante(String nome, String email, String senha, LocalDate dataNascimento, String cpf) throws IOException {
        // Verificar se todos os campos obrigatórios estão preenchidos
        if (nome == null || nome.trim().isEmpty() ||
            email == null || email.trim().isEmpty() ||
            senha == null || senha.trim().isEmpty() ||
            dataNascimento == null || // Verifica se a data é nula
            cpf == null || cpf.trim().isEmpty()) {
            
            JOptionPane.showMessageDialog(null, "Por favor, preencha todos os campos obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return false; // Retorna false se algum campo obrigatório não estiver preenchido
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

            // Criar um novo participante
            Participante participante = new Participante();
            participante.setNomeCompleto(nome);
            participante.setEmail(email);
            participante.setSenha(senha);
            participante.setDataNascimento(dataNascimento); // Data já está no formato LocalDate
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

    // Converte uma string para data no formato dd/MM/yyyy
    private LocalDate parseDataNascimento(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            return null; // Retorna null se a data for inválida
        }
    }
}