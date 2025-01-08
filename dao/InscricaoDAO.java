package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Inscricao;
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

            while (rs.next()) {
                Inscricao inscricao = new Inscricao();
                inscricao.setId(rs.getInt("id"));
                // Aqui você pode buscar o evento e o participante se necessário
                inscricao.setStatusInscricao(StatusInscricao.valueOf(rs.getString("status_inscricao")));
                inscricao.setPresencaConfirmada(rs.getBoolean("presenca_confirmada"));
                inscricoes.add(inscricao);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar inscrições", e);
        }

        return inscricoes;
    }
}