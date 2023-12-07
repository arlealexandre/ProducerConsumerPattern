package obj6;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferMultiSync extends ProdConsBuffer {

    public ProdConsBufferMultiSync (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
    }

    public synchronized void put(Message m, int n) {
    
        // If 
        while ((this.nbMessage + n > size)) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        for (int i=1; i<=n; i++) { // put n copies of message m
            System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" copie n°"+Integer.toString(i)+"/"+Integer.toString(n)+" dans la case n°"+Integer.toString(in));
            this.buffer[in] = m;
            this.in = (in + 1) % size;
            this.nbMessage++;
            this.totalNbMessage++;
            notify();
        }

        while ((!((MessageMultiSync)m).isMessageConsumed())) {
            System.out.println("Prod"+Thread.currentThread().getName()+" attend ");
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        notifyAll();
        System.out.println("Prod"+Thread.currentThread().getName()+" libre");
    }

    public void put(Message m) {
        this.put(m, 1);
    }

    public synchronized Message get() throws InterruptedException {
        
        System.out.println("Cons"+Thread.currentThread().getName()+" veut lire");
        
        while (this.nbMessage == 0) {
            wait();
        }

        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];
        MessageMultiSync resMS = (MessageMultiSync) res;

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        this.out = (out + 1) % size;
        this.nbMessage--;
        resMS.copyConsumed();

        while (!(resMS.isMessageConsumed())) {
            System.out.println("Cons"+Thread.currentThread().getName()+" attend ");
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        notifyAll();
        
        return res;
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
