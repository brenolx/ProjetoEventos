package userinterfaces;

import entities.Usuario;
import services.GerenciadorUsuario;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class TelaGerenciamentoUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tableUsuarios;
    private DefaultTableModel model;
    private GerenciadorUsuario gerenciadorUsuario;

    public TelaGerenciamentoUsuarios(GerenciadorUsuario gerenciadorUsuario) throws SQLException {
        this.gerenciadorUsuario = gerenciadorUsuario;
        configurarJanela();
        inicializarComponentes();
        carregarUsuarios();
    }

    private void configurarJanela() {
        setTitle("Gerenciamento de Usuários");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        // Layout principal
        getContentPane().setLayout(new BorderLayout());

        // Barra Superior
        JPanel barraSuperior = new JPanel(new BorderLayout());
        JLabel titulo = new JLabel("Gerenciamento de Usuários", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 18));
        barraSuperior.add(titulo, BorderLayout.CENTER);
        getContentPane().add(barraSuperior, BorderLayout.NORTH);

        // Tabela de usuários
        String[] colunas = {"ID", "Nome Completo", "Email", "Tipo de Usuário"};
        model = new DefaultTableModel(colunas, 0);
        tableUsuarios = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableUsuarios);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Painel de Ações
        JPanel painelAcoes = new JPanel();
        JButton btnAdicionar = new JButton("Adicionar Usuário");
        JButton btnAtualizar = new JButton("Atualizar Usuário");
        JButton btnExcluir = new JButton("Excluir Usuário");

        painelAcoes.add(btnAdicionar);
        painelAcoes.add(btnAtualizar);
        painelAcoes.add(btnExcluir);
        getContentPane().add(painelAcoes, BorderLayout.SOUTH);

        // Listeners dos botões
        btnAdicionar.addActionListener(e -> gerenciadorUsuario.abrirTelaCadastroParticipante());
        btnAtualizar.addActionListener(e -> atualizarUsuario());
        btnExcluir.addActionListener(e -> {
			try {
				excluirUsuario();
			} catch (SQLException | IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
    }

    private void carregarUsuarios() throws SQLException {
        try {
            List<Usuario> usuarios = gerenciadorUsuario.listarUsuarios();
            model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados
            for (Usuario usuario : usuarios) {
                model.addRow(new Object[]{usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail(), usuario.getTipoUsuario()});
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void atualizarUsuario() {
        int selectedRow = tableUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para atualizar.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirUsuario() throws SQLException, IOException {
        int selectedRow = tableUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            int usuarioId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                gerenciadorUsuario.excluirUsuario(usuarioId);
                carregarUsuarios(); // Atualiza a lista de usuários
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para excluir.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }
}