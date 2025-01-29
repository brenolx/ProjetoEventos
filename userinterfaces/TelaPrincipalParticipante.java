package userinterfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import entities.Participante;

public class TelaPrincipalParticipante extends JFrame {

	private static final long serialVersionUID = 1L;
	private Participante participante;

	public TelaPrincipalParticipante(Participante participante) {
		this.participante = participante;
		setTitle("Participante - Principal");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(600, 400);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		getContentPane().add(panel);

		// Menu Lateral
		JPanel menuLateral = new JPanel();
		menuLateral.setLayout(new GridLayout(4, 1, 10, 10));
		menuLateral.setBackground(new Color(60, 63, 65));
		menuLateral.setPreferredSize(new Dimension(150, 0));

		JButton btnEventos = new JButton("Eventos Abertos");
		JButton btnInscricoes = new JButton("Inscrições");
		JButton btnRelatorios = new JButton("Relatórios");
		JButton btnSair = new JButton("Sair");

		menuLateral.add(btnEventos);
		menuLateral.add(btnInscricoes);
		menuLateral.add(btnRelatorios);
		menuLateral.add(btnSair);

		panel.add(menuLateral, BorderLayout.WEST);

		// Área Principal
		JLabel labelBemVindo = new JLabel("Bem-vindo, participante!");
		labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
		labelBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(labelBemVindo, BorderLayout.CENTER);

		// Popup Menu -- criar um para cada novo botão
		JPopupMenu popupMenuRelatorios = new JPopupMenu();

		JMenuItem itemEventosInscritos = new JMenuItem("Eventos Inscritos");
		JMenuItem itemHistoricoParticipacao = new JMenuItem("Histórico/Participação");

		// popup dos
		popupMenuRelatorios.add(itemEventosInscritos);
		popupMenuRelatorios.add(itemHistoricoParticipacao);

		// Adicionando ação de mostrar o menu popup ao passar o mouse
		btnRelatorios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				popupMenuRelatorios.show(btnRelatorios, btnRelatorios.getWidth(), 0);
			}
		});

		// Adicionando ação ao botão "Inscrições"
		btnInscricoes.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				new TelaInscricoesParticipante(TelaPrincipalParticipante.this, participante).setVisible(true);
			}
		});

		btnEventos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// Ao clicar no botão, abre a tela de gerenciamento de eventos
				setVisible(false); // Oculta a tela principal
				try {
					new TelaGerenciamentoEventosParticipante(TelaPrincipalParticipante.this).setVisible(true);
				} catch (IOException ex) {
					ex.printStackTrace();
					JOptionPane.showMessageDialog(TelaPrincipalParticipante.this,
							"Erro ao abrir a tela de eventos: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
				}
			}
		});

		// Adicionar o listener ao item de menu
		itemEventosInscritos.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					new TelaRelatorioParticipante(participante.getId()).setVisible(true);
				});
			}
		});

		// Adicionar o listener ao item de menu Historico eventos passados
		itemHistoricoParticipacao.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(() -> {
					new TelaRelatorioParticipacaoEventos(participante.getId()).setVisible(true);
				});
			}
		});
	}

	public Participante getParticipante() {
		return participante;
	}
}
