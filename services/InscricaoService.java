package services;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.swing.JOptionPane;

import dao.BancoDados;
import dao.EventoDAO;
import dao.InscricaoDAO;
import entities.Evento;
import entities.Inscricao;
import enuns.StatusEvento;
import enuns.StatusInscricao;

public class InscricaoService {

    public boolean adicionarInscricao(Inscricao inscricao) throws IOException {
        Connection conn = null;
        try {
            conn = BancoDados.conectar();
            InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
            EventoDAO eventoDAO = new EventoDAO(conn);

            // Verifica se o evento está aberto
            Evento evento = eventoDAO.buscarEventoPorId(inscricao.getEvento().getId());
            if (evento == null) {
                JOptionPane.showMessageDialog(null, "Evento não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!evento.getStatus().equals(StatusEvento.ABERTO)) {
                JOptionPane.showMessageDialog(null, "Inscrições só podem ser feitas para eventos abertos.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Verifica a capacidade máxima
            int totalInscricoes = inscricaoDAO.listarInscricoesPorEvento(evento.getId()).size();
            if (totalInscricoes >= evento.getCapacidadeMaxima()) {
                JOptionPane.showMessageDialog(null, "Capacidade máxima do evento atingida.", "Erro", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Define os dados da inscrição
            inscricao.setStatusInscricao(StatusInscricao.ATIVA);
            inscricao.setDataInscricao(java.time.LocalDateTime.now()); // Define a data da inscrição
            inscricao.setPresencaConfirmada(false); // Inicialmente, a presença não está confirmada

            // Adiciona a inscrição
            return inscricaoDAO.adicionarInscricao(inscricao);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro ao realizar a inscrição: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
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