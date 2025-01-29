package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Evento;
import entities.Inscricao;
import entities.Participante;
import entities.Usuario;
import enuns.StatusInscricao;

public class InscricaoDAO {

    private Connection conn;

    public InscricaoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean adicionarInscricao(Inscricao inscricao) throws SQLException {
        String query = "INSERT INTO inscricoes (participante_id, evento_id, status_inscricao) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, inscricao.getParticipante().getId());
            stmt.setInt(2, inscricao.getEvento().getId());
            stmt.setString(3, inscricao.getStatusInscricao().name());

            stmt.executeUpdate();
            return true; // Inscrição adicionada com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar inscrição", e);
        }
    }
    
    public boolean reativarInscricao(int inscricaoId) throws SQLException {
        String query = "UPDATE inscricoes SET status_inscricao = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, StatusInscricao.PENDENTE.name()); // Define o novo status como ATIVA
            stmt.setInt(2, inscricaoId); // Define o ID da inscrição a ser reativada
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0; // Retorna true se a atualização foi bem-sucedida
        } catch (SQLException e) {
            throw new SQLException("Erro ao reativar inscrição: " + e.getMessage(), e);
        }
    }
    
    public Inscricao verificarInscricao(int participanteId, int eventoId) throws SQLException {
        String query = "SELECT * FROM inscricoes WHERE participante_id = ? AND evento_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, participanteId);
            stmt.setInt(2, eventoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Cria uma nova instância de Inscricao
                Inscricao inscricao = new Inscricao();
                inscricao.setId(rs.getInt("id"));
                inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
                inscricao.setDataInscricao(rs.getTimestamp("data_inscricao").toLocalDateTime());

                // Aqui você pode buscar o evento e o participante associados, se necessário
                EventoDAO eventoDAO = new EventoDAO(conn);
                Evento evento = eventoDAO.buscarEventoPorId(eventoId);
                inscricao.setEvento(evento);

                UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
                Participante participante = (Participante) usuarioDAO.getUsuarioPorId(participanteId);
                inscricao.setParticipante(participante);

                return inscricao; // Retorna a inscrição encontrada
            } else {
                return null; // Retorna null se não houver inscrição
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao verificar inscrição: " + e.getMessage(), e);
        }
    }

    public boolean cancelarInscricao(int id) throws SQLException {
        String query = "UPDATE inscricoes SET status_inscricao = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, StatusInscricao.CANCELADA.name());
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true; // Inscrição cancelada com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao cancelar inscrição", e);
        }
    }

    public boolean confirmarPresenca(int id) throws SQLException {
        String query = "UPDATE inscricoes SET status_inscricao = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            // Set the status to "ATIVA" as a string
            stmt.setString(1, StatusInscricao.ATIVA.name()); // Use name() to get the string representation
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true; // Presença confirmada com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao confirmar presença", e);
        }
    }

    public List<Inscricao> listarInscricoes(int participanteId) throws SQLException {
        List<Inscricao> inscricoes = new ArrayList<>();
        String query = "SELECT * FROM inscricoes WHERE participante_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, participanteId);
            ResultSet rs = stmt.executeQuery();

            EventoDAO eventoDAO = new EventoDAO(conn); // Cria uma instância do EventoDAO

            while (rs.next()) {
                Inscricao inscricao = new Inscricao();
                inscricao.setId(rs.getInt("id"));
                inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));

                // Busca o evento associado à inscrição
                int eventoId = rs.getInt("evento_id"); // Supondo que você tenha um campo evento_id na tabela inscricoes
                Evento evento = eventoDAO.buscarEventoPorId(eventoId); // Busca o evento pelo ID
                inscricao.setEvento(evento); // Define o evento na inscrição

                inscricoes.add(inscricao);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar inscrições", e);
        }

        return inscricoes;
    }

    public List<Inscricao> listarInscricoesPorEvento(int eventoId) throws SQLException {
        List<Inscricao> inscricoes = new ArrayList<>();
        String query = "SELECT * FROM inscricoes WHERE evento_id = ?";
        EventoDAO eventoDAO = new EventoDAO(conn);
        UsuarioDAO usuarioDAO = new UsuarioDAO(conn);

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, eventoId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Inscricao inscricao = new Inscricao();
                inscricao.setId(rs.getInt("id"));
                inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
                inscricao.setEvento(eventoDAO.buscarEventoPorId(eventoId));

                // Busca o participante
                int participanteId = rs.getInt("participante_id");
                Usuario usuario = usuarioDAO.getUsuarioPorId(participanteId); // Busca o usuário

                // Verifica o tipo de usuário antes de fazer o cast
                if (usuario instanceof Participante) {
                    inscricao.setParticipante((Participante) usuario); // Cast seguro
                } else {
                    // Trata o caso onde o usuário não é um Participante
                    throw new SQLException("Usuário com ID " + participanteId + " não é um Participante.");
                }

                inscricoes.add(inscricao);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar inscrições por evento: " + e.getMessage(), e);
        }

        return inscricoes;
    }

    public Inscricao buscarInscricaoPorId(int id) throws SQLException {
        String query = "SELECT * FROM inscricoes WHERE id = ?";
        Inscricao inscricao = null;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                inscricao = new Inscricao();
                inscricao.setId(rs.getInt("id"));
                inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));

                // Busca o evento associado à inscrição
                int eventoId = rs.getInt("evento_id");
                EventoDAO eventoDAO = new EventoDAO(conn);
                Evento evento = eventoDAO.buscarEventoPorId(eventoId);
                inscricao.setEvento(evento); // Define o evento na inscrição

                // Busca o participante associado à inscrição
                int participanteId = rs.getInt("participante_id");
                UsuarioDAO usuarioDAO = new UsuarioDAO(conn);
                Participante participante = (Participante) usuarioDAO.getUsuarioPorId(participanteId);
                inscricao.setParticipante(participante); // Define o participante na inscrição
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao buscar inscrição por ID: " + e.getMessage(), e);
        }

        return inscricao; // Retorna a inscrição encontrada ou null se não existir
    }
}