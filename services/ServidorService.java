package services;

import entities.Mensagem;
import entities.Servidor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorService {
    private Servidor servidor;
    private ExecutorService executorService;

    public ServidorService() {
        this.servidor = null; // Inicialmente, não há servidor
        this.executorService = Executors.newCachedThreadPool(); // Executor para gerenciar threads
    }

    public void enviarMensagem(Mensagem mensagem) {

        // Se o servidor não estiver inicializado, cria uma nova instância
        if (servidor == null) {
            servidor = new Servidor(mensagem);
        } else {
            servidor = new Servidor(mensagem); // Atualiza a mensagem no servidor existente
        }

        // Envia a mensagem em uma nova thread
        executorService.submit(() -> servidor.iniciar());
    }

    public void shutdown() {
        executorService.shutdown(); // Para o executor quando não for mais necessário
    }
}