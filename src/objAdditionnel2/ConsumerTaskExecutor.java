package objAdditionnel2;

import objAdditionnel1.MessageExec;
import objAdditionnel1.ProdConsBufferExec;

public class ConsumerTaskExecutor extends Thread {

    private ProdConsBufferExec buffer;
    private TaskExecutor taskManager; // Le consomateur est en lien avec le manager des taches, en effet il lui transmet après lecture

    public ConsumerTaskExecutor(ProdConsBufferExec b, TaskExecutor m) {
        this.buffer = b;
        this.taskManager = m;
    }

    public void run() {
        while (true) {
            try {
                MessageExec instruction = this.buffer.get();
                this.taskManager.askTask(instruction); // Il donne la tache au manager après l'avoir lu
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
