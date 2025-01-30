package userinterfaces;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import dao.BancoDados;
import dao.EventoDAO;
import dao.InscricaoDAO;
import entities.Evento;
import entities.Inscricao;
import entities.Participante;

public class TelaRelatorioDetalhadoEvento extends JFrame {
    private static final long serialVersionUID = 1L;

    // Constantes para colunas
    private static final String[] COLUNAS_EVENTOS = { "ID", "Título", "Data", "Hora" };
    private static final String[] COLUNAS_PARTICIPANTES = { "Nome", "Email", "Status" };

    private JTable tableEventos;
    private DefaultTableModel modelEventos;
    private JTable tableParticipantes;
    private DefaultTableModel modelParticipantes;

    public TelaRelatorioDetalhadoEvento() {
        setTitle("Selecione um Evento");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        inicializarComponentes();
        carregarEventos();
    }

    private void inicializarComponentes() {
        JPanel panel = new JPanel(new BorderLayout());
        getContentPane().add(panel);

        // Tabela de Eventos
        modelEventos = new DefaultTableModel(COLUNAS_EVENTOS, 0);
        tableEventos = new JTable(modelEventos);
        tableEventos.setFillsViewportHeight(true);
        JScrollPane scrollPaneEventos = new JScrollPane(tableEventos);
        panel.add(scrollPaneEventos, BorderLayout.CENTER);

        // Botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(criarBotao("Selecionar Evento", e -> selecionarEvento()));
        buttonPanel.add(criarBotao("Voltar", e -> dispose()));
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void carregarEventos() {
        try (Connection conn = BancoDados.conectar()) {
            EventoDAO eventoDAO = new EventoDAO(conn);
            List<Evento> eventos = eventoDAO.listarEventos();

            for (Evento evento : eventos) {
                if (evento.getDataHora().isAfter(java.time.LocalDateTime.now())) {
                    modelEventos.addRow(new Object[] { evento.getId(), evento.getTitulo(),
                            evento.getDataHora().toLocalDate(), evento.getDataHora().toLocalTime() });
                }
            }
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar eventos: " + e.getMessage());
        } finally {
        	try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    private void selecionarEvento() {
        int selectedRow = tableEventos.getSelectedRow();
        if (selectedRow != -1) {
            int eventoId = (int) modelEventos.getValueAt(selectedRow, 0);
            carregarDetalhesEvento(eventoId);
        } else {
            mostrarAviso("Por favor, selecione um evento.");
        }
    }

    private void carregarDetalhesEvento(int eventoId) {
        try (Connection conn = BancoDados.conectar()) {
            EventoDAO eventoDAO = new EventoDAO(conn);
            Evento evento = eventoDAO.buscarEventoPorId(eventoId);

            if (evento != null) {
                exibirDetalhesEvento(evento);
            } else {
                mostrarErro("Evento não encontrado.");
            }
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar detalhes do evento: " + e.getMessage());
        } finally {
        	try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    private void exibirDetalhesEvento(Evento evento) {
        getContentPane().removeAll();

        // Criar painel de detalhes do evento
        JPanel detalhesPanel = new JPanel(new GridBagLayout());
        detalhesPanel.setBorder(BorderFactory.createTitledBorder("Detalhes do Evento"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Espaçamento
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0; // Começar na coluna 0
        gbc.gridy = 0; // Começar na linha 0

        // Adicionar detalhes do evento
        adicionarDetalhe(detalhesPanel, gbc, "Título:", evento.getTitulo());
        adicionarDetalhe(detalhesPanel, gbc, "Descrição:", evento.getDescricao());
        adicionarDetalhe(detalhesPanel, gbc, "Data:", evento.getDataHora().toLocalDate().toString());
        adicionarDetalhe(detalhesPanel, gbc, "Hora:", evento.getDataHora().toLocalTime().toString());
        adicionarDetalhe(detalhesPanel, gbc, "Duração (Horas):", String.valueOf(evento.getDuracaoHoras()));
        adicionarDetalhe(detalhesPanel, gbc, "Local:", evento.getLocal());
        adicionarDetalhe(detalhesPanel, gbc, "Capacidade Máxima:", String.valueOf(evento.getCapacidadeMaxima()));
        adicionarDetalhe(detalhesPanel, gbc, "Preço:", evento.getPreco().toString());
        adicionarDetalhe(detalhesPanel, gbc, "Categoria:", evento.getCategoria().name());
        adicionarDetalhe(detalhesPanel, gbc, "Status:", evento.getStatus().name());

        getContentPane().add(detalhesPanel, BorderLayout.NORTH);

        // Criar modelo e tabela de participantes
        modelParticipantes = new DefaultTableModel(COLUNAS_PARTICIPANTES, 0);
        tableParticipantes = new JTable(modelParticipantes);
        JScrollPane scrollPaneParticipantes = new JScrollPane(tableParticipantes);
        scrollPaneParticipantes.setBorder(BorderFactory.createTitledBorder("Participantes"));

        carregarParticipantesInscritos(evento.getId());
        getContentPane().add(scrollPaneParticipantes, BorderLayout.CENTER);

        // Criar painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(criarBotao("Voltar", e -> dispose()));
        buttonPanel.add(criarBotao("Exportar", e -> exportarDados(evento)));

        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        // Atualizar layout e interface
        getContentPane().revalidate();
        getContentPane().repaint();
    }

    private void adicionarDetalhe(JPanel panel, GridBagConstraints gbc, String label, String value) {
        gbc.gridx = 0;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(value), gbc);
        gbc.gridy++;
    }

    private JButton criarBotao(String texto, ActionListener acao) {
        JButton button = new JButton(texto);
        button.addActionListener(acao);
        return button;
    }

    private void carregarParticipantesInscritos(int eventoId) {
        try (Connection conn = BancoDados.conectar()) {
            InscricaoDAO inscricaoDAO = new InscricaoDAO(conn);
            List<Inscricao> inscricoes = inscricaoDAO.listarInscricoesPorEvento(eventoId);

            modelParticipantes.setRowCount(0);

            if (inscricoes.isEmpty()) {
                modelParticipantes.addRow(new Object[] { "Nenhum participante inscrito", "-", "-" });
            } else {
                for (Inscricao inscricao : inscricoes) {
                    Participante participante = inscricao.getParticipante();
                    modelParticipantes.addRow(new Object[] { 
                        participante.getNomeCompleto(), 
                        participante.getEmail(), 
                        inscricao.getStatusInscricao().name() 
                    });
                }
            }
        } catch (SQLException | IOException e) {
            mostrarErro("Erro ao carregar participantes: " + e.getMessage());
        } finally {
        	try {
				BancoDados.desconectar();
			} catch (SQLException e) {
				e.printStackTrace();
			}
        }
    }

    private void exportarDados(Evento evento) {
        if (modelParticipantes.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Não há dados para exportar.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Salvar como");
        fileChooser.setSelectedFile(new File("relatorio_evento_participantes.xls")); // Nome padrão do arquivo

        int userSelection = fileChooser.showSaveDialog(this);
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            try (FileWriter fw = new FileWriter(fileToSave); BufferedWriter bw = new BufferedWriter(fw)) {
                // Escreve o cabeçalho do evento
                bw.write("Detalhes do Evento\n");
                bw.write("Título: " + evento.getTitulo() + "\n");
                bw.write("Descrição: " + evento.getDescricao() + "\n");
                bw.write("Data: " + evento.getDataHora().toLocalDate() + "\n");
                bw.write("Hora: " + evento.getDataHora().toLocalTime() + "\n");
                bw.write("Duração (Horas): " + evento.getDuracaoHoras() + "\n");
                bw.write("Local: " + evento.getLocal() + "\n");
                bw.write("Capacidade Máxima: " + evento.getCapacidadeMaxima() + "\n");
                bw.write("Preço: " + evento.getPreco() + "\n");
                bw.write("Categoria: " + evento.getCategoria().name() + "\n");
                bw.write("Status: " + evento.getStatus().name() + "\n\n");

                // Escreve o cabeçalho da tabela de participantes
                bw.write("Participantes\n");
                bw.write("Nome\tEmail\tStatus\n");

                // Escreve os dados dos participantes
                for (int i = 0; i < modelParticipantes.getRowCount(); i++) {
                    String nome = (String) modelParticipantes.getValueAt(i, 0);
                    String email = (String) modelParticipantes.getValueAt(i, 1);
                    String status = (String) modelParticipantes.getValueAt(i, 2);
                    bw.write(nome + "\t" + email + "\t" + status + "\n");
                }

                bw.flush();
                JOptionPane.showMessageDialog(this, "Relatório foi salvo com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Erro ao exportar relatório: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void mostrarErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Erro", JOptionPane.ERROR_MESSAGE);
    }

    private void mostrarAviso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Aviso", JOptionPane.WARNING_MESSAGE);
    }
}