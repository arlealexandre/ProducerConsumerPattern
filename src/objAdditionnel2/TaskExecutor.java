package objAdditionnel2;
import java.util.ArrayList;

import objAdditionnel1.MessageExec;

public class TaskExecutor {
    MessageExec[] taches;
    ArrayList<Executor> executeur;
    int available;

    TaskExecutor () {
        this.taches = new MessageExec[0];
        this.executeur = new ArrayList<>();
        this.available = 0;
    }

    public synchronized void askTask (MessageExec mes) {
        if (available == 0) {
            Executor c = new Executor(mes);
            c.setName(String.valueOf(executeur.size()));
            executeur.add(c);
            c.start();
        } else {
            for (Executor e : executeur) {
                if (e.isAvailable()) {
                    e.start(mes);
                }
            }
        }
    }
}
