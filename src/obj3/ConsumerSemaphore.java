package obj3;

public class ConsumerSemaphore extends Thread {

    private ProdConsBufferSemaphore buffer;

    public ConsumerSemaphore(ProdConsBufferSemaphore b) {
        this.buffer = b;
    }

    public void run() {
        while (true) {
            try {
                this.buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
