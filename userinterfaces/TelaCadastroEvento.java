package userinterfaces;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.math.BigDecimal;
import entities.Evento;
import entities.Administrador;
import enuns.CategoriaEvento;
import services.EventoService;

public class TelaCadastroEvento extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField tituloField;
    private JTextField descricaoField;
    private JTextField dataField; // Campo para a data
    private JTextField horaField; // Campo para a hora
    private JTextField duracaoField;
    private JTextField localField;
    private JTextField capacidadeField;
    private JTextField precoField;
    private JComboBox<String> categoriaComboBox; // ComboBox para selecionar a categoria
    private TelaGerenciamentoEventosAdmin telaGerenciamentoEventosAdmin;
    private Administrador organizador; // Adicionando o organizador

    public TelaCadastroEvento(TelaGerenciamentoEventosAdmin telaGerenciamentoEventosAdmin, Administrador organizador) {
        this.telaGerenciamentoEventosAdmin = telaGerenciamentoEventosAdmin;
        this.organizador = organizador; // Inicializando o organizador
        setTitle("Cadastro de Evento");
        setSize(400, 500); // Aumentando a altura da tela
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Título
        JLabel lblTitulo = new JLabel("Cadastro de Evento", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        add(lblTitulo, gbc);

        // Título
        gbc.gridwidth = 1;
        gbc.gridy++;
        add(new JLabel("Título:"), gbc);
        tituloField = new JTextField();
        gbc.gridx = 1;
        add(tituloField, gbc);

        // Descrição
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Descrição:"), gbc);
        descricaoField = new JTextField();
        gbc.gridx = 1;
        add(descricaoField, gbc);

        // Data
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Data (dd/MM/yyyy):"), gbc);
        dataField = new JTextField();
        gbc.gridx = 1;
        add(dataField, gbc);

        // Hora
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Hora (HH:mm):"), gbc);
        horaField = new JTextField();
        gbc.gridx = 1;
        add(horaField, gbc);

        // Duração
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Duração (horas):"), gbc);
        duracaoField = new JTextField();
        gbc.gridx = 1;
        add(duracaoField, gbc);

        // Local
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Local:"), gbc);
        localField = new JTextField();
        gbc.gridx = 1;
        add(localField, gbc);

        // Capacidade Máxima
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Capacidade Máxima:"), gbc);
        capacidadeField = new JTextField();
        gbc.gridx = 1;
        add(capacidadeField, gbc);

        // Preço
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Preço:"), gbc);
        precoField = new JTextField();
        gbc.gridx = 1;
        add(precoField, gbc);

        // Categoria
        gbc.gridx = 0;
        gbc.gridy++;
        add(new JLabel("Categoria:"), gbc);
        String[] categorias = {"PALESTRA", "WORKSHOP", "CONFERENCIA"}; // Exemplo de categorias
        categoriaComboBox = new JComboBox<>(categorias);
        gbc.gridx = 1;
        add(categoriaComboBox, gbc);

        // Botão de Cadastrar
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        JButton btnCadastrar = new JButton("Cadastrar Evento");
        btnCadastrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    cadastrarEvento();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });
        add(btnCadastrar, gbc);
    }

    private void cadastrarEvento() throws IOException {
        // Validações
        if (tituloField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O título é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (descricaoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A descrição é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (dataField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A data é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (horaField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A hora é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (duracaoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A duração é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (localField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O local é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (capacidadeField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "A capacidade máxima é obrigatória.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (precoField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "O preço é obrigatório.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação de formato de data e hora
        String dataString = dataField.getText();
        String horaString = horaField.getText();
        String dataHoraString = dataString + " " + horaString; // Formato: dd/MM/yyyy HH:mm
        try {
            java.time.LocalDateTime.parse(dataHoraString, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Data ou hora em formato inválido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Validação de capacidade e preço
        int capacidade;
        BigDecimal preco;
        try {
            capacidade = Integer.parseInt(capacidadeField.getText());
            if (capacidade <= 0) {
                JOptionPane.showMessageDialog(this, "A capacidade máxima deve ser um número positivo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Capacidade máxima deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            preco = new BigDecimal(precoField.getText());
            if (preco.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(this, "O preço deve ser um valor positivo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Preço deve ser um número válido.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Cria um novo evento com os dados fornecidos
        Evento evento = new Evento();
        evento.setTitulo(tituloField.getText());
        evento.setDescricao(descricaoField.getText());

        // Converte a string combinada para LocalDateTime
        evento.setDataHora(java.time.LocalDateTime.parse(dataHoraString, java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        
        evento.setDuracaoHoras(Integer.parseInt(duracaoField.getText()));
        evento.setLocal(localField.getText());
        evento.setCapacidadeMaxima(capacidade);
        evento.setPreco(preco);

        // Definindo a categoria do evento
        String categoriaSelecionada = (String) categoriaComboBox.getSelectedItem();
        evento.setCategoria(CategoriaEvento.valueOf(categoriaSelecionada)); // Converte a string para o enum

        // Verifica se o organizador está definido
        if (organizador == null) {
            JOptionPane.showMessageDialog(this, "O organizador do evento não pode ser nulo.", "Erro de Validação", JOptionPane.ERROR_MESSAGE);
            return; // Retorna se o organizador não estiver definido
        }
        
        // Definindo o organizador do evento
        evento.setOrganizador(organizador); // Atribuindo o organizador ao evento

        // Chama o serviço para adicionar o evento
        EventoService eventoService = new EventoService();
        boolean sucesso = eventoService.adicionarEvento(evento);

        if (sucesso) {
            JOptionPane.showMessageDialog(this, "Evento cadastrado com sucesso!");
            telaGerenciamentoEventosAdmin.carregarEventos(); // Atualiza a lista de eventos
            dispose(); // Fecha a tela de cadastro
        } else {
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar evento.");
        }
    }
}