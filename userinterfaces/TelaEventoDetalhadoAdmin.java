package userinterfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import entities.Evento;
import enuns.CategoriaEvento;
import enuns.StatusEvento; // Import the StatusEvento enum
import services.EventoService;

public class TelaEventoDetalhado extends JFrame {

	private static final long serialVersionUID = 1L;
	private int eventoId;
	private JTextField tituloField, dataField, horaField, localField, duracaoField, capacidadeField, precoField;
	private JTextArea descricaoArea;
	private JComboBox<String> categoriaComboBox, statusComboBox; // Added statusComboBox
	private Evento evento;
	private JTable tabelaParticipantes;
	private TelaGerenciamentoEventos telaGerenciadorEventos;

	public TelaEventoDetalhado(TelaGerenciamentoEventos telaGerenciadorEventos, int eventoId) {
		this.telaGerenciadorEventos = telaGerenciadorEventos;
		this.eventoId = eventoId;
		iniciarComponentes();
		carregarDetalhesEvento();
	}

	private void carregarDetalhesEvento() {
		EventoService eventoService = new EventoService();
		try {
			evento = eventoService.buscarEventoPorId(eventoId);
			tituloField.setText(evento.getTitulo());
			descricaoArea.setText(evento.getDescricao());
			dataField.setText(evento.getDataHora().toLocalDate().toString());
			horaField.setText(evento.getDataHora().toLocalTime().toString());
			duracaoField.setText(String.valueOf(evento.getDuracaoHoras()));
			localField.setText(evento.getLocal());
			capacidadeField.setText(String.valueOf(evento.getCapacidadeMaxima()));
			precoField.setText(evento.getPreco().toString());
			categoriaComboBox.setSelectedItem(evento.getCategoria().name());
			statusComboBox.setSelectedItem(evento.getStatus().name()); // Set the status JComboBox
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do evento: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void iniciarComponentes() {
		setTitle("Detalhes do Evento");
		setSize(820, 720);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

		// Cabeçalho
		JLabel lblTitulo = new JLabel("Detalhes do Evento", SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
		lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
		mainPanel.add(lblTitulo);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Formulário
		JPanel formPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		int row = 0;
		// Título
		addLabeledField(formPanel, gbc, row++, "Título:", tituloField = new JTextField(20));
		tituloField.setPreferredSize(new Dimension(600, 30));

		// Descrição
		addLabeledArea(formPanel, gbc, row++, "Descrição:", descricaoArea = new JTextArea(4, 50));
		descricaoArea.setFont(new Font("Arial", Font.PLAIN, 12));

		// Data, Hora e Duração
		JPanel dataHoraDuracaoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		dataField = new JTextField(12);
		horaField = new JTextField(9);
		duracaoField = new JTextField(9); // Duração como JTextField
		dataHoraDuracaoPanel.add(new JLabel("Data (yyyy-MM-dd):"));
		dataHoraDuracaoPanel.add(dataField);
		dataHoraDuracaoPanel.add(new JLabel("Hora (HH:mm):"));
		dataHoraDuracaoPanel.add(horaField);
		dataHoraDuracaoPanel.add(new JLabel("Duração (horas):"));
		dataHoraDuracaoPanel.add(duracaoField);
		gbc.gridx = 0;
		gbc.gridy = row++;
		gbc.gridwidth = 3;
		formPanel.add(dataHoraDuracaoPanel, gbc);

		// Local, Capacidade e Preço
		JPanel localCapacidadePrecoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		localField = new JTextField(30);
		capacidadeField = new JTextField(6);
		precoField = new JTextField(8); // Mover o campo de preço para aqui
		String[] statusOptions = { StatusEvento.ABERTO.name(), StatusEvento.FECHADO.name(),
				StatusEvento.ENCERRADO.name(), StatusEvento.CANCELADO.name() };
		statusComboBox = new JComboBox<>(statusOptions); // JComboBox for status
		localCapacidadePrecoPanel.add(new JLabel("Local:"));
		localCapacidadePrecoPanel.add(localField);
		localCapacidadePrecoPanel.add(new JLabel("Capacidade Máxima:"));
		localCapacidadePrecoPanel.add(capacidadeField);
		localCapacidadePrecoPanel.add(new JLabel("Preço:")); // Adicionar o rótulo de preço
		localCapacidadePrecoPanel.add(precoField); // Adicionar o campo de preço
		gbc.gridx = 0;
		gbc.gridy = row++;
		gbc.gridwidth = 3;
		formPanel.add(localCapacidadePrecoPanel, gbc);

		// Categoria e Status
		JPanel categoriaStatusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		String[] categorias = { "PALESTRA", "WORKSHOP", "CONFERENCIA" };
		categoriaComboBox = new JComboBox<>(categorias);
		categoriaStatusPanel.add(new JLabel("Categoria:"));
		categoriaStatusPanel.add(categoriaComboBox);
		categoriaStatusPanel.add(new JLabel("Status:"));
		categoriaStatusPanel.add(statusComboBox);
		gbc.gridx = 0;
		gbc.gridy = row++;
		gbc.gridwidth = 3;
		formPanel.add(categoriaStatusPanel, gbc);

		mainPanel.add(formPanel);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Tabela de Participantes
		String[] colunas = { "Nome do Participante", "Status da Inscrição" };
		DefaultTableModel model = new DefaultTableModel(colunas, 0);
		tabelaParticipantes = new JTable(model);
		tabelaParticipantes.setRowHeight(25);
		JScrollPane scrollPane = new JScrollPane(tabelaParticipantes);
		scrollPane.setBorder(BorderFactory.createTitledBorder("Participantes"));
		scrollPane.setPreferredSize(new Dimension(700, 250));

		model.addRow(new Object[] { "Participante 1", "Inscrito" });
		model.addRow(new Object[] { "Participante 2", "Confirmado" });
		model.addRow(new Object[] { "Participante 3", "Cancelado" });

		mainPanel.add(scrollPane);
		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

		// Botões
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		JButton btnSalvar = new JButton("Salvar Alterações");
		btnSalvar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				salvarAlteracoes();
			}
		});

		JButton btnCancelar = new JButton("Cancelar");
		btnCancelar.addActionListener(e -> dispose());

		buttonPanel.add(btnSalvar);
		buttonPanel.add(btnCancelar);

		mainPanel.add(buttonPanel);

		add(new JScrollPane(mainPanel));
	}

