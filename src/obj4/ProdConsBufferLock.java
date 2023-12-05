package obj4;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferLock extends ProdConsBuffer {

    ReentrantLock lock;
    Condition notEmpty, notFull;

    public ProdConsBufferLock (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
        this.lock = new ReentrantLock();
        this.notEmpty = lock.newCondition();
        this.notFull = lock.newCondition();
    }

    public void put(Message m) {
    
        lock.lock();

        try {

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
            
            notEmpty.signal();

        } finally {
            lock.unlock();
        }

    }

    public Message get() throws InterruptedException {

        lock.lock();

        try {
            while (this.nbMessage == 0) {
            notEmpty.await();
            }
     
            int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
            try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

            Message res = this.buffer[out];

            System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

            this.out = (out + 1) % size;
            this.nbMessage--;

            notFull.signal();
            
            return res;
        } finally {
            lock.unlock();
        }

    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
