package userinterfaces;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;

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
	private TelaGerenciamentoUsuarios telaGerenciamentoUsuario;

    public TelaDetalhesUsuario(TelaGerenciamentoUsuarios telaGerenciamentoUsuario, int usuarioId) throws SQLException, IOException {
    	this.telaGerenciamentoUsuario = telaGerenciamentoUsuario;
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
        tipoUsuarioComboBox.addActionListener(e -> atualizarCamposPorTipoUsuario()); // Adiciona o listener
        mainPanel.add(criarCampoComboBox("Tipo de Usuário:", tipoUsuarioComboBox));

        // Campos para Administrador
        cargoField = new JTextField(20);
        dataContratacaoField = new JTextField(20);
        mainPanel.add(criarCampoTexto("Cargo:", cargoField));
        mainPanel.add(criarCampoTexto("Data de Contratação:", dataContratacaoField));

        // Campos para Participante
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

    private void carregarDetalhesUsuario() {
        UsuarioService usuarioService = new UsuarioService();
        try {
            Usuario usuario = usuarioService.getUsuarioPorId(usuarioId);

            if (usuario != null) {
                nomeField.setText(usuario.getNomeCompleto());
                emailField.setText(usuario.getEmail());

                if (usuario.getTipoUsuario() != null) {
                    tipoUsuarioComboBox.setSelectedItem(usuario.getTipoUsuario());
                } else {
                    JOptionPane.showMessageDialog(this, "Tipo de usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (usuario instanceof Administrador) {
                    Administrador admin = (Administrador) usuario;
                    cargoField.setText(admin.getCargo());
                    if (admin.getDataContratacao() != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        dataContratacaoField.setText(admin.getDataContratacao().format(formatter)); // Formata a data
                    }
                    dataNascimentoField.setEnabled(false);
                    cpfField.setEnabled(false);
                } else if (usuario instanceof Participante) {
                    Participante participante = (Participante) usuario;
                    if (participante.getDataNascimento() != null) {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        dataNascimentoField.setText(participante.getDataNascimento().format(formatter)); // Formata a data
                    }
                    cpfField.setText(participante.getCpf());
                    cargoField.setEnabled(false);
                    dataContratacaoField.setEnabled(false);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Usuário não encontrado.", "Erro", JOptionPane.ERROR_MESSAGE);
                System.err.println("Usuário não encontrado para o ID: " + usuarioId);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar detalhes do usuário: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("SQLException ao carregar detalhes do usuário: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro de I/O: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("IOException ao carregar detalhes do usuário: " + e.getMessage());
        }
    }

    private void atualizarCamposPorTipoUsuario() {
        TipoUsuario tipoUsuario = (TipoUsuario) tipoUsuarioComboBox.getSelectedItem();
        
        if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
            cargoField.setEnabled(true);
            dataContratacaoField.setEnabled(true);
            dataNascimentoField.setEnabled(false); // Desabilita edição de Data de Nascimento
            cpfField.setEnabled(false); // Desabilita edição de CPF
        } else if (tipoUsuario == TipoUsuario.PARTICIPANTE) {
            cargoField.setEnabled(false);
            dataContratacaoField.setEnabled(false);
            dataNascimentoField.setEnabled(true); // Habilita edição de Data de Nascimento
            cpfField.setEnabled(true); // Habilita edição de CPF
        }
    }

    private void salvarAlteracoes() {
        String nome = nomeField.getText();
        String email = emailField.getText();
        TipoUsuario tipoUsuario = (TipoUsuario) tipoUsuarioComboBox.getSelectedItem();
        String cargo = cargoField.getText();
        String dataContratacao = dataContratacaoField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String cpf = cpfField.getText();

        UsuarioService usuarioService = new UsuarioService();
        try {
        	System.out.println("Tipo usuario: " + tipoUsuario);
            if (tipoUsuario == null) {
                throw new IllegalArgumentException("Tipo de usuário não pode ser nulo.");
            }

            if (tipoUsuario == TipoUsuario.ADMINISTRADOR) {
                Administrador admin = new Administrador();
                admin.setTipoUsuario(tipoUsuario);
                admin.setId(usuarioId);
                admin.setNomeCompleto(nome);
                admin.setEmail(email);
                admin.setCargo(cargo);

                if (!dataContratacao.isEmpty()) {
                    admin.setDataContratacao(java.time.LocalDate.parse(dataContratacao));
                } else {
                    throw new IllegalArgumentException("Data de contratação não pode estar vazia.");
                }
                usuarioService.atualizarUsuario(admin);
            } else {
                Participante participante = new Participante();
                participante.setTipoUsuario(tipoUsuario);
                participante.setId(usuarioId);
                participante.setNomeCompleto(nome);
                participante.setEmail(email);

                if (!dataNascimento.isEmpty()) {
                    participante.setDataNascimento(java.time.LocalDate.parse(dataNascimento));
                } else {
                    throw new IllegalArgumentException("Data de nascimento não pode estar vazia.");
                }

                participante.setCpf(cpf);
                usuarioService.atualizarUsuario(participante);
            }
            JOptionPane.showMessageDialog(this, "Usuário atualizado com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            telaGerenciamentoUsuario.carregarUsuarios();
            dispose();
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("IllegalArgumentException ao salvar alterações: " + e.getMessage());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar alterações: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            System.err.println("Exception ao salvar alterações: " + e.getMessage());
        }
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
}