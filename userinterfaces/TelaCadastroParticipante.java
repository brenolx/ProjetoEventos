package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import entities.Mensagem;
import services.GerenciadorServidor;

public class TelaCadastroParticipante extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;
    private GerenciadorServidor gerenciadorServidor;

    public TelaCadastroParticipante() {
        gerenciadorServidor = new GerenciadorServidor(); // Inicializa o gerenciador de servidor
        setTitle("Cadastro de Participante");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("<html><span style='font-size:21px'>Cadastro de Participante:</span></html>",
                SwingConstants.CENTER);

        // Componentes
        JLabel lblNome = new JLabel("Nome:", SwingConstants.CENTER);
        nomeField = new JTextField();
        nomeField.setPreferredSize(new Dimension(200, 20));

        JLabel lblEmail = new JLabel("Email:", SwingConstants.CENTER);
        emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(200, 20));

        JLabel lblSenha = new JLabel("Senha:", SwingConstants.CENTER);
        senhaField = new JPasswordField();
        senhaField.setPreferredSize(new Dimension(200, 20));

        JLabel lblDataNascimento = new JLabel("Data de Nascimento:", SwingConstants.CENTER);
        dataNascimentoField = new JTextField();
        dataNascimentoField.setPreferredSize(new Dimension(200, 20));
        configurarPlaceholderData();

        JLabel lblCPF = new JLabel("CPF:", SwingConstants.CENTER);
        cpfField = new JTextField();
        cpfField.setPreferredSize(new Dimension(200, 20));

        JButton btnCadastrar = new JButton("Cadastrar");
        JButton btnVoltar = new JButton("Voltar");
        btnCadastrar.setPreferredSize(new Dimension(80, 30));
        btnVoltar.setPreferredSize(new Dimension(80, 30));

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
        add(lblNome, gbc);
        gbc.gridx = 1;
        add(nomeField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblEmail, gbc);
        gbc.gridx = 1;
        add(emailField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblSenha, gbc);
        gbc.gridx = 1;
        add(senhaField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblDataNascimento, gbc);
        gbc.gridx = 1;
        add(dataNascimentoField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        add(lblCPF, gbc);
        gbc.gridx = 1;
        add(cpfField, gbc);

        // Adicionando os botões
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;
        add(btnVoltar, gbc);
        gbc.gridx = 1;
        add(btnCadastrar, gbc);

        btnCadastrar.addActionListener(e -> processarCadastro());
        btnVoltar.addActionListener(e -> voltarParaLogin());
    }

    // Configura o placeholder do campo de data
    private void configurarPlaceholderData() {
        dataNascimentoField.setText("dd/MM/yyyy");
        dataNascimentoField.setForeground(Color.GRAY);
        dataNascimentoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (dataNascimentoField.getText().equals("dd/MM/yyyy")) {
                    dataNascimentoField.setText("");
                    dataNascimentoField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (dataNascimentoField.getText().isEmpty()) {
                    dataNascimentoField.setText("dd/MM/yyyy");
                    dataNascimentoField.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Processa o cadastro do usuário
    private void processarCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        LocalDate dataNascimento = parseDataNascimento(dataNascimentoField.getText());
        String cpf = cpfField.getText();

        if (dataNascimento != null) {
            enviarCadastroParaServidor(nome, email, senha, dataNascimento, cpf);
        } else {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato dd/MM/yyyy.");
        }
    }

    // Converte uma string para data no formato dd/MM/yyyy
    private LocalDate parseDataNascimento(String data) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            return LocalDate.parse(data, formatter);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Envia os dados de cadastro para o servidor
    private void enviarCadastroParaServidor(String nome, String email, String senha, LocalDate dataNascimento, String cpf) {
        Mensagem mensagem = new Mensagem();
        mensagem.setOperacao("cadastrarParticipante");
        mensagem.setNome(nome);
        mensagem.setEmail(email);
        mensagem.setSenha(senha);
        mensagem.setDataNascimento(dataNascimento.toString()); // Adiciona a data de nascimento
        mensagem.setCpf(cpf);

        gerenciadorServidor.enviarMensagem(mensagem); // Envia a mensagem para o servidor
    }

    // Método para voltar à tela de login
    private void voltarParaLogin() {
    	new TelaLogin().setVisible(true);
        dispose();
    }
}