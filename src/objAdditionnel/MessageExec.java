package objAdditionnel;

public class MessageExec implements Runnable {

    private String tache; // Un message est une chaine de caractère représentant une tache
    private int value; // La valeur sur laquel réaliser la tache

    public MessageExec(String mes, int val) {
        this.tache = mes;
        this.value = val;
    }

    public MessageExec() {
        this.tache = "Default message";
        this.value = 0;
    }

    public String toString() {
        return "tache : '" + tache + "' sur la valeur " + value;
    }

    @Override
    public void run() {
        System.out.println("Début du traitement de la tache");
        if (this.value < 0) { // Au vue des diverse tache choisi on interdit les valeurs négative
            System.out.println("Traitement de la tache impossible, valeur négative.");
        } else {
            // On réalise la bonne action en fonction de la tache demandé
            switch (tache) {
                case "fibo":
                    System.out.println("Calcule de la valeur de fibonacci au rang " + this.value + " ...");
                    System.out.println("La valeur au rang " + this.value + " de fibonacci est : " + utils.fibo(value));
                    break;
                case "factoriel":
                    System.out.println("Calcule de la valeur de " + this.value + "! ...");
                    System.out.println("La valeur de " + this.value + "! est : " + utils.fact(value));
                    break;
                case "diviseur":
                    System.out.println("Calcule des diviseurs de " + this.value + " ...");
                    System.out.print("Les diviseurs de " + this.value + " sont : ");
                    int[] div = utils.diviseur(value);
                    for (int i = 0; i < div.length; i++) {
                        if (i == div.length - 1) {
                            System.out.println(div[i]);
                        } else {
                            System.out.print(div[i] + ", ");
                        }
                        
                    }
                    break;
                case "premier":
                    System.out.println("Vérification si " + this.value + " est un nombre premier...");
                    if (utils.estPremier(value)) {
                        System.out.println("Le nombre " + this.value + " est premier !");
                    } else {
                        System.out.println("Le nombre " + this.value + " n'est pas premier !");
                    }

                    break;
                default:
                    System.out.println("Aucune tache à réaliser.");
                    break;
            }
            System.out.println("Fin de la tache.");
        }
    }
}
