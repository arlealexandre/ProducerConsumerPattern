package obj3;

import java.util.Random;

import obj1.Message;

public class ProducerSemaphore extends Thread {

    private ProdConsBufferSemaphore buffer;
    private Random rand;
    private int nbMessage;

    public ProducerSemaphore(ProdConsBufferSemaphore b, int minProd, int maxProd) {
        this.buffer = b;
        this.rand = new Random();
        this.nbMessage = rand.nextInt((maxProd - minProd) + 1) + minProd;
    }

    public void run() {
        for (int i=0; i<nbMessage; i++) {
            Message m = new Message("*");
            this.buffer.put(m);
        }
    }

    public int nbMessageToProduce() {
        return nbMessage;
    }

}