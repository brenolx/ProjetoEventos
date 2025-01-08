package userinterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TelaGerenciamentoEventos extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTable tableEventos;

    public TelaGerenciamentoEventos() {
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setTitle("Gerenciamento de Eventos");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null);
    }

    private void inicializarComponentes() {
        // Layout principal
        getContentPane().setLayout(new BorderLayout());

        // Painel principal
        JPanel painelPrincipal = new JPanel(new BorderLayout());
        getContentPane().add(painelPrincipal, BorderLayout.CENTER);

        // Tabela de eventos
        String[] colunas = {"Título", "Categoria", "Data e Hora", "Status"};
        DefaultTableModel model = new DefaultTableModel(colunas, 0);
        tableEventos = new JTable(model);
        tableEventos.setFillsViewportHeight(true);
        tableEventos.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(tableEventos);
        painelPrincipal.add(scrollPane, BorderLayout.CENTER);

        // Painel de botões de interação
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnAdicionar = criarBotao("Adicionar");
        JButton btnEditar = criarBotao("Editar");
        JButton btnExcluir = criarBotao("Excluir");
        JButton btnAtualizar = criarBotao("Atualizar");
        painelBotoes.add(btnAdicionar);
        painelBotoes.add(btnEditar);
        painelBotoes.add(btnExcluir);
        painelBotoes.add(btnAtualizar);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);

        // Barra de ferramentas
        JPanel barraFerramentas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnVoltar = criarBotao("Voltar");
        barraFerramentas.add(btnVoltar);
        getContentPane().add(barraFerramentas, BorderLayout.NORTH);

        // Listener para o botão Voltar
        btnVoltar.addActionListener(e -> {

        });
    }

    private JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFocusPainted(false);
        return botao;
    }
}
