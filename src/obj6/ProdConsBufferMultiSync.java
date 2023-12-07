package obj6;

import obj1.Message;
import obj1.ProdConsBuffer;

public class ProdConsBufferMultiSync extends ProdConsBuffer {

    public ProdConsBufferMultiSync (int size, int prodTime, int consTime) {
        super(size,prodTime,consTime);
    }

    public synchronized void put(Message m, int n) {
    
        // Un producteur se met en attente tant que le buffer n'est pas en mesure d'accepter son message 
        while ((this.nbMessage + n > size)) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        int prodTime = (int)Math.floor(Math.random() * this.maxProdTime); // We randomised prodTime for better result but we keep an average of "prodTime"
        try { Thread.sleep(prodTime); } catch (InterruptedException e) { e.printStackTrace(); }

        // Ecriture de toutes les copies du message m dans le buffer
        for (int i=1; i<=n; i++) { // put n copies of message m
            System.out.println("Prod"+Thread.currentThread().getName()+" a écrit "+m.toString()+" copie n°"+Integer.toString(i)+"/"+Integer.toString(n)+" dans la case n°"+Integer.toString(in));
            this.buffer[in] = m;
            this.in = (in + 1) % size;
            this.nbMessage++;
            this.totalNbMessage++;
            notify(); // on réveille un thread à chaque écriture d'une copie
        }

        // le producteur se met en attente tant que les copies de son message n'ont pas été toutes consommées
        while ((!((MessageMultiSync)m).isMessageConsumed())) {
            System.out.println("Prod"+Thread.currentThread().getName()+" attend ");
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        // à son réveil il réveille tous les autres threads
        notifyAll();
        System.out.println("Prod"+Thread.currentThread().getName()+" libre");
    }

    public void put(Message m) {
        // on réutilise ici la fonction précédente, il s'agit d'écrire dans le buffer une seule copie du message
        this.put(m, 1);
    }

    public synchronized Message get() throws InterruptedException {
        
        System.out.println("Cons"+Thread.currentThread().getName()+" veut lire");
        
        // tant qu'il n'y a pas de message à lire dans le buffer le consommateur se bloque
        while (this.nbMessage == 0) {
           try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        int consTime = (int)Math.floor(Math.random() * this.maxConsTime); // We randomised consTime for better result but we keep an average of "consTime"
        try { Thread.sleep(consTime); } catch (InterruptedException e) { e.printStackTrace(); }

        Message res = this.buffer[out];
        MessageMultiSync resMS = (MessageMultiSync) res;

        System.out.println("Cons"+Thread.currentThread().getName()+" a lu "+res.toString()+" dans la case n°"+Integer.toString(out));

        this.out = (out + 1) % size;
        this.nbMessage--;
        // on consomme une copie du message
        resMS.copyConsumed();

        // le consommateur se bloque tant que toutes les copies du message qu'il vient de lire n'ont pas été toutes consommées
        while (!(resMS.isMessageConsumed())) {
            System.out.println("Cons"+Thread.currentThread().getName()+" attend ");
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

        // le consommateur qui consomme la dernière copie du message réveille tous les autres consommateurs
        notifyAll();
        
        return res;
    }

    @Override
    public Message[] get(int k) throws InterruptedException {
        throw new UnsupportedOperationException("Unimplemented method 'get'");
    }

}
