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
import services.EventoService;

public class TelaEventoDetalhadoParticipante extends JFrame {

    private static final long serialVersionUID = 1L;
    private int eventoId;
    private JLabel tituloLabel, dataLabel, horaLabel, localLabel, duracaoLabel, capacidadeLabel, precoLabel;
    private JLabel descricaoLabel, categoriaLabel, statusLabel; 
    private Evento evento;
    private TelaGerenciamentoEventosParticipante telaGerenciadorEventos;

    public TelaEventoDetalhadoParticipante(TelaGerenciamentoEventosParticipante telaGerenciadorEventos, int eventoId) {
        this.telaGerenciadorEventos = telaGerenciadorEventos;
        this.eventoId = eventoId;
        iniciarComponentes();
        carregarDetalhesEvento();
    }

    private void carregarDetalhesEvento() {
        EventoService eventoService = new EventoService();
        try {
            evento = eventoService.buscarEventoPorId(eventoId);
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
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do evento: " + e.getMessage(), "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void iniciarComponentes() {
        setTitle("Detalhes do Evento");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Detalhes do Evento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 28));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;
        addLabeledInfo(formPanel, gbc, row++, "Título:", tituloLabel = new JLabel());
        tituloLabel.setPreferredSize(new Dimension(600, 20));
        tituloLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        addLabeledInfo(formPanel, gbc, row++, "Descrição:", descricaoLabel = new JLabel());
        descricaoLabel.setFont(new Font("Arial", Font.PLAIN, 12));

        // Data, Hora, Duração
        addLabeledInfo(formPanel, gbc, row++, "Data:", dataLabel = new JLabel());
        dataLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        addLabeledInfo(formPanel, gbc, row++, "Hora:", horaLabel = new JLabel());
        horaLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        addLabeledInfo(formPanel, gbc, row++, "Duração (horas):", duracaoLabel = new JLabel());
        duracaoLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        // Local, Capacidade, Preço, Status
        addLabeledInfo(formPanel, gbc, row++, "Local:", localLabel = new JLabel());
        localLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        addLabeledInfo(formPanel, gbc, row++, "Capacidade Máxima:", capacidadeLabel = new JLabel());
        capacidadeLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        addLabeledInfo(formPanel, gbc, row++, "Preço:", precoLabel = new JLabel());
        precoLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        // Categoria, Status
        addLabeledInfo(formPanel, gbc, row++, "Categoria:", categoriaLabel = new JLabel());
        categoriaLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        addLabeledInfo(formPanel, gbc, row++, "Status:", statusLabel = new JLabel());
        statusLabel.setFont(new Font("Arial", Font.PLAIN, 12)); // Fonte normal

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnCancelar = new JButton("Voltar");
        btnCancelar.addActionListener(e -> dispose());

        buttonPanel.add(btnCancelar);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        pack();
    }

    private void addLabeledInfo(JPanel panel, GridBagConstraints gbc, int row, String label, JLabel infoLabel) {
        gbc.gridx = 0;
        gbc.gridy = row; //row
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1; 
        gbc.gridwidth = 2;
        panel.add(infoLabel, gbc);
    }
}
