package userinterfaces;

import javax.swing.*;
import java.awt.*;

public class TelaCadastroAdmin extends JFrame {

    public TelaCadastroAdmin() {
        // Configurações da Janela
        setTitle("Cadastro de Administradores");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Layout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // JLabel com fonte maior para "Cadastre-se"
        JLabel lblTitulo = new JLabel("<html><span style='font-size:21px'>Cadastre-se:</span></html>", SwingConstants.CENTER);

        // Componentes
        JLabel lblNomeCompleto = new JLabel("Nome Completo:", SwingConstants.CENTER);
        JTextField txtNomeCompleto = new JTextField();
        txtNomeCompleto.setPreferredSize(new Dimension(150, 20)); // Diminuído para 150

        JLabel lblEmail = new JLabel("E-mail:", SwingConstants.CENTER);
        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(150, 20)); // Diminuído para 150

        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(150, 20)); // Diminuído para 150

        JLabel lblCargo = new JLabel("Cargo:", SwingConstants.CENTER);
        JTextField txtCargo = new JTextField();
        txtCargo.setPreferredSize(new Dimension(150, 20)); // Diminuído para 150

        JLabel lblDataContratacao = new JLabel("Data da Contratação:", SwingConstants.CENTER);
        JTextField txtDataContratacao = new JTextField();
        txtDataContratacao.setPreferredSize(new Dimension(150, 25)); // Diminuído para 150

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.setPreferredSize(new Dimension(200, 30)); // Largura reduzida e altura 30
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.setPreferredSize(new Dimension(80, 30)); // 1/3 da largura do botão "Salvar", altura igual

        // Adicionando os Componentes ao JFrame em duas colunas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblNomeCompleto, gbc);
        gbc.gridx = 1;
        add(txtNomeCompleto, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblEmail, gbc);
        gbc.gridx = 1;
        add(txtEmail, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblSenha, gbc);
        gbc.gridx = 1;
        add(txtSenha, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblCargo, gbc);
        gbc.gridx = 1;
        add(txtCargo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblDataContratacao, gbc);
        gbc.gridx = 1;
        add(txtDataContratacao, gbc);

        // Adicionando os botões "Voltar" e "Salvar"
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnVoltar, gbc);
        gbc.gridx = 1;
        add(btnSalvar, gbc);

        btnSalvar.addActionListener(e -> salvarDados());
        btnVoltar.addActionListener(e -> voltarParaLogin());
    }

    // Método para salvar dados (exemplo)
    private void salvarDados() {
        // Lógica para salvar os dados
        JOptionPane.showMessageDialog(this, "Dados salvos com sucesso!");
    }

    // Método para voltar à tela de login
    private void voltarParaLogin() {
        // Supondo que você tem uma classe TelaLogin
        TelaLogin telaLogin = new TelaLogin();
        telaLogin.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        TelaCadastroAdmin frame = new TelaCadastroAdmin();
        frame.setVisible(true);
    }
}
