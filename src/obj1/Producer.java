package obj1;

import java.util.Random;

public class Producer extends Thread {

    private ProdConsBuffer buffer;
    private Random rand;
    private int nbMessage;

    public Producer(ProdConsBuffer b, int minProd, int maxProd) {
        this.buffer = b;
        this.rand = new Random();
        this.nbMessage = rand.nextInt((maxProd - minProd) + 1) + minProd;
    }

    public void run() {
        for (int i=0; i<nbMessage; i++) {
            Message m = new Message("*");
            try {
                this.buffer.put(m);
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
    }

    public int nbMessageToProduce() {
        return nbMessage;
    }

}