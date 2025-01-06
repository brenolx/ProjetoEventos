package entities;

import services.LoginService;

public class Servidor extends Thread {

    private Mensagem mensagemRecebida;

    // Construtor para inicializar a mensagem recebida
    public Servidor(Mensagem mensagemRecebida) {
    	
        this.mensagemRecebida = mensagemRecebida;
    }

    @Override
    public void run() {
        try {
            // Simula o recebimento da mensagem
            System.out.println("Mensagem recebida: " + mensagemRecebida);

            // Processa a mensagem recebida
            processarMensagem(mensagemRecebida);

        } catch (Exception e) {
            System.err.println("Erro no processamento: " + e.getMessage());
        }
    }

    // Método que processa a mensagem e retorna uma resposta
    private void processarMensagem(Mensagem mensagem) {
        if (mensagem.getOperacao().equals("login")) {
            System.out.println("Login confirmado");
            LoginService loginService = new LoginService();
            loginService.logarUsuario(mensagem.getEmail(), mensagem.getSenha());
  
        } else {
            System.out.println("Operação desconhecida.");
        }
    }
}
