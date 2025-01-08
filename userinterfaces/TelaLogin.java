package userinterfaces;

import javax.swing.*;
import java.awt.*;
import entities.Mensagem;
import services.GerenciadorServidor;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private GerenciadorServidor gerenciadorServidor;

    public TelaLogin() {
        gerenciadorServidor = new GerenciadorServidor();
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
        this.setVisible(false); // Oculta a tela de login
    }
    
    private void abrirTelaCadastroAdmin() {
        TelaCadastroAdmin telaCadastroAdimin = new TelaCadastroAdmin();
        telaCadastroAdimin.setVisible(true);
        this.setVisible(false); // Oculta a tela de login
    }


    private void executarLogin() {
        Mensagem mensagem = new Mensagem();
        mensagem.setOperacao("login");
        mensagem.setEmail(textField.getText());
        mensagem.setSenha(new String(passwordField.getPassword()));
        enviarMensagem(mensagem);
        this.setVisible(false);
    }

    private void enviarMensagem(Mensagem mensagem) {
        gerenciadorServidor.enviarMensagem(mensagem);
    }

    public void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}