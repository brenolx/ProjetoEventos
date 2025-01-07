package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class TelaPrincipalAdmin extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaPrincipalAdmin() {
        setTitle("Admin - Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        getContentPane().add(panel);

        // Menu Lateral
        JPanel menuLateral = new JPanel();
        menuLateral.setLayout(new GridLayout(5, 1, 10, 10));
        menuLateral.setBackground(new Color(60, 63, 65));
        menuLateral.setPreferredSize(new Dimension(150, 0));

        JButton btnUsuarios = new JButton("Usuários");
        JButton btnEventos = new JButton("Eventos");
        JButton btnInscricoes = new JButton("Inscrições");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

        menuLateral.add(btnUsuarios);
        menuLateral.add(btnEventos);
        menuLateral.add(btnInscricoes);
        menuLateral.add(btnRelatorios);
        menuLateral.add(btnSair);

        panel.add(menuLateral, BorderLayout.WEST);

        // Área Principal
        JLabel labelBemVindo = new JLabel("Bem-vindo, Administrador!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        labelBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelBemVindo, BorderLayout.CENTER);

        // Popup Menu
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem itemRelatorioParticipantes = new JMenuItem("Relatório de Participantes");
        JMenuItem itemEventosPopulares = new JMenuItem("Eventos Mais Populares");
        JMenuItem itemEventosNaoOcorridos = new JMenuItem("Eventos Não Ocorridos");
        JMenuItem itemRelatorioDetalhado = new JMenuItem("Relatório Detalhado");

        popupMenu.add(itemRelatorioParticipantes);
        popupMenu.add(itemEventosPopulares);
        popupMenu.add(itemEventosNaoOcorridos);
        popupMenu.add(itemRelatorioDetalhado);

        // Adicionando ação de mostrar o menu popup ao passar o mouse
        btnRelatorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                popupMenu.show(btnRelatorios, btnRelatorios.getWidth(), 0);
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaPrincipalAdmin().setVisible(true);
        });
    }
}
