package obj1;
public class Producer extends Thread {

    private ProdConsBuffer buffer;

    public Producer(ProdConsBuffer b) {
        this.buffer = b;
    }

    public void run() {
        Message m = new Message("*");
        this.buffer.put(m);
    }

}