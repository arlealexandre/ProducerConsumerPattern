package obj1;

public class ProdConsBufferMonitor extends ProdConsBuffer {

    public ProdConsBufferMonitor(int size, int prodTime, int consTime) {
        super(size, consTime, consTime);
    }

    public synchronized void put(Message m) {

        /*
         * Implémentation basé sur le TGA
         */

        while (this.nbMessage == size) { // Garde : tant que le buffer est plein on attend avant d'écrire
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int prodTime = (int) Math.floor(Math.random() * this.maxProdTime); // Attente d'une durée moyenne de prodTime
        try {
            Thread.sleep(prodTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // On garde une trace
        System.out.println("Prod" + Thread.currentThread().getName() + " a écrit " + m.toString() + " dans la case n°"
                + Integer.toString(in));

        // Acutalisation du buffer
        this.buffer[in] = m;
        this.in = (in + 1) % size;
        this.nbMessage++;
        this.totalNbMessage++;

        // Réveil des autres threads
        notifyAll();
    }

    public synchronized Message get() throws InterruptedException {

        /*
         * Implémentation basé sur le TGA
         */

        while (this.nbMessage == 0) { // Garde : tant que le buffer est vide on attend avant de lire
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        int consTime = (int) Math.floor(Math.random() * this.maxConsTime); // Attente d'une durée moyenne de prodTime
        try {
            Thread.sleep(consTime);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Message res = this.buffer[out];

        // On garde une trace
        System.out.println("Cons" + Thread.currentThread().getName() + " a lu " + res.toString() + " dans la case n°"
                + Integer.toString(out));

        // Acutalisation du buffer
        this.out = (out + 1) % size;
        this.nbMessage--;

        // Réveil des autres threads
        notifyAll();

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
