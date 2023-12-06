package obj5;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferMulti extends ProdConsBuffer {

   
    public ProdConsBufferMulti (int size, int prodTime, int consTime) {
        super(size, consTime, consTime);
    }

    public synchronized void put(Message m) {
    
        while (this.nbMessage == size) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;

        notifyAll();
    }

    public synchronized Message get() throws InterruptedException {

        while (this.nbMessage == 0) {
            wait();
        }

        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        this.out = (out + 1) % size;
        this.nbMessage--;

        notifyAll();
        
        return res;
    }

    @Override
    public synchronized Message[] get(int k) throws InterruptedException {
        Message[] res = new Message[k];
        for (int i = 0; i < k; i++) {
            while (this.nbMessage == 0) {
                wait();
            }

            int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
            try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

            res[i] = this.buffer[out];

            System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res[i].toString()+" dans la case n°"+Integer.toString(out));

            this.out = (out + 1) % size;
            this.nbMessage--;

            notifyAll();
        }
        return res;
    }

    @Override
    public void put(Message m, int n) {
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

}
