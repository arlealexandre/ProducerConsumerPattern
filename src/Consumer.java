public class Consumer extends Thread {

    private ProdConsBuffer buffer;

    public Consumer(ProdConsBuffer b) {
        this.buffer = b;
    }

    public void run() {
        try {
            Message m = this.buffer.get();
            System.out.println("Cons"+this.getName()+" a lu "+m.toString());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
