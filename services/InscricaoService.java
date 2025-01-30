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

	public Inscricao buscarInscricaoPorId(int id) throws IOException {
		Connection conn = null;
		try {
			// Conectar ao banco de dados
			conn = BancoDados.conectar();
			if (conn == null) {
				throw new SQLException("Conexão com o banco de dados não foi estabelecida.");
			}

			// Criar uma instância do InscricaoDAO
			InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
			return inscricaoDAO.buscarInscricaoPorId(id); // Chama o método do DAO para buscar a inscrição
		} catch (SQLException e) {
			throw new IOException("Erro ao buscar inscrição: " + e.getMessage(), e);
		} finally {
			// Desconectar do banco de dados
			try {
				if (conn != null) {
					BancoDados.desconectar();
				}
			} catch (SQLException e) {
				throw new IOException("Erro ao desconectar do banco de dados: " + e.getMessage(), e);
			}
		}
	}

	// Método para verificar o status da inscrição
	public boolean verificarStatusInscricao(Inscricao inscricao) {
		// Verifica se a inscrição já está ativa
		if (inscricao.getStatusInscricao() == StatusInscricao.ATIVA) {
			JOptionPane.showMessageDialog(null, "A inscrição já está confirmada.", "Aviso",
					JOptionPane.INFORMATION_MESSAGE);
			return true; // Retorna true se a inscrição já estiver ativa
		}
		return false; // Retorna false se a inscrição não estiver ativa
	}

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
				JOptionPane.showMessageDialog(null, "Inscrições só podem ser feitas para eventos abertos.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			// Verifica se o participante já está inscrito
			Inscricao inscricaoExistente = inscricaoDAO.verificarInscricao(inscricao.getParticipante().getId(),
					evento.getId());

			if (inscricaoExistente != null) {
				StatusInscricao status = inscricaoExistente.getStatusInscricao();

				// Verifica o status da inscrição
				switch (status) {
				case ATIVA:
					JOptionPane.showMessageDialog(null,
							"Você já está inscrito e sua presença está confirmada neste evento.", "Inscrição",
							JOptionPane.INFORMATION_MESSAGE);
					return false; // Retorna se já estiver inscrito e ativo

				case CANCELADA:
					try {
						inscricaoDAO.reativarInscricao(inscricaoExistente.getId());
						JOptionPane.showMessageDialog(null, "Sua inscrição que estava cancelada foi ativada novamente.",
								"Inscrição", JOptionPane.INFORMATION_MESSAGE);
					} catch (SQLException e) {
						JOptionPane.showMessageDialog(null, "Erro ao reativar a inscrição: " + e.getMessage(), "Erro",
								JOptionPane.ERROR_MESSAGE);
					}
					break;

				case PENDENTE:
					JOptionPane.showMessageDialog(null, "Sua inscrição está pendente. Confirme na aba Inscrições.",
							"Inscrição", JOptionPane.INFORMATION_MESSAGE);
					return false; // Retorna se a inscrição está pendente
				}
			}

			// Verifica a capacidade máxima
			int totalInscricoes = inscricaoDAO.listarInscricoesPorEvento(evento.getId()).size();
			if (totalInscricoes >= evento.getCapacidadeMaxima()) {
				JOptionPane.showMessageDialog(null, "Capacidade máxima do evento atingida.", "Erro",
						JOptionPane.ERROR_MESSAGE);
				return false;
			}

			// Define os dados da inscrição
			inscricao.setStatusInscricao(StatusInscricao.PENDENTE);
			inscricao.setDataInscricao(java.time.LocalDateTime.now()); // Define a data da inscrição

			// Adiciona a inscrição
			return inscricaoDAO.adicionarInscricao(inscricao);
		} catch (SQLException e) {
			return false;
		} finally {
			// Desconectar do banco de dados
			try {
				if (conn != null) {
					BancoDados.desconectar();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	// Método para cancelar uma inscrição
	public boolean cancelarInscricao(int inscricaoId) throws IOException {
	    Connection conn = null;
	    try {
	        // Conectar ao banco de dados
	        conn = BancoDados.conectar();
	        InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
	        
	        // Verificar a inscrição antes de cancelar
	        Inscricao inscricao = inscricaoDAO.buscarInscricaoPorId(inscricaoId);
	        if (inscricao == null || inscricao.getEvento() == null) {
	            JOptionPane.showMessageDialog(null, "Inscrição ou evento associado não encontrado.", "Erro",
	                    JOptionPane.ERROR_MESSAGE);
	            return false; // Retorna false se a inscrição ou evento não existir
	        }

	        // Verificar o status do evento
	        if (!inscricao.getEvento().getStatus().equals(StatusEvento.ABERTO)) {
	            JOptionPane.showMessageDialog(null, "Não é possível cancelar a inscrição, o evento não está aberto.", "Erro",
	                    JOptionPane.ERROR_MESSAGE);
	            return false; // Retorna false se o evento não estiver aberto
	        }

	        // Chama o método do DAO para cancelar a inscrição
	        return inscricaoDAO.cancelarInscricao(inscricaoId);
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(null, "Erro ao cancelar a inscrição: " + e.getMessage(), "Erro",
	                JOptionPane.ERROR_MESSAGE);
	        return false; // Retorna false em caso de erro
	    } finally {
	        // Desconectar do banco de dados
	        try {
	            if (conn != null) {
	                BancoDados.desconectar();
	            }
	        } catch (SQLException e) {
	            JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
	                    JOptionPane.ERROR_MESSAGE);
	        }
	    }
	}

	// Método para confirmar presença
	public boolean confirmarPresenca(int inscricaoId) throws IOException {
		Connection conn = null;
		try {
			// Conectar ao banco de dados
			conn = BancoDados.conectar();
			InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
			return inscricaoDAO.confirmarPresenca(inscricaoId); // Chama o método do DAO para confirmar a presença
		} catch (SQLException e) {
			JOptionPane.showMessageDialog(null, "Erro ao confirmar presença: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			return false; // Retorna false em caso de erro
		} finally {
			// Desconectar do banco de dados
			try {
				if (conn != null) {
					BancoDados.desconectar();
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, "Erro ao desconectar do banco de dados: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}
}