package services;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadManager {
    private static ExecutorService executorService;

    static {
        executorService = Executors.newCachedThreadPool(); // Cria um pool de threads
    }

    public static void execute(Runnable task) {
        executorService.submit(task); // Submete a tarefa ao executor
    }

    public static void shutdown() {
        executorService.shutdown(); // Encerra o executor
    }
}