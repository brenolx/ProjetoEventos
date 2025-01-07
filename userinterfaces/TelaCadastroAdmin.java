package userinterfaces;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

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

        JLabel lblTipoUsuario = new JLabel("Tipo de Usuário:", SwingConstants.CENTER);
        JComboBox<String> cbTipoUsuario = new JComboBox<>(new String[]{"Administrador", "Participante"});

        JLabel lblCargo = new JLabel("Cargo:", SwingConstants.CENTER);
        JTextField txtCargo = new JTextField();
        txtCargo.setPreferredSize(new Dimension(200, 20));

        JLabel lblDataContratacao = new JLabel("Data da Contratação:", SwingConstants.CENTER);
        JTextField txtDataContratacao = new JTextField();
        txtDataContratacao.setPreferredSize(new Dimension(200, 20));

        JButton btnSalvar = new JButton("Cadastrar");

        // Adicionando os Componentes ao JFrame em duas colunas
        gbc.gridx = 0;
        gbc.gridy = 0;
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
        add(lblTipoUsuario, gbc);
        gbc.gridx = 1;
        add(cbTipoUsuario, gbc);

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


