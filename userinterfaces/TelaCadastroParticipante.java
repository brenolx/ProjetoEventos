package userinterfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.JOptionPane;
import java.awt.Dimension;

public class TelaCadastroParticipante extends JFrame {

    private static final long serialVersionUID = 1L;

    public TelaCadastroParticipante() {
        setTitle("Cadastro de Usuário");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.BLACK); // Fundo preto

        JPanel cadastroPanel = criarAbaCadastro();
        getContentPane().add(cadastroPanel);
    }

    private JPanel criarAbaCadastro() {
        JPanel cadastroPanel = new JPanel(new GridBagLayout());
        cadastroPanel.setBackground(Color.BLACK); // Fundo preto

        // Configurações do título
        GridBagConstraints gbcTitulo = new GridBagConstraints();
        gbcTitulo.insets = new Insets(10, 10, 10, 10);
        gbcTitulo.gridx = 0;
        gbcTitulo.gridy = 0;
        gbcTitulo.gridwidth = 2;
        gbcTitulo.anchor = GridBagConstraints.CENTER;

        JLabel titulo = new JLabel("Cadastro de Participante");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));  // Alterando a fonte para "Segoe UI"
        titulo.setForeground(Color.WHITE);
        cadastroPanel.add(titulo, gbcTitulo);

        // Campo de nome
        GridBagConstraints gbcNome = new GridBagConstraints();
        gbcNome.insets = new Insets(10, 10, 10, 10);
        gbcNome.gridx = 0;
        gbcNome.gridy = 1;
        gbcNome.anchor = GridBagConstraints.LINE_START;

        JLabel nomeLabel = new JLabel("Nome:");
        nomeLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        nomeLabel.setForeground(Color.ORANGE);
        cadastroPanel.add(nomeLabel, gbcNome);

        GridBagConstraints gbcNomeField = new GridBagConstraints();
        gbcNomeField.insets = new Insets(10, 10, 10, 10);
        gbcNomeField.gridx = 1;
        gbcNomeField.gridy = 1;
        gbcNomeField.fill = GridBagConstraints.HORIZONTAL;

        JTextField nomeField = new JTextField(20);
        estilizarCampoTexto(nomeField);
        cadastroPanel.add(nomeField, gbcNomeField);

        // Campo de email
        GridBagConstraints gbcEmail = new GridBagConstraints();
        gbcEmail.insets = new Insets(10, 10, 10, 10);
        gbcEmail.gridx = 0;
        gbcEmail.gridy = 2;
        gbcEmail.anchor = GridBagConstraints.LINE_START;

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        emailLabel.setForeground(Color.ORANGE);
        cadastroPanel.add(emailLabel, gbcEmail);

        GridBagConstraints gbcEmailField = new GridBagConstraints();
        gbcEmailField.insets = new Insets(10, 10, 10, 10);
        gbcEmailField.gridx = 1;
        gbcEmailField.gridy = 2;
        gbcEmailField.fill = GridBagConstraints.HORIZONTAL;

        JTextField emailField = new JTextField(20);
        estilizarCampoTexto(emailField);
        cadastroPanel.add(emailField, gbcEmailField);

        // Campo de senha
        GridBagConstraints gbcSenha = new GridBagConstraints();
        gbcSenha.insets = new Insets(10, 10, 10, 10);
        gbcSenha.gridx = 0;
        gbcSenha.gridy = 3;
        gbcSenha.anchor = GridBagConstraints.LINE_START;

        JLabel senhaLabel = new JLabel("Senha:");
        senhaLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        senhaLabel.setForeground(Color.ORANGE);
        cadastroPanel.add(senhaLabel, gbcSenha);

        GridBagConstraints gbcSenhaField = new GridBagConstraints();
        gbcSenhaField.insets = new Insets(10, 10, 10, 10);
        gbcSenhaField.gridx = 1;
        gbcSenhaField.gridy = 3;
        gbcSenhaField.fill = GridBagConstraints.HORIZONTAL;

        JPasswordField senhaField = new JPasswordField(20);
        estilizarCampoTexto(senhaField);
        cadastroPanel.add(senhaField, gbcSenhaField);

        // Botão de cadastro
        GridBagConstraints gbcCadastrarButton = new GridBagConstraints();
        gbcCadastrarButton.insets = new Insets( 10, 10, 10, 10);
        gbcCadastrarButton.gridx = 0;
        gbcCadastrarButton.gridy = 4;
        gbcCadastrarButton.gridwidth = 2;
        gbcCadastrarButton.anchor = GridBagConstraints.CENTER;

        JButton cadastrarButton = new JButton("Cadastrar");
        estilizarBotao(cadastrarButton);
        cadastrarButton.addActionListener(e -> cadastrarUsuario(nomeField.getText(), emailField.getText(), new String(senhaField.getPassword())));
        cadastroPanel.add(cadastrarButton, gbcCadastrarButton);

        return cadastroPanel;
    }

    private void estilizarCampoTexto(JTextField campoTexto) {
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Fonte mais moderna
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setForeground(Color.BLACK);
        campoTexto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void estilizarCampoTexto(JPasswordField campoTexto) {
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Fonte mais moderna
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setForeground(Color.BLACK);
        campoTexto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));  // Fonte mais moderna
        botao.setBackground(Color.ORANGE);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createLineBorder(Color.ORANGE, 2));
        botao.setPreferredSize(new Dimension(315, 23));
    }

    private void cadastrarUsuario(String nome, String email, String senha) {
        // A lógica de cadastro do usuário seria implementada aqui
        JOptionPane.showMessageDialog(this, "Usuário cadastrado!");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
        	TelaCadastroParticipante tela = new TelaCadastroParticipante();
            tela.setVisible(true);
        });
    }
}