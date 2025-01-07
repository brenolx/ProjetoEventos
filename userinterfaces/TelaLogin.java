package userinterfaces;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import entities.Mensagem;
import services.GerenciadorServidor;

public class TelaLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;
    private GerenciadorServidor gerenciadorServidor; // Instância do gerenciador

    public TelaLogin() {
        gerenciadorServidor = new GerenciadorServidor(); // Inicializa o gerenciador
        configurarJanela();
        adicionarComponentes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(487, 349);
        setLocationRelativeTo(null);
    }

    private void adicionarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipal();
        getContentPane().add(painelPrincipal);
        setJMenuBar(criarMenuBar());
    }

    private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel(new GridBagLayout());
        painelPrincipal.setBackground(Color.WHITE);  // Define o fundo branco
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // JLabel com fonte maior para "Login"
        JLabel lblTitulo = new JLabel("<html><span style='font-size:20px'>Login</span></html>", SwingConstants.CENTER);

        // Componentes
        JLabel emailLabel = new JLabel("Email:", SwingConstants.CENTER);
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 20));

        JLabel senhaLabel = new JLabel("Senha:", SwingConstants.CENTER);
        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 20));

        JButton botaoLogin = new JButton("Entrar");
        botaoLogin.addActionListener(e -> executarLogin());

        // Adicionando os Componentes ao JFrame
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(lblTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(emailLabel, gbc);
        gbc.gridx = 1;
        painelPrincipal.add(textField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        painelPrincipal.add(senhaLabel, gbc);
        gbc.gridx = 1;
        painelPrincipal.add(passwordField, gbc);

        // Centralizando o botão
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        painelPrincipal.add(botaoLogin, gbc);

        return painelPrincipal;
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu cadastroMenu = new JMenu("Cadastro");

        JMenuItem cadastrarParticipanteItem = new JMenuItem("Cadastrar Participante");
        cadastrarParticipanteItem.addActionListener(e -> abrirTelaCadastroParticipante()); // Chama o método para abrir a tela de cadastro
        cadastroMenu.add(cadastrarParticipanteItem);

        JMenuItem cadastrarAdminItem = new JMenuItem("Cadastrar Admin");
        cadastrarAdminItem.addActionListener(e -> abrirTelaCadastroAdmin()); // Atualizado para abrir a tela de cadastro de admin
        cadastroMenu.add(cadastrarAdminItem);

        menuBar.add(cadastroMenu);
        return menuBar;
    }

    private void executarLogin() {
        Mensagem mensagem = new Mensagem();
        mensagem.setOperacao("login");
        mensagem.setEmail(textField.getText());
        mensagem.setSenha(new String(passwordField.getPassword()));
        enviarMensagem(mensagem);
    }

    private void enviarMensagem(Mensagem mensagem) {
        gerenciadorServidor.enviarMensagem(mensagem); 
    }

    private void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    private void abrirTelaCadastroParticipante() {
        TelaCadastroParticipante telaCadastro = new TelaCadastroParticipante();
        telaCadastro.setVisible(true);
        this.setVisible(false); // Oculta a tela de login
    }

    // Método para abrir a tela de cadastro de administrador
    private void abrirTelaCadastroAdmin() {
        TelaCadastroAdmin telaCadastroAdmin = new TelaCadastroAdmin();
        telaCadastroAdmin.setVisible(true);
        this.setVisible(false); // Oculta a tela de login
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}
