package userinterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.border.EmptyBorder;

public class TelaInscricoesParticipante extends JFrame {

    private static final long serialVersionUID = 1L;
	private TelaPrincipalParticipante telaPrincipalParticipante;

    public TelaInscricoesParticipante(TelaPrincipalParticipante telaPrincipalParticipante) {
    	this.telaPrincipalParticipante = telaPrincipalParticipante;
        setTitle("Inscrições");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        // Painel Principal com Padding
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(15, 15, 15, 15));
        getContentPane().add(panel);

        // Tabela de Inscrições
        String[] colunas = {"Nome do Evento", "Status do Evento", "Status da Inscrição"};
        
        // Matriz com dados Teste
        Object[][] dados = {
            {"Evento A", "Ativo", "Inscrito"},
            {"Evento B", "Concluído", "Concluído"},
            {"Evento C", "Cancelado", "Cancelado"}
        };

        DefaultTableModel model = new DefaultTableModel(dados, colunas);
        JTable tabelaInscricoes = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabelaInscricoes);

        panel.add(scrollPane, BorderLayout.CENTER);

        // Painel de Botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JButton btnVoltar = new JButton("Voltar");
        JButton btnCancelarInscricao = new JButton("Cancelar Inscrição");
        JButton btnConfirmarPresenca = new JButton("Confirmar Presença");

        panelBotoes.add(btnVoltar);
        panelBotoes.add(btnCancelarInscricao);
        panelBotoes.add(btnConfirmarPresenca);

        panel.add(panelBotoes, BorderLayout.SOUTH);

        // Ação do Botão Voltar
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();  // Fecha a tela atual
                telaPrincipalParticipante.setVisible(true);
            }
        });

        // Ação do Botão Cancelar Inscrição
        btnCancelarInscricao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para cancelar inscrição
                int selectedRow = tabelaInscricoes.getSelectedRow();
                if (selectedRow != -1) {
                    model.setValueAt("Cancelado", selectedRow, 2);  // Atualiza o status da inscrição para "Cancelado"
                }
            }
        });

        // Ação do Botão Confirmar Presença
        btnConfirmarPresenca.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Lógica para confirmar presença
                int selectedRow = tabelaInscricoes.getSelectedRow();
                if (selectedRow != -1) {
                    model.setValueAt("Presença Confirmada", selectedRow, 2);  // Atualiza o status da inscrição para "Presença Confirmada"
                }
            }
        });
    }
}