package obj6;

import java.util.Random;

import obj1.ProdConsBuffer;

public class ProducerMultiSync extends Thread {

    private ProdConsBuffer buffer;
    private Random rand;
    private int nbMessage;

    public ProducerMultiSync(ProdConsBuffer b, int minProd, int maxProd) {
        this.buffer = b;
        this.rand = new Random();
        this.nbMessage = rand.nextInt((maxProd - minProd) + 1) + minProd;
    }

    public void run() {
        for (int i=1; i<=nbMessage; i++) {  
            int n = rand.nextInt(buffer.getSize())+1; // number of copies between 1 and buffer size
            MessageMultiSync m = new MessageMultiSync("M"+this.getName()+Integer.toString(i),n);
            this.buffer.put(m, n); // put n copies of message m
        }
    }

    public int nbMessageToProduce() {
        return nbMessage;
    }

}