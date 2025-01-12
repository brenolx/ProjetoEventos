package userinterfaces;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import entities.Usuario;
import services.UsuarioService;

public class TelaGerenciamentoUsuarios extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tableUsuarios;
    private DefaultTableModel model;
    private UsuarioService usuarioService;
    private TelaPrincipalAdmin telaAdmin;

    public TelaGerenciamentoUsuarios(TelaPrincipalAdmin telaAdmin) throws SQLException {
        this.telaAdmin = telaAdmin;
        this.usuarioService = new UsuarioService(); 
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
        JButton btnVerMais = new JButton("Ver Mais");
        JButton btnExcluir = new JButton("Excluir Usuário");
        JButton btnVoltar = new JButton("Voltar"); 

        painelAcoes.add(btnAdicionar);
        painelAcoes.add(btnVerMais); 
        painelAcoes.add(btnExcluir);
        painelAcoes.add(btnVoltar); 
        getContentPane().add(painelAcoes, BorderLayout.SOUTH);

        // Listeners dos botões
        btnAdicionar.addActionListener(e -> usuarioService.abrirTelaCadastroParticipante());
        btnVerMais.addActionListener(e -> {
			try {
				verMaisUsuario();
			} catch (SQLException | IOException e1) {

				e1.printStackTrace();
			}
		}); // Action for Ver Mais button
        btnExcluir.addActionListener(e -> {
            try {
                excluirUsuario();
            } catch (SQLException | IOException e1) {
                e1.printStackTrace();
            }
        });
        
       
        btnVoltar.addActionListener(e -> {
            dispose(); 
            telaAdmin.setVisible(true); 
        });
    }

    public void carregarUsuarios() {
        try {
            List<Usuario> usuarios = usuarioService.listarUsuarios();
            if (usuarios == null) {
                JOptionPane.showMessageDialog(this, "Nenhum usuário encontrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                return; 
            }
            model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados
            for (Usuario usuario : usuarios) {
                model.addRow(new Object[]{usuario.getId(), usuario.getNomeCompleto(), usuario.getEmail(), usuario .getTipoUsuario()});
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar usuários: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verMaisUsuario() throws SQLException, IOException {
        int selectedRow = tableUsuarios.getSelectedRow(); // Obtém a linha selecionada
        if (selectedRow != -1) {
            int usuarioId = (int) model.getValueAt(selectedRow, 0); // Obtém o ID do usuário da linha selecionada
            TelaDetalhesUsuario telaDetalhesUsuario = new TelaDetalhesUsuario(TelaGerenciamentoUsuarios.this, usuarioId); // Passa o ID do usuário para a tela de detalhes
            telaDetalhesUsuario.setVisible(true); // Exibe a tela de detalhes
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para ver os detalhes.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void excluirUsuario() throws SQLException, IOException {
        int selectedRow = tableUsuarios.getSelectedRow();
        if (selectedRow != -1) {
            int usuarioId = (int) model.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este usuário?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
            	System.out.println("usuario id: " + usuarioId);
                usuarioService.excluirUsuario(usuarioId);
                carregarUsuarios(); // Atualiza a lista de usuários
            }
        } else {
            JOptionPane.showMessageDialog(this, "Por favor, selecione um usuário para excluir.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
        }
    }
}