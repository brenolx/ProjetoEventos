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

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;

public class TelaPrincipalParticipante extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaPrincipalParticipante() {
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

//        JButton btnUsuarios = new JButton("Usuários");
        JButton btnEventos = new JButton("Eventos");
        JButton btnInscricoes = new JButton("Inscrições");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

//        menuLateral.add(btnUsuarios);
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
        JPopupMenu popupMenuEventos = new JPopupMenu();
        
        JMenuItem itemEventosInscritos = new JMenuItem("Eventos Inscritos");
        JMenuItem itemHistoricoParticipacao = new JMenuItem("Histórico/Participação");
        JMenuItem itemEventosDisponiveis = new JMenuItem("Eventos Disponívels");
        
        // popup dos
        popupMenuRelatorios.add(itemEventosInscritos);
        popupMenuRelatorios.add(itemHistoricoParticipacao);
        popupMenuEventos.add(itemEventosDisponiveis);
        
        

        // Adicionando ação de mostrar o menu popup ao passar o mouse
        btnRelatorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                popupMenuRelatorios.show(btnRelatorios, btnRelatorios.getWidth(), 0);
            }
        });
        
        btnEventos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                popupMenuEventos.show(btnEventos, btnEventos.getWidth(), 0);
                
            }
        });
        
     // Adicionando ação ao botão "Inscrições" 
        btnInscricoes.addActionListener(new ActionListener() { 
        	@Override public void actionPerformed(ActionEvent e) { 
        		new TelaInscricoesParticipante().setVisible(true); } 
        	});
    }
}










