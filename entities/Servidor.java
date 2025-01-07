package entities;

import services.CadastroParticipanteService;
import services.LoginService;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Servidor {
    private Mensagem mensagemRecebida;

    // Construtor para inicializar a mensagem recebida
    public Servidor(Mensagem mensagemRecebida) {
        this.mensagemRecebida = mensagemRecebida;
    }

    public void iniciar() {
        try {
            // Simula o recebimento da mensagem
            System.out.println("Mensagem recebida: " + mensagemRecebida);

            // Processa a mensagem recebida
            processarMensagem(mensagemRecebida);
        } catch (Exception e) {
            System.err.println("Erro no processamento: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro no processamento: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Método que processa a mensagem e retorna uma resposta
    private void processarMensagem(Mensagem mensagem) throws IOException {
        String operacao = mensagem.getOperacao();
        
        switch (operacao) {
            case "login":
                LoginService loginService = new LoginService();
                // Chama o método logarUsuario do LoginService
                loginService.logarUsuario(mensagem.getEmail(), mensagem.getSenha());
                break;
            case "cadastrarParticipante":
                processarCadastroParticipante(mensagem);
                break;
            default:
                System.out.println("Operação desconhecida.");
                JOptionPane.showMessageDialog(null, "Operação não cadastrada: " + operacao, "Erro", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }

    // Método para processar o cadastro de um participante
    private void processarCadastroParticipante(Mensagem mensagem) {
        CadastroParticipanteService cadastroService = new CadastroParticipanteService();
        try {
            // Converte a data de nascimento de String para LocalDate
            LocalDate dataNascimento = LocalDate.parse(mensagem.getDataNascimento());
            boolean sucesso = cadastroService.cadastrarParticipante(
                mensagem.getNome(),
                mensagem.getEmail(),
                mensagem.getSenha(),
                dataNascimento,
                mensagem.getCpf()
            );

            if (sucesso) {
                System.out.println("Participante cadastrado com sucesso: " + mensagem.getNome());
            } else {
                System.out.println("Falha ao cadastrar o participante: " + mensagem.getNome());
            }
        } catch (Exception e) {
            System.err.println("Erro ao processar cadastro: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Erro ao processar cadastro: " + e.getMessage(), "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }
}