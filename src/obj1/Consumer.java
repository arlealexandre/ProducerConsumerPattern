package obj1;

public class Consumer extends Thread {

    private ProdConsBuffer buffer;

    public Consumer(ProdConsBuffer b) {
        this.buffer = b;
    }

    /*
     * Les consomateurs on une seul tache qu'il effectue en boucle, récupérer un
     * message dans le buffer
     */
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
