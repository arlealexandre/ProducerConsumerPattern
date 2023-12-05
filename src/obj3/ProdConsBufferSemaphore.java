package obj3;

import java.util.concurrent.Semaphore;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferSemaphore extends ProdConsBuffer {

    Semaphore notFull, notEmpty, mutex;

    public ProdConsBufferSemaphore (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
        this.notFull = new Semaphore(size, true);
        this.notEmpty = new Semaphore(0, true);
        this.mutex = new Semaphore(1, true);
    }

    public void put(Message m) {
    
        try { notFull.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

        
        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;
        mutex.release();

        notEmpty.release();
    }

    public Message get() throws InterruptedException {

        try { notEmpty.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

        
        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        this.out = (out + 1) % size;
        this.nbMessage--;
        mutex.release();

        notFull.release();
        
        return res;
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
