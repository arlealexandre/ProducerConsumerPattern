package objAdditionnel2;

import java.util.ArrayList;

import objAdditionnel1.MessageExec;

/*
 * Le gérant des taches
 */
public class TaskExecutor {
    MessageExec[] taches; // Toutes les taches qu'il est actuellement entrain de gérer
    ArrayList<Executor> executeur; // Tous les executeurs qu'il gère
    int available; // Le nombre d'executeur qu'il a de disponible (inutile dans notre cas car nous
                   // n'avons pas implémenté le fait qu'un thread reste en vie 3 secondes après la
                   // fin de sadernière execution) mais utile dans une future implémentation avec
                   // cette fonctionnalité en plus qu'il serait judicieux d'implémenter en
                   // utilisant la classe Timer

    TaskExecutor() {
        this.taches = new MessageExec[0];
        this.executeur = new ArrayList<>();
        this.available = 0;
    }

    /*
     * Cette fonction supprime l'intéret de la variable available en effet, nous
     * supprimons les threads dès qu'ils deviennent inactif (quand ils ont finit
     * leur tache) il ne seront donc jamais "available" et donc le compteur de
     * thread available de cette classe restera à 0.
     */
    public synchronized void updateExecuteur() {
        for (Executor e : this.executeur) {
            if (!e.isAlive()) {
                this.executeur.remove(e);
            }
        }
    }

    public synchronized void askTask(MessageExec mes) throws InterruptedException {
        this.updateExecuteur();

        if (available == 0 && available < 10) { // Lorsque aucun thread est disponible on en crée qui prend la tache et
                                                // l'execute
            Executor c = new Executor(mes);
            c.setName(String.valueOf(executeur.size()));
            executeur.add(c);
            c.start();
        } else if (available > 10) { // lorsqu'il y a déjà trop de tache en cours on lève une erreur, on pourrait pu
                                     // crée une liste "infini" de tache en attente qu'ont donnerais a un thread dès
                                     // qu'il devient avaiable (ce qui n'est aussi pas possible avec notre
                                     // implémentation sans le timer des 3 secondes)
            throw new InterruptedException("Trop de taches en paralleles");
        } else {// Sinon si au moins un thread est disponible on lance un des threads disponible
                // sur la tache à réaliser
            for (Executor e : executeur) {
                if (e.isAvailable()) {
                    e.startNewTask(mes);
                }
            }
        }
    }
}
