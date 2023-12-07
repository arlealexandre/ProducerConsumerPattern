package objAdditionnel;

import java.util.Random;

public class ProducerExec extends Thread {

    private static final String[] OPERATIONS = {"fibo", "factoriel", "diviseur", "premier", "rien"}; 

    private ProdConsBufferExec buffer;
    private Random rand; // Une variable pour ajouter de l'aléatoire dans les temps d'attente
    private int nbMessage; // Le nombre de message produit par ce producteur

    public ProducerExec(ProdConsBufferExec b, int minProd, int maxProd) {
        this.buffer = b;
        this.rand = new Random();
        this.nbMessage = rand.nextInt((maxProd - minProd) + 1) + minProd;
    }

    /*
     * Le producteur écrit nbMessage message puis se finit
     */
    public void run() {
        for (int i = 0; i < nbMessage; i++) {
            // On génère un message aléatoire (tache a réaliser + valeur sur laquel réaliser la tache aléatoire)
            MessageExec m = new MessageExec(OPERATIONS[rand.nextInt(OPERATIONS.length)], rand.nextInt(20));
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