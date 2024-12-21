package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TelaLogin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField emailField;
    private JPasswordField senhaField;

    public TelaLogin() {
        setTitle("Login - Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout());

        // Painel principal
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel titulo = new JLabel("Bem-vindo!");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(titulo, gbc);

        // Campo de email
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.LINE_START;
        gbc.gridy = 1;
        JLabel emailLabel = new JLabel("Email:");
        panel.add(emailLabel, gbc);

        gbc.gridx = 1;
        emailField = new JTextField(20);
        panel.add(emailField, gbc);

        // Campo de senha
        gbc.gridx = 0;
        gbc.gridy = 2;
        JLabel senhaLabel = new JLabel("Senha:");
        panel.add(senhaLabel, gbc);

        gbc.gridx = 1;
        senhaField = new JPasswordField(20);
        panel.add(senhaField, gbc);

        // Botão de login
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        JButton loginButton = new JButton("Entrar");
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String email = emailField.getText();
                String senha = new String(senhaField.getPassword());
                // Chamada ao método de autenticação
                autenticarUsuario(email, senha);
            }
        });
        panel.add(loginButton, gbc);
    }

    private void autenticarUsuario(String email, String senha) {
        // Implemente a lógica de autenticação aqui
        JOptionPane.showMessageDialog(this, "Autenticado com sucesso!"); // Exemplo
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaLogin().setVisible(true);
        });
    }
}
