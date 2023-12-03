package obj3;

import java.util.concurrent.Semaphore;

import obj1.IProdConsBuffer;
import obj1.Message;

public class ProdConsBufferSemaphore implements IProdConsBuffer {

    Semaphore notFull, notEmpty, mutex;

    Message[] buffer;
    int nbMessage;
    int totalNbMessage;
    
    int in;
    int out;
    
    int size;

    int maxProdTime;
    int maxConsTime;

    /* 
     * Parameter : 
     * - size : The size of the buffer
     * - prodTime : The average time a production of a message take
     * - consTime : The average time a consommation of a message take 
     */
    public ProdConsBufferSemaphore (int size, int prodTime, int consTime) {
        this.buffer = new Message[size];
        this.nbMessage = 0;
        this.in = 0;
        this.out = 0;
        this.size = size;
        this.maxProdTime = (prodTime * 2 ) - 1;
        this.maxConsTime = (consTime * 2) - 1;
        this.notFull = new Semaphore(size, true);
        this.notEmpty = new Semaphore(0, true);
        this.mutex = new Semaphore(1, true);
    }

    public void put(Message m) {
    
        try { notFull.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;
        mutex.release();

        notEmpty.release();
    }

    public Message get() throws InterruptedException {

        try { notEmpty.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        this.out = (out + 1) % size;
        this.nbMessage--;
        mutex.release();

        notFull.release();
        
        return res;
    }

    public int nmsg() {
        return this.nbMessage;
    }

    public int totmsg() {
        return this.totalNbMessage;
    }

}
