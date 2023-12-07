package obj5;

import obj1.ProdConsBuffer;

public class ConsumerMulti extends Thread {

    private ProdConsBuffer buffer;
    private int numberMessage; // Le nombre de message que le consommateur veut consommer d'affiler

    public ConsumerMulti(ProdConsBuffer b, int k) {
        this.buffer = b;
        this.numberMessage = k;
    }

    public void run() {
        while (true) {
            try {
                this.buffer.get(this.numberMessage);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
