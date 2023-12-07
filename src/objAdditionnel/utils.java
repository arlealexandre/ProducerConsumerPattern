package objAdditionnel;

public class utils {
    /*
     * Retourne la valeur de la suite de fibonacci au rang n
     */
    public static int fibo (int n) {
        return n < 2 ? n : fibo(n-1) + fibo(n-2);
    }

    /*
     * Retourne la valeur de n!
     */
    public static long fact (long n) {
        return n < 2 ? 1 : n * fact(n-1);
    }

    /*
     * Retourne tous les diviseurs de n
     */
    public static int [] diviseur (int n) {
        int [] diviseur = new int [0];
        for (int i = 1; i <=n; i++) {
            if (n%i == 0) {
                int[]temp = new int[diviseur.length+1];
                for (int j = 0; j < diviseur.length; j++) {
                    temp[j] = diviseur[j];
                }
                temp[diviseur.length] = i;
                diviseur = temp;
            }
        }
        return diviseur;
    }

    /*
     * Retourne vrai si n est premier et faux sinon
     */
    public static boolean estPremier (int n) {
        if (n == 2) {
            return true;
        }

        for (int i = 2; i < n; i++) {
            if (n%i == 0) {
                return false;
            }
        }

        return true;
    }
}
