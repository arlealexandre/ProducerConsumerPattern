package obj6;

import obj1.Message;

/*
 * This MessageMultiSync class extends Message class. It add a new parameter nbToBeConsumed that corresponds 
 * to the number of copy of the current message that must be consumed by consumers
 */
public class MessageMultiSync extends Message {

    private int nbToBeConsumed;

    public MessageMultiSync (String mes, int n) {
        super(mes);
        nbToBeConsumed = n;
    }

    public void copyConsumed() {
        this.nbToBeConsumed--;
    }

    public boolean isMessageConsumed() {
        return nbToBeConsumed==0;
    }
    
}
