package obj5;

import obj1.ProdConsBuffer;

public class Consumer extends Thread {

    private ProdConsBuffer buffer;
    private int numberMessage;

    public Consumer(ProdConsBuffer b, int k) {
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