	private void salvarAlteracoes() {
		try {
			evento.setTitulo(tituloField.getText());
			evento.setDescricao(descricaoArea.getText());
			evento.setDataHora(java.time.LocalDateTime.parse(dataField.getText() + " " + horaField.getText(),
					java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
			evento.setDuracaoHoras(Integer.parseInt(duracaoField.getText()));
			evento.setLocal(localField.getText());
			evento.setCapacidadeMaxima(Integer.parseInt(capacidadeField.getText()));
			evento.setPreco(new BigDecimal(precoField.getText()));
			evento.setCategoria(CategoriaEvento.valueOf((String) categoriaComboBox.getSelectedItem()));
			evento.setStatus(StatusEvento.valueOf((String) statusComboBox.getSelectedItem())); // Set the status

			EventoService eventoService = new EventoService();
			eventoService.atualizarEvento(evento);

			JOptionPane.showMessageDialog(this, "Evento atualizado com sucesso!", "Sucesso",
					JOptionPane.INFORMATION_MESSAGE);
			telaGerenciadorEventos.carregarEventos();
			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void addLabeledField(JPanel panel, GridBagConstraints gbc, int row, String label, JTextField field) {
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 1;
		panel.add(new JLabel(label), gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		panel.add(field, gbc);
	}

	private void addLabeledArea(JPanel panel, GridBagConstraints gbc, int row, String label, JTextArea area) {
		gbc.gridx = 0;
		gbc.gridy = row;
		gbc.gridwidth = 1;
		panel.add(new JLabel(label), gbc);

		gbc.gridx = 1;
		gbc.gridwidth = 2;
		JScrollPane scrollPane = new JScrollPane(area);
		scrollPane.setPreferredSize(new Dimension(600, 80));
		panel.add(scrollPane, gbc);
	}
}
