package obj1;
public class Consumer extends Thread {

    private ProdConsBuffer buffer;

    public Consumer(ProdConsBuffer b) {
        this.buffer = b;
    }

    public void run() {
        try {
            Message m = this.buffer.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
