package obj4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferLock extends ProdConsBuffer {

    /* Nous utilisons ici un verrou et deux conditions */
    ReentrantLock lock;
    Condition notEmpty, notFull;

    public ProdConsBufferLock (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
        this.lock = new ReentrantLock(); // verrou pour gérer les sections critiques
        this.notEmpty = lock.newCondition(); // conditions pour gérer les consommateurs
        this.notFull = lock.newCondition(); // conditions pour gérer les producteurs
    }

    public void put(Message m) {
    
        // on vérouille le verrou -> début de la section critique
        lock.lock();

        try {

            // le producteur se met en attente sur la condition notFull tant que le buffer est plein
            while (this.nbMessage == size) {
                try { notFull.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            }

            int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
            try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

            System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" dans la case n°"+Integer.toString(in));

            this.buffer[in] = m;
            this.in = (in + 1) % size;
            this.nbMessage++;
            this.totalNbMessage++;
            
            // le producteur libère un consommateur qui est en attente sur la condition notEmpty
            notEmpty.signal();

        } finally {
            // on dévérouille le verrou -> fin de la section critique
            lock.unlock();
        }
    }

    public Message get() throws InterruptedException {

        // on vérouille le verrou -> début de la section critique
        lock.lock();

        try {

            // le consommateur se met en attente sur la condition notEmpty tant que le buffer ne contient pas de message à lire
            while (this.nbMessage == 0) {
                try { notFull.await(); } catch (InterruptedException e) { e.printStackTrace(); }
            }
     
            int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
            try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

            Message res = this.buffer[out];

            System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

            this.out = (out + 1) % size;
            this.nbMessage--;

            // le consommateur libère un producteur qui est en attente sur la condition notFull
            notFull.signal();
            
            return res;
        } finally {
            // on dévérouille le verrou -> fin de la section critique
            lock.unlock();
        }

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
