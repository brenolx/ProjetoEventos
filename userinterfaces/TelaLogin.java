package userinterfaces;

import javax.swing.*;
import java.awt.*;
import services.LoginService;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private LoginService loginService; // Adiciona LoginService

    public TelaLogin() {
        loginService = new LoginService(); // Inicializa o LoginService
        configurarJanela();
        adicionarComponentes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
    }

    private void adicionarComponentes() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("<html><span style='font-size:21px'>Login de Usuário:</span></html>", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        // Email
        JLabel lblEmail = new JLabel("Email:", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblEmail, gbc);

        textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 20));
        gbc.gridx = 1;
        add(textField, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblSenha, gbc);

        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));
        gbc.gridx = 1;
        add(passwordField, gbc);

        // Botão de Login
        JButton btnLogin = new JButton("Entrar");
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnLogin, gbc);

        btnLogin.addActionListener(e -> executarLogin());

        // Adicionando a barra de menu
        setJMenuBar(criarMenuBar());
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu cadastroMenu = new JMenu("Cadastro");

        JMenuItem cadastrarParticipanteItem = new JMenuItem("Cadastrar Participante");
        cadastrarParticipanteItem.addActionListener(e -> abrirTelaCadastroParticipante());
        cadastroMenu.add(cadastrarParticipanteItem);

        JMenuItem cadastrarAdminItem = new JMenuItem("Cadastrar Admin");
        cadastrarAdminItem.addActionListener(e -> abrirTelaCadastroAdmin());
        cadastroMenu.add(cadastrarAdminItem);

        menuBar.add(cadastroMenu);
        return menuBar;
    }

    private void abrirTelaCadastroParticipante() {
        TelaCadastroParticipante telaCadastro = new TelaCadastroParticipante();
        telaCadastro.setVisible(true);
        dispose(); // Fecha a tela de login
    }
    
    private void abrirTelaCadastroAdmin() {
        TelaCadastroAdmin telaCadastroAdmin = new TelaCadastroAdmin();
        telaCadastroAdmin.setVisible(true);
        dispose(); // Fecha a tela de login
    }

    private void executarLogin() {
        String email = textField.getText();
        String senha = new String(passwordField.getPassword());

        try {
            // Usar o LoginService para realizar o login
            if (loginService.logarUsuario(email, senha)) {
                dispose(); // Fecha a tela de login se o login for bem-sucedido
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao realizar login: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}