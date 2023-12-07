package obj6;

import obj1.Message;

/**
 * Cette classe MessageMultiSync hérite de la première classe Message. Elle ajoute un nouveau paramètre nbToBeConsumed,
 * qui correspond au nombre de copie de ce message qui doivent être consommé.
 */
public class MessageMultiSync extends Message {

    private int nbToBeConsumed;

    public MessageMultiSync (String mes, int n) {
        super(mes);
        nbToBeConsumed = n;
    }

    // décrémente de 1 le nombre de copie consommé
    public void copyConsumed() {
        this.nbToBeConsumed--;
    }

    // un message est consommé lorsque toutes ses copies ont été consommé
    public boolean isMessageConsumed() {
        return nbToBeConsumed==0;
    }
    
}
