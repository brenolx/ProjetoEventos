package userinterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.InscricaoDAO;
import entities.Evento;
import entities.Inscricao;
import entities.Participante;
import enuns.StatusInscricao;
import services.InscricaoService;

public class TelaInscricoesParticipante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tabelaInscricoes;
	private DefaultTableModel model;
	private Participante participante;
	private TelaPrincipalParticipante telaPrincipalParticipante;

	public TelaInscricoesParticipante(TelaPrincipalParticipante telaPrincipalParticipante, Participante participante) {
		this.telaPrincipalParticipante = telaPrincipalParticipante;
		this.participante = participante;
		setTitle("Inscrições");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);
		inicializarComponentes();
		carregarInscricoes();
	}

	private void inicializarComponentes() {
		JPanel panel = new JPanel(new BorderLayout());
		getContentPane().add(panel);

		String[] colunas = { "Id Inscrição", "Nome do Evento", "Status do Evento", "Status da Inscrição" };
		model = new DefaultTableModel(colunas, 0);
		tabelaInscricoes = new JTable(model);
		panel.add(new JScrollPane(tabelaInscricoes), BorderLayout.CENTER);

		JPanel panelBotoes = new JPanel();
		JButton btnVoltar = new JButton("Voltar");
		JButton btnCancelarInscricao = new JButton("Cancelar Inscrição");
		JButton btnConfirmarPresenca = new JButton("Confirmar Presença");

		panelBotoes.add(btnVoltar);
		panelBotoes.add(btnCancelarInscricao);
		panelBotoes.add(btnConfirmarPresenca);
		panel.add(panelBotoes, BorderLayout.SOUTH);

		btnVoltar.addActionListener(e -> voltarParaTelaPrincipal());
		btnCancelarInscricao.addActionListener(e -> cancelarInscricao());
		btnConfirmarPresenca.addActionListener(e -> confirmarPresenca());
	}

	private void carregarInscricoes() {
		try (Connection conn = BancoDados.conectar()) {
			InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
			List<Inscricao> inscricoes = inscricaoDAO.listarInscricoes(participante.getId());

			for (Inscricao inscricao : inscricoes) {
				if (inscricao.getEvento() != null) {
					model.addRow(new Object[] { inscricao.getId(), inscricao.getEvento().getTitulo(),
							inscricao.getEvento().getStatus().name(), inscricao.getStatusInscricao().name() });
				} else {
					model.addRow(
							new Object[] { "Evento não encontrado", "N/A", inscricao.getStatusInscricao().name() });
				}
			}
		} catch (SQLException | IOException e) {
			exibirMensagemErro("Erro ao carregar inscrições: " + e.getMessage());
		} finally {
			try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void voltarParaTelaPrincipal() {
		dispose();
		telaPrincipalParticipante.setVisible(true);
	}

	private void cancelarInscricao() {
		int selectedRow = tabelaInscricoes.getSelectedRow();
		if (selectedRow == -1) {
			exibirMensagemAviso("Por favor, selecione uma inscrição para cancelar.");
			return;
		}

		int inscricaoId = (int) model.getValueAt(selectedRow, 0);
		InscricaoService inscricaoService = new InscricaoService();

		try {
			if (inscricaoService.cancelarInscricao(inscricaoId)) {
				model.setValueAt("CANCELADA", selectedRow, 3);
				exibirMensagemSucesso("Inscrição cancelada com sucesso!");
			} else {
				exibirMensagemErro("Erro ao cancelar a inscrição.");
			}
		} catch (IOException ex) {
			exibirMensagemErro("Erro ao cancelar a inscrição: " + ex.getMessage());
		}
	}

	private void confirmarPresenca() {
		int selectedRow = tabelaInscricoes.getSelectedRow();
		if (selectedRow == -1) {
			exibirMensagemAviso("Por favor, selecione uma inscrição para confirmar presença.");
			return;
		}

		int inscricaoId = (int) model.getValueAt(selectedRow, 0);
		InscricaoService inscricaoService = new InscricaoService();

		try {
			Inscricao inscricao = inscricaoService.buscarInscricaoPorId(inscricaoId);
			if (inscricao == null || inscricao.getEvento() == null) {
				exibirMensagemErro("Inscrição ou evento associado não encontrado.");
				return;
			}

			// Verifica se a presença já está confirmada
			if (inscricao.getStatusInscricao() == StatusInscricao.ATIVA) {
				exibirMensagemAviso("A presença já está confirmada para esta inscrição.");
				return; // Retorna se a presença já estiver confirmada
			}
			
			// Verifica se a presença já está cancelada
			if (inscricao.getStatusInscricao() == StatusInscricao.CANCELADA) {
				exibirMensagemAviso("A sua inscrição nesse evento está cancelada!");
				return; // Retorna se a presença já estiver confirmada
			}

			if (exibirCaixaConfirmacao(inscricao.getEvento()) == JOptionPane.YES_OPTION) {
				if (inscricaoService.confirmarPresenca(inscricaoId)) {
					model.setValueAt("ATIVA", selectedRow, 3);
					exibirMensagemSucesso("Presença confirmada com sucesso!");
				} else {
					exibirMensagemErro("Erro ao confirmar presença.");
				}
			}
		} catch (IOException ex) {
			exibirMensagemErro("Erro ao confirmar presença: " + ex.getMessage());
		}
	}

	private int exibirCaixaConfirmacao(Evento evento) {
		BigDecimal precoEvento = evento.getPreco();

		JPanel confirmationPanel = new JPanel();
		confirmationPanel.setLayout(new BoxLayout(confirmationPanel, BoxLayout.Y_AXIS));
		confirmationPanel.add(new JLabel("Você está prestes a confirmar sua presença no evento:"));
		confirmationPanel.add(new JLabel("<html><b>" + evento.getTitulo() + "</b></html>"));
		confirmationPanel.add(Box.createVerticalStrut(10));
		confirmationPanel.add(new JLabel("Preço do Ingresso: " + precoEvento.toString()));
		confirmationPanel.add(Box.createVerticalStrut(10));
		confirmationPanel.add(new JLabel("Deseja continuar?"));

		return JOptionPane.showConfirmDialog(this, confirmationPanel, "Confirmar Presença", JOptionPane.YES_NO_OPTION,
				JOptionPane.QUESTION_MESSAGE);
	}

	private void exibirMensagemSucesso(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	}

	private void exibirMensagemErro(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
	}

	private void exibirMensagemAviso(String mensagem) {
		JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
	}
}
