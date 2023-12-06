package obj6;

import obj1.Message;

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
