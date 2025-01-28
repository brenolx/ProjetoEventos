package userinterfaces;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import dao.InscricaoDAO;
import entities.Inscricao;
import entities.Evento;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class TelaRelatorioParticipante extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public TelaRelatorioParticipante(Connection conn, int participanteId) {
        setTitle("Relatório de Eventos Inscritos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Adiciona o título
        JLabel titulo = new JLabel("Relatório detalhado de Eventos Inscritos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        // Configura o modelo da tabela
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(model);

        // Adiciona as colunas com títulos em negrito
        model.addColumn("Título");
        model.addColumn("Descrição");
        model.addColumn("Data");
        model.addColumn("Hora");
        model.addColumn("Duração (Horas)");
        model.addColumn("Local");
        model.addColumn("Capacidade Máxima");
        model.addColumn("Preço R$");
        model.addColumn("Categoria");
        model.addColumn("Status Evento");
        model.addColumn("Status Inscrição");

        // Define larguras fixas para as colunas
        int[] columnWidths = {80, 120, 70, 50, 100, 80, 120, 70, 100, 80, 100};
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }

        // Centraliza os títulos e conteúdos das colunas
        DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, renderer);

        // Popula a tabela com dados
        try {
            InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
            List<Inscricao> inscricoes = inscricaoDAO.listarInscricoes(participanteId);
            for (Inscricao inscricao : inscricoes) {
                Evento evento = inscricao.getEvento();
                model.addRow(new Object[]{
                    evento.getTitulo(),
                    evento.getDescricao(),
                    evento.getDataHora().toLocalDate(),
                    evento.getDataHora().toLocalTime(),
                    evento.getDuracaoHoras(),
                    evento.getLocal(),
                    evento.getCapacidadeMaxima(),
                    evento.getPreco(),
                    evento.getCategoria().name(),
                    evento.getStatus().name(),
                    inscricao.getStatusInscricao().name()
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar inscrições: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        // Adiciona a tabela à tela com bordas
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(new EmptyBorder(10, 10, 0, 10));
        add(scrollPane, BorderLayout.CENTER);

        // Cria e adiciona o botão Voltar
        JButton btnVoltar = new JButton("Voltar");
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // Cria e adiciona o botão Exportar
        JButton btnExportar = new JButton("Exportar .xls");
        btnExportar.setBackground(Color.getHSBColor(0.33f, 1f, 0.5f));
        btnExportar.setForeground(Color.WHITE);
        btnExportar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exportarParaXLS();
            }
        });

        // Painel para os botões
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        panel.add(btnVoltar);
        panel.add(btnExportar);

        // Adiciona o painel com os botões à tela
        add(panel, BorderLayout.SOUTH);
    }

    private void exportarParaXLS() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como");
        fileChooser.setSelectedFile(new File("relatorio.xls"));
        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter writer = new FileWriter(fileToSave)) {
                // Cria a linha do cabeçalho
                for (int i = 0; i < model.getColumnCount(); i++) {
                    writer.write(model.getColumnName(i) + "\t");
                }
                writer.write("\n");

                // Popula a planilha com dados
                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        if (value != null) {
                            writer.write(value.toString());
                        }
                        writer.write("\t");
                    }
                    writer.write("\n");
                }
                writer.flush();
                JOptionPane.showMessageDialog(this, "Relatório foi salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }
}
