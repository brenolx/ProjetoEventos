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
        txtNomeCompleto.setPreferredSize(new Dimension(200, 20));

        JLabel lblEmail = new JLabel("E-mail:", SwingConstants.CENTER);
        JTextField txtEmail = new JTextField();
        txtEmail.setPreferredSize(new Dimension(200, 20));

        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        JPasswordField txtSenha = new JPasswordField();
        txtSenha.setPreferredSize(new Dimension(200, 20));

        JLabel lblCargo = new JLabel("Cargo:", SwingConstants.CENTER);
        JTextField txtCargo = new JTextField();
        txtCargo.setPreferredSize(new Dimension(200, 20));

        JLabel lblDataContratacao = new JLabel("Data da Contratação:", SwingConstants.CENTER);
        JTextField txtDataContratacao = new JTextField();
        txtDataContratacao.setPreferredSize(new Dimension(200, 20));

        JButton btnSalvar = new JButton("Salvar");

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

        // Centralizando o botão
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnSalvar, gbc);
    }

    public static void main(String[] args) {
        TelaCadastroAdmin frame = new TelaCadastroAdmin();
        frame.setVisible(true);
    }
}




