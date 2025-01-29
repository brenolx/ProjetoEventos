package userinterfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import entities.Evento;
import entities.Inscricao;
import entities.Participante;
import enuns.StatusInscricao;
import services.EventoService;
import services.InscricaoService;

public class TelaEventoDetalhadoParticipante extends JFrame {

    private static final long serialVersionUID = 1L;
    private static final Font LABEL_FONT = new Font("Arial", Font.PLAIN, 12);
    private static final Font TITLE_FONT = new Font("Arial", Font.BOLD, 28);
    private static final Dimension LABEL_DIMENSION = new Dimension(600, 20);
    
    private int eventoId;
    private JLabel tituloLabel, dataLabel, horaLabel, localLabel, duracaoLabel, capacidadeLabel, precoLabel;
    private JLabel descricaoLabel, categoriaLabel, statusLabel; 
    private Evento evento;
	private Participante participante;

    public TelaEventoDetalhadoParticipante(Participante participante, int eventoId) {
    	this.participante = participante;
        this.eventoId = eventoId;
        iniciarComponentes();
        carregarDetalhesEvento();
    }

    private void carregarDetalhesEvento() {
        EventoService eventoService = new EventoService();
        try {
            evento = eventoService.buscarEventoPorId(eventoId);
            atualizarLabelsComDetalhesEvento();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarLabelsComDetalhesEvento() {
        tituloLabel.setText(evento.getTitulo());
        descricaoLabel.setText(evento.getDescricao());
        dataLabel.setText(evento.getDataHora().toLocalDate().toString());
        horaLabel.setText(evento.getDataHora().toLocalTime().toString());
        duracaoLabel.setText(String.valueOf(evento.getDuracaoHoras()));
        localLabel.setText(evento.getLocal());
        capacidadeLabel.setText(String.valueOf(evento.getCapacidadeMaxima()));
        precoLabel.setText(evento.getPreco().toString());
        categoriaLabel.setText(evento.getCategoria().name());
        statusLabel.setText(evento.getStatus().name());
    }

    private void iniciarComponentes() {
        setTitle("Detalhes do Evento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Detalhes do Evento", SwingConstants.CENTER);
        lblTitulo.setFont(TITLE_FONT);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        adicionarInformacaoRotulada(formPanel, gbc, row++, "Título:", tituloLabel = new JLabel());
        configurarLabel(tituloLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Descrição:", descricaoLabel = new JLabel());
        configurarLabel(descricaoLabel);

        // Data, Hora, Duração
        adicionarInformacaoRotulada(formPanel, gbc, row++, "Data:", dataLabel = new JLabel());
        configurarLabel(dataLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Hora:", horaLabel = new JLabel());
        configurarLabel(horaLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Duração (horas):", duracaoLabel = new JLabel());
        configurarLabel(duracaoLabel);

        // Local, Capacidade, Preço, Status
        adicionarInformacaoRotulada(formPanel, gbc, row++, "Local:", localLabel = new JLabel());
        configurarLabel(localLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Capacidade Máxima:", capacidadeLabel = new JLabel());
        configurarLabel(capacidadeLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Preço:", precoLabel = new JLabel());
        configurarLabel(precoLabel);

        // Categoria, Status
        adicionarInformacaoRotulada(formPanel, gbc, row++, "Categoria:", categoriaLabel = new JLabel());
        configurarLabel(categoriaLabel);

        adicionarInformacaoRotulada(formPanel, gbc, row++, "Status:", statusLabel = new JLabel());
        configurarLabel(statusLabel);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnInscrever = new JButton("Inscrever-se");
        btnInscrever.addActionListener(e -> inscreverNoEvento());
        JButton btnCancelar = new JButton("Voltar");
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnInscrever);
        buttonPanel.add(btnCancelar);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
    }

    private void inscreverNoEvento() {
        // Obtém o participante atual (supondo que você tenha um método para isso)
        if (participante == null) {
            JOptionPane.showMessageDialog(this, "Você precisa estar logado para se inscrever.", "Erro", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria a inscrição
        Inscricao inscricao = new Inscricao();
        inscricao.setEvento(evento);
        inscricao.setParticipante(participante);
        inscricao.setDataInscricao(java.time.LocalDateTime.now()); // Define a data da inscrição
        inscricao.setStatusInscricao(StatusInscricao.PENDENTE); // Define o status como PENDENTE

        // Chama o serviço de inscrição para adicionar a nova inscrição
        InscricaoService inscricaoService = new InscricaoService();
      
            boolean sucesso = false;
			try {
				sucesso = inscricaoService.adicionarInscricao(inscricao);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Inscrição realizada com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            }
    }

    private void adicionarInformacaoRotulada(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel infoLabel) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 2;
        panel.add(infoLabel, gbc);
    }

    private void configurarLabel(JLabel label) {
        label.setPreferredSize(LABEL_DIMENSION);
        label.setFont(LABEL_FONT);
    }
}