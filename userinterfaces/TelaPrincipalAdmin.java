package userinterfaces;

import javax.swing.*;
import entities.Administrador;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;

public class TelaPrincipalAdmin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JLabel labelBemVindo;
    private Administrador adm;

    public TelaPrincipalAdmin(Administrador adm) {
        this.adm = adm;
        iniciarComponentes();
    }

    private void iniciarComponentes() {
        setTitle("Admin - Gerenciamento de Eventos");
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

        JButton btnUsuarios = new JButton("Usuários");
        JButton btnEventos = new JButton("Eventos");
        JButton btnRelatorios = new JButton("Relatórios");
        JButton btnSair = new JButton("Sair");

        // Ação do botão "Usuarios"
        btnUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipalAdmin.this.setVisible(false);
                try {
                    TelaGerenciamentoUsuarios telaGerenciamentoUsuario = new TelaGerenciamentoUsuarios(TelaPrincipalAdmin.this);
                    telaGerenciamentoUsuario.setVisible(true);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Ação do botão "Eventos"
        btnEventos.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaPrincipalAdmin.this.setVisible(false);
                try {
                    TelaGerenciamentoEventosAdmin telaGerenciamentoEventosAdmin = new TelaGerenciamentoEventosAdmin(TelaPrincipalAdmin.this, adm);
                    telaGerenciamentoEventosAdmin.setVisible(true);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        // Ação do botão "Sair"
        btnSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                TelaLogin telaLogin = new TelaLogin();
                telaLogin.setVisible(true);
            }
        });

        // Popup Menu para Relatórios
        JPopupMenu popupMenu = criarMenuPopupRelatorios();
        btnRelatorios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                popupMenu.show(btnRelatorios, btnRelatorios.getWidth(), -3);
            }
        });

        // Adiciona os botões ao menu lateral
        menuLateral.add(btnUsuarios);
        menuLateral.add(btnEventos);
        menuLateral.add(btnRelatorios);
        menuLateral.add(btnSair);

        panel.add(menuLateral, BorderLayout.WEST);

        // Área Principal
        labelBemVindo = new JLabel();
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        labelBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelBemVindo, BorderLayout.CENTER);

        atualizarMensagem();
    }

    private void atualizarMensagem() {
        if (adm != null) {
            labelBemVindo.setText("Bem-vindo, Administrador " + adm.getNomeCompleto() + "!");
        } else {
            labelBemVindo.setText("Bem-vindo, Administrador!");
        }
    }

    private JPopupMenu criarMenuPopupRelatorios() {
        JPopupMenu popupMenu = new JPopupMenu();

        JMenuItem itemRelatorioParticipantes = new JMenuItem("Relatório de Participantes");
        JMenuItem itemEventosPopulares = new JMenuItem("Eventos Mais Populares");
        JMenuItem itemEventosNaoOcorridos = new JMenuItem("Eventos Não Ocorridos");
        JMenuItem itemRelatorioDetalhado = new JMenuItem("Relatório Detalhado");

        // Ação do item "Relatório de Participantes"
        itemRelatorioParticipantes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TelaRelatorioParticipantesEventosPassadosAdmin telaRelatorioParticipantesEventosPassadosAdmin = new TelaRelatorioParticipantesEventosPassadosAdmin(true);
                telaRelatorioParticipantesEventosPassadosAdmin.setVisible(true);
            }
        });

        popupMenu.add(itemRelatorioParticipantes);
        popupMenu.add(itemEventosPopulares);
        popupMenu.add(itemEventosNaoOcorridos);
        popupMenu.add(itemRelatorioDetalhado);

        return popupMenu;
    }
}
