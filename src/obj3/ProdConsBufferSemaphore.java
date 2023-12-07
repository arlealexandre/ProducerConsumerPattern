package obj3;

import java.util.concurrent.Semaphore;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferSemaphore extends ProdConsBuffer {

    /* Nous utilisons ici 3 semaphores */
    Semaphore notFull, notEmpty, mutex;

    public ProdConsBufferSemaphore (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
        this.notFull = new Semaphore(size, true); // sémaphore pour gérer les producteurs
        this.notEmpty = new Semaphore(0, true); // sémaphore pour gérer les consommateurs 
        this.mutex = new Semaphore(1, true); // sémaphore pour gérer l'accès au buffer 
    }

    public void put(Message m) {
    
        // un producteur acquiert le sémaphore notfull
        try { notFull.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        
        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

        // un seul producteur peut accéder au buffer pour écrire son message
        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;
        mutex.release();

        // le producteur relache le verrou notempty et permet de libérer un consommateur
        notEmpty.release();
    }

    public Message get() throws InterruptedException {

        // un consommateur acquiert le sémaphore notempty
        try { notEmpty.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }

        
        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        // un seul consommateur peut accéder au buffer lire un message
        try { mutex.acquire(); } catch (InterruptedException e) { e.printStackTrace(); }
        this.out = (out + 1) % size;
        this.nbMessage--;
        mutex.release();

        // le consommateur relache le verrou notfull et permet de libérer un producteur
        notFull.release();
        
        return res;
    }

    @Override
    public void put(Message m, int n) {
        throw new UnsupportedOperationException("Unimplemented method 'put'");
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
