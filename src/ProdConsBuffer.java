public class ProdConsBuffer implements IProdConsBuffer {
    Message[] buffer;
    int nbMessage;
    int totalNbMessage;
    int in;
    int out;
    int size;

    ProdConsBuffer (int size) {
        this.buffer = new Message[size];
        this.nbMessage = 0;
        this.in = 0;
        this.out = 0;
        this.size = size;
    }

    public synchronized void put(Message m) {
        while (this.nbMessage == size) {
            try { wait(); } catch (InterruptedException e) { e.printStackTrace(); }
        }

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

        Message res = this.buffer[out];
        this.out = (out + 1) % size;
        this.nbMessage--;
        
        notifyAll();
        
        return res;
    }

    public int nmsg() {
        return this.nbMessage;
    }

    public int totmsg() {
        return this.totalNbMessage;
    }
}
