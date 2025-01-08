package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entities.Evento;
import enuns.CategoriaEvento;
import enuns.StatusEvento;

public class EventoDAO {

    private Connection conn;

    public EventoDAO(Connection conn) {
        this.conn = conn;
    }

    public boolean adicionarEvento(Evento evento) throws SQLException {
        String query = "INSERT INTO eventos (titulo, descricao, data_hora, duracao_horas, local, capacidade_maxima, status, categoria, preco, organizador_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(evento.getDataHora()));
            stmt.setInt(4, evento.getDuracaoHoras());
            stmt.setString(5, evento.getLocal());
            stmt.setInt(6, evento.getCapacidadeMaxima());
            stmt.setString(7, StatusEvento.FECHADO.name()); // Inicia como "fechado"
            stmt.setString(8, evento.getCategoria().name());
            stmt.setBigDecimal(9, evento.getPreco());
            stmt.setInt(10, evento.getOrganizador().getId());

            stmt.executeUpdate();
            return true; // Evento adicionado com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao adicionar evento", e);
        }
    }

    public boolean atualizarEvento(Evento evento) throws SQLException {
        String query = "UPDATE eventos SET titulo = ?, descricao = ?, data_hora = ?, duracao_horas = ?, local = ?, capacidade_maxima = ?, status = ?, categoria = ?, preco = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, evento.getTitulo());
            stmt.setString(2, evento.getDescricao());
            stmt.setTimestamp(3, java.sql.Timestamp.valueOf(evento.getDataHora()));
            stmt.setInt(4, evento.getDuracaoHoras());
            stmt.setString(5, evento.getLocal());
            stmt.setInt(6, evento.getCapacidadeMaxima());
            stmt.setString(7, evento.getStatus().name());
            stmt.setString(8, evento.getCategoria(). name());
            stmt.setBigDecimal(9, evento.getPreco());
            stmt.setInt(10, evento.getId());

            stmt.executeUpdate();
            return true; // Evento atualizado com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao atualizar evento", e);
        }
    }

    public boolean removerEvento(int id) throws SQLException {
        String query = "DELETE FROM eventos WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true; // Evento removido com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao remover evento", e);
        }
    }

    public List<Evento> listarEventos(String status) throws SQLException {
        List<Evento> eventos = new ArrayList<>();
        String query = "SELECT * FROM eventos WHERE status = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, status);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Evento evento = new Evento();
                evento.setId(rs.getInt("id"));
                evento.setTitulo(rs.getString("titulo"));
                evento.setDescricao(rs.getString("descricao"));
                evento.setDataHora(rs.getTimestamp("data_hora").toLocalDateTime());
                evento.setDuracaoHoras(rs.getInt("duracao_horas"));
                evento.setLocal(rs.getString("local"));
                evento.setCapacidadeMaxima(rs.getInt("capacidade_maxima"));
                evento.setStatus(StatusEvento.valueOf(rs.getString("status")));
                evento.setCategoria(CategoriaEvento.valueOf(rs.getString("categoria")));
                evento.setPreco(rs.getBigDecimal("preco"));
                // Aqui você pode buscar o organizador se necessário
                eventos.add(evento);
            }
        } catch (SQLException e) {
            throw new SQLException("Erro ao listar eventos", e);
        }

        return eventos;
    }

    public boolean modificarStatusEvento(int id, String novoStatus) throws SQLException {
        String query = "UPDATE eventos SET status = ? WHERE id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, novoStatus);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            return true; // Status do evento modificado com sucesso
        } catch (SQLException e) {
            throw new SQLException("Erro ao modificar status do evento", e);
        }
    }
}