package userinterfaces;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

import entities.Administrador;
import entities.Evento;
import services.EventoService;

public class TelaGerenciamentoEventosAdmin extends JFrame {

	private static final long serialVersionUID = 1L;
	private JTable tableEventos;
	private DefaultTableModel model;
	private TelaPrincipalAdmin telaAdmin;
	private Administrador adm;

	public TelaGerenciamentoEventosAdmin(TelaPrincipalAdmin telaAdmin, Administrador adm) throws IOException {
		this.telaAdmin = telaAdmin;
		this.adm = adm;
		configurarJanela();
		inicializarComponentes();
		carregarEventos();
	}

	private void configurarJanela() {
		setTitle("Gerenciamento de Eventos");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(900, 600);
		setLocationRelativeTo(null);
	}

	private void inicializarComponentes() {
		// Layout principal
		getContentPane().setLayout(new BorderLayout());

		// Barra Superior com espaçamento
		JPanel barraSuperior = new JPanel(new BorderLayout());
		barraSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaçamento interno

		JLabel titulo = new JLabel("Gerenciamento de Eventos", SwingConstants.CENTER);
		titulo.setFont(new Font("SansSerif", Font.BOLD, 18));

		// Criando novo botão para a barra superior
		JButton btnVoltar = criarBotao("Voltar");
		barraSuperior.add(btnVoltar, BorderLayout.WEST);
		barraSuperior.add(titulo, BorderLayout.CENTER);
		getContentPane().add(barraSuperior, BorderLayout.NORTH);

		// Listener do botão Voltar
		btnVoltar.addActionListener(e -> {
			dispose(); // Fecha a tela atual
			if (telaAdmin != null) {
				telaAdmin.setVisible(true);
			}
		});

		// Painel central para tabela e busca
		JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
		painelCentral.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espaçamento interno

		// Barra de busca
		JPanel barraBusca = new JPanel(new BorderLayout(5, 5));
		JTextField campoBusca = new JTextField();
		// Criando novo botão para busca
		JButton btnBuscar = criarBotao("Buscar");
		barraBusca.add(campoBusca, BorderLayout.CENTER);
		barraBusca.add(btnBuscar, BorderLayout.EAST);
		painelCentral.add(barraBusca, BorderLayout.NORTH);

		// Listener do botão Buscar
		btnBuscar.addActionListener(e -> buscarEvento(campoBusca.getText()));

		// Tabela de eventos
		String[] colunas = {"ID", "Título", "Categoria", "Status"};
		model = new DefaultTableModel(colunas, 0);
		tableEventos = new JTable(model);
		tableEventos.setFillsViewportHeight(true);
		JScrollPane scrollPane = new JScrollPane(tableEventos);
		painelCentral.add(scrollPane, BorderLayout.CENTER);
		getContentPane().add(painelCentral, BorderLayout.CENTER);

		// Painel de Ações com botões centralizados e espaçamento adequado
		JPanel painelAcoes = new JPanel();
		painelAcoes.setLayout(new BoxLayout(painelAcoes, BoxLayout.Y_AXIS)); // Usando BoxLayout para espaçamento
																				// adequado
		painelAcoes.setAlignmentX(Component.CENTER_ALIGNMENT); // Centralizando o painel de ações

		// Criando novos botões para ações
		JButton btnAdicionar = criarBotao("Adicionar");
		JButton btnVer = criarBotao("Ver mais");
		JButton btnExcluir = criarBotao("Excluir");
		JButton btnAtualizar = criarBotao("Atualizar");

		// Criando um painel horizontal para os botões
		JPanel painelBotoes = new JPanel();
		painelBotoes.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Centralizando os botões
		painelBotoes.add(btnAdicionar);
		painelBotoes.add(btnVer);
		painelBotoes.add(btnExcluir);
		painelBotoes.add(btnAtualizar);

		// Adicionando o painel com os botões no painel de ações
		painelAcoes.add(Box.createVerticalGlue()); // Espaço vazio para empurrar os botões para baixo
		painelAcoes.add(painelBotoes);
		painelAcoes.add(Box.createVerticalStrut(10)); // Espaçamento final entre os botões e a borda inferior

		getContentPane().add(painelAcoes, BorderLayout.SOUTH);

		// Listeners dos botões principais
		btnExcluir.addActionListener(e -> excluirEvento());
		btnAdicionar.addActionListener(e -> adicionarEvento());
		btnVer.addActionListener(e -> verEventoDetalhado());
		btnAtualizar.addActionListener(e -> atualizarEvento());
	}

