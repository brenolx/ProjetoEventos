package userinterfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import dao.BancoDados;
import dao.EventoDAO;
import dao.InscricaoDAO;
import entities.Evento;

public class TelaRelatorioEventosNaoOcorridos extends JFrame {
    private static final long serialVersionUID = 1L;
    private JTable table;
    private DefaultTableModel model;

    public TelaRelatorioEventosNaoOcorridos() {
        setTitle("Relatório de Eventos Não Ocorridos");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("Relatório de Eventos Não Ocorridos", SwingConstants.CENTER);
        titulo.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(titulo, BorderLayout.NORTH);

        configurarTabela();
        carregarDados();

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnVoltar = criarBotao("Voltar", e -> dispose());
        JButton btnExportar = criarBotao("Exportar .xls", e -> exportarParaXLS());
        panel.add(btnVoltar);
        panel.add(btnExportar);
        add(panel, BorderLayout.SOUTH);
    }

    private void configurarTabela() {
        model = new DefaultTableModel() {
            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        table = new JTable(model);
        String[] colunas = {"Título", "Descrição", "Data", "Hora", "Duração (Horas)", "Local", "Capacidade Máxima", "Capacidade Restante", "Preço", "Categoria", "Status"};
        for (String coluna : colunas) {
            model.addColumn(coluna);
        }
    }

    private void carregarDados() {
        try (Connection conn = BancoDados.conectar()) {
            EventoDAO eventoDAO = new EventoDAO(conn);
            InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
            List<Evento> eventos = eventoDAO.listarEventos(); // Método que deve retornar todos os eventos

            for (Evento evento : eventos) {
                if (evento.getDataHora().isAfter(java.time.LocalDateTime.now())) { // Verifica se o evento ainda não ocorreu
                    int totalInscricoes = inscricaoDAO.listarInscricoesPorEvento(evento.getId()).size(); // Obtém o número de inscrições
                    int capacidadeRestante = evento.getCapacidadeMaxima() - totalInscricoes; // Calcula a capacidade restante

                    model.addRow(new Object[]{
                        evento.getTitulo(),
                        evento.getDescricao(),
                        evento.getDataHora().toLocalDate(),
                        evento.getDataHora().toLocalTime(),
                        evento.getDuracaoHoras(),
                        evento.getLocal(),
                        evento.getCapacidadeMaxima(),
                        capacidadeRestante,
                        evento.getPreco(),
                        evento.getCategoria().name(),
                        evento.getStatus().name()
                    });
                }
            }
        } catch (SQLException | IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao buscar eventos: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        } finally {
        	try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    private JButton criarBotao(String texto, ActionListener acao) {
        JButton botao = new JButton(texto);
        botao.addActionListener(acao);
        return botao;
    }

    private void exportarParaXLS() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "A tabela está vazia. Não há dados para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como");
        fileChooser.setSelectedFile(new File("relatorio_eventos_nao_ocorridos.xls"));
        if (fileChooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            try (FileWriter fw = new FileWriter(fileChooser.getSelectedFile()); BufferedWriter bw = new BufferedWriter(fw)) {
                for (int i = 0; i < model.getColumnCount(); i++) {
                    bw.write(model.getColumnName(i) + "\t");
                }
                bw.write("\n");

                for (int i = 0; i < model.getRowCount(); i++) {
                    for (int j = 0; j < model.getColumnCount(); j++) {
                        Object value = model.getValueAt(i, j);
                        bw.write((value != null ? value.toString() : "") + "\t");
                    }
                    bw.write("\n");
                }

                bw.flush();
                JOptionPane.showMessageDialog(this, "Relatório foi salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}