package userinterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import dao.BancoDados;
import dao.InscricaoDAO;
import entities.Evento;
import entities.Inscricao;
import enuns.StatusEvento;

public class TelaRelatorioParticipacaoEventos extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable table;
	private DefaultTableModel model;

	public TelaRelatorioParticipacaoEventos(int participanteId) {
		setTitle("Relatório de Participações em Eventos Passados");
		setSize(1000, 600);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		// Adiciona o título
		JLabel titulo = new JLabel("Relatório detalhado de Participações em Eventos Passados", SwingConstants.CENTER);
		titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
		add(titulo, BorderLayout.NORTH);
		// Configura o modelo da tabela
		model = new DefaultTableModel() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		table = new JTable(model);
		// Adiciona as colunas com títulos em negrito
		model.addColumn("<html><b>Título</b></html>");
		model.addColumn("<html><b>Descrição</b></html>");
		model.addColumn("<html><b>Data</b></html>");
		model.addColumn("<html><b>Hora</b></html>");
		model.addColumn("<html><b>Duração (Horas)</b></html>");
		model.addColumn("<html><b>Local</b></html>");
		model.addColumn("<html><b>Capacidade Máxima</b></html>");
		model.addColumn("<html><b>Preço R$</b></html>");
		model.addColumn("<html><b>Categoria</b></html>");
		model.addColumn("<html><b>Status Evento</b></html>");
		// Define larguras fixas para as colunas
		int[] columnWidths = { 80, 120, 70, 50, 100, 80, 120, 70, 100, 80 };
		for (int i = 0; i < columnWidths.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
		}
		// Centraliza os títulos e conteúdos das colunas
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(JLabel.CENTER);
		table.setDefaultRenderer(Object.class, renderer);
		// Popula a tabela com dados
		Connection conn = null;
		try {
			conn = BancoDados.conectar();
			InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
			List<Inscricao> inscricoes = inscricaoDAO.listarInscricoes(participanteId);

			for (Inscricao inscricao : inscricoes) {
				Evento evento = inscricao.getEvento();
				if (evento.getStatus() == StatusEvento.ENCERRADO) {
					model.addRow(new Object[] { evento.getTitulo(), evento.getDescricao(),
							evento.getDataHora().toLocalDate(), evento.getDataHora().toLocalTime(),
							evento.getDuracaoHoras(), evento.getLocal(), evento.getCapacidadeMaxima(),
							evento.getPreco(), evento.getCategoria().name(), evento.getStatus().name() });
				}
			}
		} catch (SQLException | IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao buscar participações: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		} finally {
			try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				
				e.printStackTrace();
			}
		}
		// Adiciona a tabela à tela com bordas
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBorder(new EmptyBorder(10, 10, 0, 10));
		add(scrollPane, BorderLayout.CENTER);
		// Cria e adiciona o botão Voltar
		JButton btnVoltar = new JButton("Voltar");
		btnVoltar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		// Cria e adiciona o botão Exportar
		JButton btnExportar = new JButton("Exportar .xls");
		btnExportar.setBackground(Color.getHSBColor(0.33f, 1f, 0.5f));
		btnExportar.setForeground(Color.WHITE);
		btnExportar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exportarParaXLS();
			}
		});
		// Painel para os botões
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		panel.add(btnVoltar);
		panel.add(btnExportar);
		// Adiciona o painel com os botões à tela
		add(panel, BorderLayout.SOUTH);
	}

	private void exportarParaXLS() {
		if (model.getRowCount() == 0) {
			JOptionPane.showMessageDialog(this, "A tabela está vazia. Não há dados para exportar.", "Aviso",
					JOptionPane.WARNING_MESSAGE);
			return;
		}
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Salvar como");
		fileChooser.setSelectedFile(new File("relatorio.xls"));
		int userSelection = fileChooser.showSaveDialog(this);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			File fileToSave = fileChooser.getSelectedFile();
			try (FileWriter fw = new FileWriter(fileToSave); BufferedWriter bw = new BufferedWriter(fw)) {

				// Cria a linha do cabeçalho
				for (int i = 0; i < model.getColumnCount(); i++) {
					bw.write(model.getColumnName(i) + "\t");
				}
				bw.write("\n");
				// Popula a planilha com dados
				for (int i = 0; i < model.getRowCount(); i++) {
					for (int j = 0; j < model.getColumnCount(); j++) {
						Object value = model.getValueAt(i, j);
						if (value != null) {
							bw.write(value.toString());
						}
						bw.write("\t");
					}
					bw.write("\n");
				}
				bw.flush();
				JOptionPane.showMessageDialog(this, "Relatório foi salvo com sucesso!", "Sucesso",
						JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + e.getMessage(), "Erro",
						JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		}
	}
}