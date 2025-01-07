package userinterfaces;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import services.CadastroParticipanteService;

// Tela de Cadastro de Participante.

public class TelaCadastroParticipante extends JFrame {

    private static final long serialVersionUID = 1L;

    // Campos de entrada
    private JTextField nomeField;
    private JTextField emailField;
    private JPasswordField senhaField;
    private JTextField dataNascimentoField;
    private JTextField cpfField;

    // Construtor
    public TelaCadastroParticipante() {
        setTitle("Cadastro de Participante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        // Criando o painel com GridBagLayout
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBackground(Color.BLACK);
        getContentPane().add(panel);

        // Adicionando os componentes ao painel
        adicionarComponentes(panel);
    }

    // Adiciona os componentes à tela
    private void adicionarComponentes(JPanel panel) {
        // Título
        JLabel titulo = criarLabel("Cadastro de Participante", new Font("Segoe UI", Font.BOLD, 24), Color.WHITE);
        GridBagConstraints gbcTitulo = criarGridBagConstraints(0, 0, 2, GridBagConstraints.CENTER);
        panel.add(titulo, gbcTitulo);

        // Campos de entrada
        nomeField = criarCampoTexto("Nome:", 1, panel);
        emailField = criarCampoTexto("Email:", 2, panel);
        senhaField = criarCampoSenha("Senha:", 3, panel);
        dataNascimentoField = criarCampoTexto("Data de Nascimento:", 4, panel);
        configurarPlaceholderData();
        cpfField = criarCampoTexto("CPF:", 5, panel);

        // Botão de cadastro
        JButton cadastrarButton = new JButton("Cadastrar");
        estilizarBotao(cadastrarButton);
        GridBagConstraints gbcButton = criarGridBagConstraints(0, 6, 2, GridBagConstraints.CENTER);
        panel.add(cadastrarButton, gbcButton);

        cadastrarButton.addActionListener(e -> processarCadastro());
    }

    // Configura o placeholder do campo de data
    private void configurarPlaceholderData() {
        dataNascimentoField.setText("yyyy-MM-dd");
        dataNascimentoField.setForeground(Color.GRAY);
        dataNascimentoField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                if (dataNascimentoField.getText().equals("yyyy-MM-dd")) {
                    dataNascimentoField.setText("");
                    dataNascimentoField.setForeground(Color.BLACK);
                }
            }

            public void focusLost(java.awt.event.FocusEvent evt) {
                if (dataNascimentoField.getText().isEmpty()) {
                    dataNascimentoField.setText("yyyy-MM-dd");
                    dataNascimentoField.setForeground(Color.GRAY);
                }
            }
        });
    }

    // Cria um JLabel configurado
    private JLabel criarLabel(String texto, Font fonte, Color cor) {
        JLabel label = new JLabel(texto);
        label.setFont(fonte);
        label.setForeground(cor);
        return label;
    }

    // Cria um campo de texto com label
    private JTextField criarCampoTexto(String labelTexto, int gridy, JPanel panel) {
        JLabel label = criarLabel(labelTexto, new Font("Tahoma", Font.PLAIN, 12), Color.ORANGE);
        GridBagConstraints gbcLabel = criarGridBagConstraints(0, gridy, 1, GridBagConstraints.LINE_START);
        panel.add(label, gbcLabel);

        JTextField campoTexto = new JTextField(20);
        estilizarCampoTexto(campoTexto);
        GridBagConstraints gbcCampoTexto = criarGridBagConstraints(1, gridy, 1, GridBagConstraints.LINE_START);
        panel.add(campoTexto, gbcCampoTexto);

        return campoTexto;
    }

    // Cria um campo de senha com label
    private JPasswordField criarCampoSenha(String labelTexto, int gridy, JPanel panel) {
        JLabel label = criarLabel(labelTexto, new Font("Tahoma", Font.PLAIN, 12), Color.ORANGE);
        GridBagConstraints gbcLabel = criarGridBagConstraints(0, gridy, 1, GridBagConstraints.LINE_START);
        panel.add(label, gbcLabel);

        JPasswordField campoSenha = new JPasswordField(20);
        estilizarCampoTexto(campoSenha);
        GridBagConstraints gbcCampoSenha = criarGridBagConstraints(1, gridy, 1, GridBagConstraints.LINE_START);
        panel.add(campoSenha, gbcCampoSenha);

        return campoSenha;
    }

    // Configura o layout do componente
    private GridBagConstraints criarGridBagConstraints(int gridx, int gridy, int gridwidth, int anchor) {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gridx;
        gbc.gridy = gridy;
        gbc.gridwidth = gridwidth;
        gbc.anchor = anchor;
        gbc.insets = new Insets(10, 10, 10, 10);
        return gbc;
    }

    // Estiliza um campo de texto
    private void estilizarCampoTexto(JTextField campoTexto) {
        campoTexto.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        campoTexto.setBackground(Color.WHITE);
        campoTexto.setForeground(Color.BLACK);
        campoTexto.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    // Estiliza um botão
    private void estilizarBotao(JButton botao) {
        botao.setFont(new Font("Segoe UI", Font.BOLD, 14));
        botao.setBackground(Color.ORANGE);
        botao.setForeground(Color.BLACK);
        botao.setFocusPainted(false);
        botao.setBorder(new LineBorder(Color.ORANGE, 2));
    }

    // Processa o cadastro do usuário
    private void processarCadastro() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        String senha = new String(senhaField.getPassword());
        LocalDate dataNascimento = parseDataNascimento(dataNascimentoField.getText());
        String cpf = cpfField.getText();

        if (dataNascimento != null) {
            cadastrarUsuario(nome, email, senha, dataNascimento, cpf);
        } else {
            JOptionPane.showMessageDialog(this, "Data de nascimento inválida. Use o formato yyyy-MM-dd.");
        }
    }

    // Converte uma string para data
    private LocalDate parseDataNascimento(String data) {
        try {
            return LocalDate.parse(data, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Realiza o cadastro do usuário
    private void cadastrarUsuario(String nome, String email, String senha, LocalDate dataNascimento, String cpf) {
        CadastroParticipanteService cadastroService = new CadastroParticipanteService();
        try {
            boolean sucesso = cadastroService.cadastrarParticipante(nome, email, senha, dataNascimento, cpf);
            if (sucesso) {
                JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso!");
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar usuário: " + e.getMessage());
        }
    }
}
