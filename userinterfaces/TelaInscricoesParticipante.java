package userinterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import dao.BancoDados;
import dao.InscricaoDAO;
import entities.Inscricao;
import entities.Participante;

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
		// Layout principal
		JPanel panel = new JPanel(new BorderLayout());
		getContentPane().add(panel);

		// Tabela de Inscrições
		String[] colunas = { "Nome do Evento", "Status do Evento", "Status da Inscrição" };
		model = new DefaultTableModel(colunas, 0);
		tabelaInscricoes = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(tabelaInscricoes);
		panel.add(scrollPane, BorderLayout.CENTER);

		// Painel de Botões
		JPanel panelBotoes = new JPanel();
		JButton btnVoltar = new JButton("Voltar");
		JButton btnCancelarInscricao = new JButton("Cancelar Inscrição");
		JButton btnConfirmarPresenca = new JButton("Confirmar Presença");

		panelBotoes.add(btnVoltar);
		panelBotoes.add(btnCancelarInscricao);
		panelBotoes.add(btnConfirmarPresenca);
		panel.add(panelBotoes, BorderLayout.SOUTH);

		// Ação do Botão Voltar
		btnVoltar.addActionListener(e -> {
			dispose(); // Fecha a tela atual
			telaPrincipalParticipante.setVisible(true);
		});

		// Ação do Botão Cancelar Inscrição
		btnCancelarInscricao.addActionListener(e -> {
			// Lógica para cancelar inscrição
			int selectedRow = tabelaInscricoes.getSelectedRow();
			if (selectedRow != -1) {
				model.setValueAt("Cancelado", selectedRow, 2); // Atualiza o status da inscrição para "Cancelado"
			}
		});

		// Ação do Botão Confirmar Presença
		btnConfirmarPresenca.addActionListener(e -> {
			// Lógica para confirmar presença
			int selectedRow = tabelaInscricoes.getSelectedRow();
			if (selectedRow != -1) {
				model.setValueAt("Presença Confirmada", selectedRow, 2); // Atualiza o status da inscrição para
																			// "Presença Confirmada"
			}
		});
	}

	private void carregarInscricoes() {
	    try (Connection conn = BancoDados.conectar()) {
	        InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
	        List<Inscricao> inscricoes = inscricaoDAO.listarInscricoes(participante.getId());

	        for (Inscricao inscricao : inscricoes) {
	        	System.out.println(inscricao);
	            if (inscricao.getEvento() != null) {
	                model.addRow(new Object[] {
	                    inscricao.getEvento().getTitulo(),
	                    inscricao.getEvento().getStatus().name(),
	                    inscricao.getStatusInscricao().name()
	                });
	            } else {
	                // Tratar caso onde o evento é null
	                model.addRow(new Object[] {
	                    "Evento não encontrado",
	                    "N/A",
	                    inscricao.getStatusInscricao().name()
	                });
	            }
	        }
	    } catch (SQLException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao carregar inscrições: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Erro de I/O: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	    }
	    finally {
	    	try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
}