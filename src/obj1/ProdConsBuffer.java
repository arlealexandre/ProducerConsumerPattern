package obj1;

public abstract class ProdConsBuffer implements IProdConsBuffer {

    protected Message[] buffer;
    protected int nbMessage;
    protected int totalNbMessage;
    
    protected int in;
    protected int out;
    
    protected int size;

    protected int maxProdTime;
    protected int maxConsTime;

    /* 
     * Parameter : 
     * - size : The size of the buffer
     * - prodTime : The average time a production of a message take
     * - consTime : The average time a consommation of a message take 
     */
    public ProdConsBuffer (int size, int prodTime, int consTime) {
        this.buffer = new Message[size];
        this.nbMessage = 0;
        this.in = 0;
        this.out = 0;
        this.size = size;
        this.maxProdTime = (prodTime * 2 ) - 1;
        this.maxConsTime = (consTime * 2) - 1;
    }

    public abstract void put(Message m) throws InterruptedException;

    public abstract Message get() throws InterruptedException;

    public int nmsg() {
        return this.nbMessage;
    }

    public int totmsg() {
        return this.totalNbMessage;
    }

    public void put(Message m, int n) {
    }
}
