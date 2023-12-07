package objAdditionnel;

public class ConsumerExec extends Thread {

    private ProdConsBufferExec buffer;

    public ConsumerExec(ProdConsBufferExec b) {
        this.buffer = b;
    }

    public void run() {
        while (true) {
            try {
                MessageExec instruction = this.buffer.get();
                instruction.run();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
