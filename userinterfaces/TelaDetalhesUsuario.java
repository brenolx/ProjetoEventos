package userinterfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import entities.Administrador;
import entities.Participante;
import entities.Usuario;
import enuns.TipoUsuario; // Importando o enum TipoUsuario
import services.UsuarioService;

public class TelaDetalhesUsuario extends JFrame {
    private static final long serialVersionUID = 1L;
    private int usuarioId;
    private JTextField nomeField;
    private JTextField emailField;
    private JComboBox<TipoUsuario> tipoUsuarioComboBox; // JComboBox para Tipo de Usuário
    private JTextField cargoField; // Campo para Administrador
    private JTextField dataContratacaoField; // Campo para Administrador
    private JTextField dataNascimentoField; // Campo para Participante
    private JTextField cpfField; // Campo para Participante

    public TelaDetalhesUsuario(int usuarioId) throws SQLException, IOException {
        this.usuarioId = usuarioId;
        inicializarComponentes();
        carregarDetalhesUsuario();
    }

    private void inicializarComponentes() {
        setTitle("Detalhes do Usuário");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel lblTitulo = new JLabel("Detalhes do Usuário");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(lblTitulo);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        mainPanel.add(criarCampoTexto("Nome:", nomeField = new JTextField(20)));
        mainPanel.add(criarCampoTexto("Email:", emailField = new JTextField(20)));

        // JComboBox para Tipo de Usuário
        tipoUsuarioComboBox = new JComboBox<>(TipoUsuario.values()); // Usando o enum TipoUsuario
        mainPanel.add(criarCampoComboBox("Tipo de Usuário:", tipoUsuarioComboBox));

        // Campos específicos para Administrador
        cargoField = new JTextField(20);
        dataContratacaoField = new JTextField(20);
        mainPanel.add(criarCampoTexto("Cargo:", cargoField));
        mainPanel.add(criarCampoTexto("Data de Contratação:", dataContratacaoField));

        // Campos específicos para Participante
        dataNascimentoField = new JTextField(20);
        cpfField = new JTextField(20);
        mainPanel.add(criarCampoTexto("Data de Nascimento:", dataNascimentoField));
        mainPanel.add(criarCampoTexto("CPF:", cpfField));

        // Botão de Salvar Alterações
        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.addActionListener(e -> salvarAlteracoes());
        btnSalvar.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnSalvar);

        // Botão de Fechar
        JButton btnFechar = new JButton("Fechar");
        btnFechar.addActionListener(e -> dispose());
        btnFechar.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        mainPanel.add(btnFechar);

        add(mainPanel);
    }

    private JPanel criarCampoTexto(String label, JTextField textField) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        panel.add(lbl);
        panel.add(textField);
        return panel;
    }

    private JPanel criarCampoComboBox(String label, JComboBox<?> comboBox) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel lbl = new JLabel(label);
        panel.add(lbl);
        panel.add(comboBox);
        return panel;
    }

    private void carregarDetalhesUsuario() throws SQLException, IOException {
        UsuarioService usuarioService = new UsuarioService();
        Usuario usuario = usuarioService.getUsuarioPorId(usuarioId);

        if (usuario != null) {
            nomeField.setText(usuario.getNomeCompleto());
            emailField.setText(usuario.getEmail());
            tipoUsuarioComboBox.setSelectedItem(usuario.getTipoUsuario()); // Usando o enum TipoUsuario

            // Verifica o tipo de usuário e carrega informações específicas
            if (usuario instanceof Administrador) {
                Administrador admin = (Administrador) usuario;
                cargoField.setText(admin.getCargo());
                if (admin.getDataContratacao() != null) {
                    dataContratacaoField.setText(admin.getDataContratacao().toString());
                }
                dataNascimentoField.setVisible(false); // Esconde campo de data de nascimento
                cpfField.setVisible(false); // Esconde campo de CPF
            } else if (usuario instanceof Participante) {
                Participante participante = (Participante) usuario;
                dataNascimentoField.setText(participante.getDataNascimento().toString());
                cpfField.setText(participante.getCpf());
                cargoField.setVisible(false); // Esconde campo de cargo
                dataContratacaoField.setVisible(false); // Esconde campo de data de contratação
            }
        } else {
            JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarAlteracoes() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        TipoUsuario tipoUsuario = (TipoUsuario) tipoUsuarioComboBox.getSelectedItem(); // Obtendo o tipo de usuário do JComboBox
        String cargo = cargoField.getText();
        String dataContratacao = dataContratacaoField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String cpf = cpfField.getText();

        
        UsuarioService usuarioService = new UsuarioService();
        try {
            if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
                Administrador admin = new Administrador();
                admin.setId(usuarioId);
                admin.setNomeCompleto(nome);
                admin.setEmail(email);
                admin.setCargo(cargo);
                admin.setDataContratacao(java.time.LocalDate.parse(dataContratacao));
                usuarioService.atualizarUsuario(admin);
            } else {
                Participante participante = new Participante();
                participante.setId(usuarioId);
                participante.setNomeCompleto(nome);
                participante.setEmail(email);
                participante.setDataNascimento(java.time.LocalDate.parse(dataNascimento));
                participante.setCpf(cpf);
                usuarioService.atualizarUsuario(participante);
            }
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            dispose(); // Fecha a tela após salvar
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}