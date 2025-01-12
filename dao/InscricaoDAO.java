package dao;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Evento;
import entities.Inscricao;
import entities.Participante;
import enuns.StatusInscricao;

public class InscricaoDAO {

    private Connection conn;

    public InscricaoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean adicionarInscricao(Inscricao inscricao) throws SQLException {
        String query = "INSERT INTO inscricoes (participante_id, evento_id, status_inscricao, presenca_confirmada) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, inscricao.getParticipante().getId());
            stmt.setInt(2, inscricao.getEvento().getId());
            stmt.setString(3, inscricao.getStatusInscricao().name());
            stmt.setBoolean(4, inscricao.isPresencaConfirmada());

            stmt.executeUpdate();
            return true; // Inscrição adicionada com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar inscrição", e);
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
        String query = "UPDATE inscricoes SET presenca_confirmada = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, true);
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
                inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));

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
                inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));
                inscricao.setEvento(eventoDAO.buscarEventoPorId(eventoId));
                inscricao.setParticipante((Participante) usuarioDAO.getUsuarioPorId(rs.getInt("participante_id")));

                inscricoes.add(inscricao);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar inscrições por evento: " + e.getMessage(), e);
        }

        return inscricoes;
    }
}