	private void atualizarEvento() {
		try {
			carregarEventos();
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(this, "Erro ao atualizar eventos: " + ex.getMessage(), "Erro",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	private void excluirEvento() {
		int selectedRow = tableEventos.getSelectedRow(); // Obtém a linha selecionada
	    if (selectedRow != -1) {
	        int eventoId = (int) model.getValueAt(selectedRow, 0); // Obtém o ID do evento da linha selecionada
	        int confirm = JOptionPane.showConfirmDialog(this, "Tem certeza que deseja excluir este evento?", "Confirmação", JOptionPane.YES_NO_OPTION);
	        if (confirm == JOptionPane.YES_OPTION) {
	            EventoService eventoService = new EventoService();
	            try {
	                boolean sucesso = eventoService.removerEvento(eventoId); // Chama o método para remover o evento
	                if (sucesso) {
	                    JOptionPane.showMessageDialog(this, "Evento excluído com sucesso!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
	                    carregarEventos(); // Atualiza a lista de eventos
	                } else {
	                    JOptionPane.showMessageDialog(this, "Erro ao excluir evento.", "Erro", JOptionPane.ERROR_MESSAGE);
	                }
	            } catch (IOException ex) {
	                JOptionPane.showMessageDialog(this, "Erro ao excluir evento: " + ex.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
	            }
	        }
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecione um evento para excluir.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
	    }
	}

	private JButton criarBotao(String texto) {
		JButton botao = new JButton(texto);
		botao.setFocusPainted(false);
		botao.setFont(new Font("SansSerif", Font.PLAIN, 14));
		botao.setPreferredSize(new Dimension(110, 32));
		return botao;
	}

	private void buscarEvento(String termo) {
	    model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

	    EventoService eventoService = new EventoService();
	    List<Evento> eventos;
	    try {
	        eventos = eventoService.listarEventos(); // Obtém a lista de eventos
	    } catch (IOException e) {
	        JOptionPane.showMessageDialog(this, "Erro ao buscar eventos: " + e.getMessage(), "Erro",
	                JOptionPane.ERROR_MESSAGE);
	        return; // Retorna em caso de erro
	    }

	    // Itera sobre os eventos e verifica se o termo está presente
	    for (Evento evento : eventos) {
	        if (evento.getTitulo().toLowerCase().contains(termo.toLowerCase())
	                || evento.getCategoria().name().toLowerCase().contains(termo.toLowerCase())
	                || evento.getDataHora().toString().toLowerCase().contains(termo.toLowerCase())
	                || evento.getStatus().name().toLowerCase().contains(termo.toLowerCase())) {
	            // Adiciona uma nova linha com o ID, título, categoria e status do evento
	            model.addRow(new Object[] { evento.getId(), evento.getTitulo(), evento.getCategoria().name(), evento.getStatus().name() });
	        }
	    }

	    // Mensagem caso nenhum evento seja encontrado
	    if (model.getRowCount() == 0) {
	        JOptionPane.showMessageDialog(this, "Nenhum evento encontrado para o termo: " + termo, "Busca",
	                JOptionPane.INFORMATION_MESSAGE);
	    }
	}

	private void adicionarEvento() {
		TelaCadastroEvento telaCadastroEvento = new TelaCadastroEvento(this, adm);
		telaCadastroEvento.setVisible(true);
	}

	private void verEventoDetalhado() {
	    int selectedRow = tableEventos.getSelectedRow(); // Obtém a linha selecionada
	    if (selectedRow != -1) {
	        int eventoId = (int) model.getValueAt(selectedRow, 0); // Obtém o ID do evento da linha selecionada
	        TelaEventoDetalhadoAdmin telaEventoDetalhado = new TelaEventoDetalhadoAdmin(TelaGerenciamentoEventosAdmin.this, eventoId); // Passa o ID do evento para a tela de detalhes
	        telaEventoDetalhado.setVisible(true); // Exibe a tela de detalhes
	    } else {
	        JOptionPane.showMessageDialog(this, "Por favor, selecione um evento para ver os detalhes.", "Seleção Inválida", JOptionPane.WARNING_MESSAGE);
	    }
	}

	public void carregarEventos() throws IOException {
	    EventoService eventoService = new EventoService();
	    List<Evento> eventos = eventoService.listarEventos();

	    model.setRowCount(0); // Limpa a tabela antes de adicionar novos dados

	    if (eventos != null) {
	        for (Evento evento : eventos) {
	            System.out.println(evento);
	            model.addRow(new Object[] { evento.getId(), evento.getTitulo(), evento.getCategoria().name(), evento.getStatus().name() });
	        }
	    }

	    // Definindo a largura da coluna "ID" após carregar os eventos
	    tableEventos.getColumnModel().getColumn(0).setPreferredWidth(50); // Ajuste a largura para 50 pixels
	    tableEventos.getColumnModel().getColumn(0).setMinWidth(30); // Define a largura mínima
	    tableEventos.getColumnModel().getColumn(0).setMaxWidth(80); // Define a largura máxima
	}

	public static void main(String[] args) throws IOException {
		TelaGerenciamentoEventosAdmin tela = new TelaGerenciamentoEventosAdmin(null, null);
		tela.setVisible(true);
	}
}
