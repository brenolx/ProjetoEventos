package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import services.CadastroParticipanteService;

public class TelaCadastroParticipante extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private CadastroParticipanteService cadastroParticipanteService;

    public TelaCadastroParticipante() {
        cadastroParticipanteService = new CadastroParticipanteService();
        setTitle("Cadastro de Participante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("<html><span style='font-size:21px'>Cadastro de Participante:</span></html>", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        // Nome
        JLabel lblNome = new JLabel("Nome:", SwingConstants.CENTER);
        nomeField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(lblNome, gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        // Email
        JLabel lblEmail = new JLabel("Email:", SwingConstants.CENTER);
        emailField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        // Senha
        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        senhaField = new JPasswordField();
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblSenha, gbc);
        gbc.gridx = 1;
        add(senhaField, gbc);

        // Data de Nascimento
        JLabel lblDataNascimento = new JLabel("Data de Nascimento (dd/MM/yyyy):", SwingConstants.CENTER);
        dataNascimentoField = new JTextField("dd/MM/yyyy");
        dataNascimentoField.setForeground(Color.GRAY); // Define a cor do texto padrão
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblDataNascimento, gbc);
        gbc.gridx = 1;
        add(dataNascimentoField, gbc);

        // Adiciona o FocusListener
        dataNascimentoField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (dataNascimentoField.getText().equals("dd/MM/yyyy")) {
                    dataNascimentoField.setText("");
                    dataNascimentoField.setForeground(Color.BLACK); // Muda a cor do texto para preto
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (dataNascimentoField.getText().isEmpty()) {
                    dataNascimentoField.setText("dd/MM/yyyy");
                    dataNascimentoField.setForeground(Color.GRAY); // Restaura a cor do texto padrão
                }
            }
        });

        // CPF
        JLabel lblCPF = new JLabel("CPF:", SwingConstants.CENTER);
        cpfField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblCPF, gbc);
        gbc.gridx = 1;
        add(cpfField, gbc);

        // Botão de Cadastrar
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> processarCadastro());
        gbc.gridx = 1;
        gbc.gridy++;
        gbc.gridwidth = 1;
        add(btnCadastrar, gbc);

        // Botão de Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(e -> voltarParaLogin());
        gbc.gridx = 0;
        add(btnVoltar, gbc);
    }

    private void processarCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        LocalDate dataNascimento = parseData(dataNascimentoField.getText());
        String cpf = cpfField.getText();

        if (validarDados(nome, email, senha, dataNascimento, cpf)) {
            boolean sucesso = false;
            try {
                sucesso = cadastroParticipanteService.cadastrarParticipante(nome, email, senha, dataNascimento, cpf);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Participante cadastrado com sucesso!", "Cadastro", JOptionPane.INFORMATION_MESSAGE);
                new TelaLogin().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Erro ao cadastrar participante.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validarDados(String nome, String email, String senha, LocalDate dataNascimento, String cpf) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || dataNascimento == null || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos obrigatórios.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!isEmailValido(email)) {
            JOptionPane.showMessageDialog(this, "O email fornecido não é válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!validarCPF(cpf)) {
            JOptionPane.showMessageDialog(this, "CPF inválido. O CPF deve ter 11 dígitos.", "Erro de Cadastro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true; // Retornar true se todas as validações passarem
    }

    private LocalDate parseData(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            return null; // Retornar null se a data for inválida
        }
    }

    private boolean isEmailValido(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return email.matches(emailRegex);
    }

    private boolean validarCPF(String cpf) {
        // Remove caracteres não numéricos
        cpf = cpf.replaceAll("[^0-9]", "");

        // Verifica se o CPF tem 11 dígitos
        return cpf.length() == 11 && !cpf.matches("(\\d)\\1{10}"); // Verifica se não é uma sequência de números repetidos
    }

    private void voltarParaLogin() {
        new TelaLogin().setVisible(true);
        dispose(); // Fecha a tela atual
    }
}