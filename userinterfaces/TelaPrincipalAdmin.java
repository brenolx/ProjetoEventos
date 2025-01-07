package userinterfaces;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipalAdmin extends JFrame {

    private static final long serialVersionUID = 1L;
    private String nome; // Alterado para String para melhor uso
    private JLabel labelBemVindo; // Mover o JLabel para ser um atributo da classe

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
        labelBemVindo = new JLabel("Bem-vindo, Administrador " + this.nome + "!");
        labelBemVindo.setFont(new Font("Arial", Font.BOLD, 20));
        labelBemVindo.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(labelBemVindo, BorderLayout.CENTER);
    }

    public void setNome(String nome) {
 
        this.nome = nome;
        atualizarMensagem(); // Atualiza a mensagem de boas-vindas
    }

    private void atualizarMensagem() {
        // Atualiza o JLabel com a nova mensagem
        labelBemVindo.setText("Bem-vindo, Administrador " + this.nome + "!");
    }
}