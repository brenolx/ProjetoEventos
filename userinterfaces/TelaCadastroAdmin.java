package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import entities.Mensagem;
import services.GerenciadorServidor;

public class TelaCadastroAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField cargoField;
    private JTextField dataContratacaoField;
    private GerenciadorServidor gerenciadorServidor;

    public TelaCadastroAdmin() {
        gerenciadorServidor = new GerenciadorServidor(); // Inicializa o gerenciador de servidor
        setTitle("Cadastro de Administrador");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("<html><span style='font-size:21px'>Cadastro de Administrador:</span></html>", SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(lblTitulo, gbc);

        // Nome Completo
        JLabel lblNome = new JLabel("Nome Completo:", SwingConstants.CENTER);
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

        // Cargo
        JLabel lblCargo = new JLabel("Cargo:", SwingConstants.CENTER);
        cargoField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblCargo, gbc);
        gbc.gridx = 1;
        add(cargoField, gbc);

        // Data de Contratação
        JLabel lblDataContratacao = new JLabel("Data de Contratação (dd/MM/yyyy):", SwingConstants.CENTER);
        dataContratacaoField = new JTextField();
        gbc.gridx = 0;
        gbc.gridy++;
        add(lblDataContratacao, gbc);
        gbc.gridx = 1;
        add(dataContratacaoField, gbc);

        // Botão de Cadastrar
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> processarCadastro());
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        add(btnCadastrar, gbc);
    }

    private void processarCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        String cargo = cargoField.getText();
        LocalDate dataContratacao = parseData(dataContratacaoField.getText());

        if (validarDados(nome, email, senha, cargo, dataContratacao)) {
            enviarCadastroParaServidor(nome, email, senha, cargo, dataContratacao);
        }
    }

    private boolean validarDados(String nome, String email, String senha, String cargo, LocalDate dataContratacao) {
        if (nome.isEmpty() || email.isEmpty() || senha.isEmpty() || cargo.isEmpty() || dataContratacao == null) {
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

    private void enviarCadastroParaServidor(String nome, String email, String senha, String cargo, LocalDate dataContratacao) {
        Mensagem mensagem = new Mensagem();
        mensagem.setOperacao("cadastrarAdmin");
        mensagem.setNome(nome);
        mensagem.setEmail(email);
        mensagem.setSenha(senha);
        mensagem.setCargo(cargo);
        mensagem.setDataContratacao(dataContratacao.toString()); // Adicionar a data de contratação

        gerenciadorServidor.enviarMensagem(mensagem); // Envia a mensagem para o servidor
    }
}