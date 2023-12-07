package objAdditionnel2;

import objAdditionnel1.MessageExec;
import objAdditionnel1.ProdConsBufferExec;

public class ConsumerTaskExecutor extends Thread {

    private ProdConsBufferExec buffer;
    private TaskExecutor taskManager;

    public ConsumerTaskExecutor(ProdConsBufferExec b, TaskExecutor m) {
        this.buffer = b;
        this.taskManager = m;
    }

    public void run() {
        while (true) {
            try {
                MessageExec instruction = this.buffer.get();
                this.taskManager.askTask(instruction);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
