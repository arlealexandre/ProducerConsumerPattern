package obj1;

import java.util.Random;

public class Producer extends Thread {

    private ProdConsBuffer buffer;
    private Random rand; // Une variable pour ajouter de l'aléatoire dans les temps d'attente
    private int nbMessage; // Le nombre de message produit par ce producteur

    public Producer(ProdConsBuffer b, int minProd, int maxProd) {
        this.buffer = b;
        this.rand = new Random();
        this.nbMessage = rand.nextInt((maxProd - minProd) + 1) + minProd;
    }

    /*
     * Le producteur écrit nbMessage message puis se finit
     */
    public void run() {
        for (int i = 0; i < nbMessage; i++) {
            Message m = new Message("M" + this.getName() + i);
            try {
                this.buffer.put(m);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public int nbMessageToProduce() {
        return nbMessage;
    }
}