package entities;

import services.LoginService;

import java.io.IOException;

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
               
                break;
            default:
                System.out.println("Operação desconhecida.");
                JOptionPane.showMessageDialog(null, "Operação não cadastrada: " + operacao, "Erro", JOptionPane.WARNING_MESSAGE);
                break;
        }
    }
}