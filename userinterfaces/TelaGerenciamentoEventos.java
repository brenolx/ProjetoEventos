package userinterfaces;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaGerenciamentoEventos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableEventos;

    public TelaGerenciamentoEventos() {
        setTitle("Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        // Layout principal
        getContentPane().setLayout(new BorderLayout());

        // Tabela de eventos
        String[] colunas = {"ID", "Título", "Categoria", "Data e Hora", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tableEventos = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tableEventos);
        getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Barra de ferramentas
        JToolBar toolbar = new JToolBar();
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnEditar = new JButton("Editar");
        JButton btnExcluir = new JButton("Excluir");
        JButton btnAtualizar = new JButton("Atualizar");

        toolbar.add(btnAdicionar);
        toolbar.add(btnEditar);
        toolbar.add(btnExcluir);
        toolbar.add(btnAtualizar);

        getContentPane().add(toolbar, BorderLayout.NORTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new TelaGerenciamentoEventos().setVisible(true);
        });
    }
}
