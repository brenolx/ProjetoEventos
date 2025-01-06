package userinterfaces;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.LineBorder;
import entities.Mensagem;
import entities.Servidor;

public class TelaLogin extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField textField;
    private JPasswordField passwordField;

    public TelaLogin() {
        configurarJanela();
        adicionarComponentes();
    }

    private void configurarJanela() {
        setTitle("Sistema de Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(487, 349);
        setLocationRelativeTo(null);
        getContentPane().setBackground(Color.ORANGE);
    }

    private void adicionarComponentes() {
        JPanel painelPrincipal = criarPainelPrincipal();
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);
        setJMenuBar(criarMenuBar());
    }

    private JPanel criarPainelPrincipal() {
        JPanel painelPrincipal = new JPanel(null);
        painelPrincipal.setBackground(Color.BLACK);

        JLabel titulo = criarTitulo("Login de Usuário", 143, 30, 190, 32);
        painelPrincipal.add(titulo);

        JLabel emailLabel = criarLabel("Email:", 83, 99);
        painelPrincipal.add(emailLabel);

        textField = criarCampoTexto(134, 96);
        painelPrincipal.add(textField);

        JLabel senhaLabel = criarLabel("Senha:", 83, 139);
        painelPrincipal.add(senhaLabel);

        passwordField = criarCampoSenha(134, 136);
        painelPrincipal.add(passwordField);

        JButton botaoLogin = criarBotao("Entrar", 83, 180);
        botaoLogin.addActionListener(e -> executarLogin());
        painelPrincipal.add(botaoLogin);

        return painelPrincipal;
    }

    private JMenuBar criarMenuBar() {
        JMenuBar menuBar = new JMenuBar();
        JMenu cadastroMenu = new JMenu("Cadastro");

        JMenuItem cadastrarParticipanteItem = new JMenuItem("Cadastrar Participante");
        cadastrarParticipanteItem.addActionListener(e -> exibirMensagem("Cadastro de Participante iniciado!"));
        cadastroMenu.add(cadastrarParticipanteItem);

        JMenuItem cadastrarAdminItem = new JMenuItem("Cadastrar Admin");
        cadastrarAdminItem.addActionListener(e -> exibirMensagem("Cadastro de Admin iniciado!"));
        cadastroMenu.add(cadastrarAdminItem);

        menuBar.add(cadastroMenu);
        return menuBar;
    }

    private JLabel criarTitulo(String texto, int x, int y, int largura, int altura) {
        JLabel titulo = new JLabel(texto);
        titulo.setBounds(x, y, largura, altura);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titulo.setForeground(Color.WHITE);
        return titulo;
    }

    private JLabel criarLabel(String texto, int x, int y) {
        JLabel label = new JLabel(texto);
        label.setBounds(x, y, 54, 15);
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        label.setForeground(Color.ORANGE);
        return label;
    }

    private JTextField criarCampoTexto(int x, int y) {
        JTextField campoTexto = new JTextField(20);
        campoTexto.setBounds(x, y, 262, 22);
        estilizarCampoTexto(campoTexto);
        return campoTexto;
    }

    private JPasswordField criarCampoSenha(int x, int y) {
        JPasswordField campoSenha = new JPasswordField(20);
        campoSenha.setBounds(x, y, 262, 22);
        estilizarCampoTexto(campoSenha);
        return campoSenha;
    }

    private JButton criarBotao(String texto, int x, int y) {
        JButton botao = new JButton(texto);
        botao.setBounds(x, y, 313, 23);
        estilizarBotao(botao);
        return botao;
    }

    private void estilizarCampoTexto(JTextField campoTexto) {
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setForeground(Color.BLACK);
        campoTexto.setBorder(new LineBorder(Color.GRAY));
    }

    private void estilizarCampoTexto(JPasswordField campoSenha) {
        campoSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoSenha.setBackground(Color.WHITE);
        campoSenha.setForeground(Color.BLACK);
        campoSenha.setBorder(new LineBorder(Color.GRAY));
    }

    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBackground(Color.ORANGE);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setBorder(new LineBorder(Color.ORANGE, 2));
    }

    private void executarLogin() {
        Mensagem mensagem = new Mensagem();
        mensagem.setOperacao("login");
        mensagem.setEmail(textField.getText());
        mensagem.setSenha(new String(passwordField.getPassword()));
        enviarMensagem(mensagem);
    }

    private void enviarMensagem(Mensagem mensagem) {
        Servidor servidor = new Servidor(mensagem);
        servidor.start();
    }

    private void exibirMensagem(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaLogin tela = new TelaLogin();
            tela.setVisible(true);
        });
    }
}
