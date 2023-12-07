package objAdditionnel;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ConsumerExec extends Thread {

    private ProdConsBuffer buffer;

    public ConsumerExec(ProdConsBuffer b) {
        this.buffer = b;
    }

    public void run() {
        while (true) {
            try {
                Message instruction = this.buffer.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
}
