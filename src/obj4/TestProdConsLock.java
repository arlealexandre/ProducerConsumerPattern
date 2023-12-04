package obj4;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

import obj1.Consumer;
import obj1.Producer;

public class TestProdConsLock {

    public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {

        ArrayList<Thread> threadsCons = new ArrayList<>();
        ArrayList<Thread> threadsProd = new ArrayList<>();

        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream("options.xml"));

        int nProd = Integer.parseInt(properties.getProperty("nProd"));
        int nCons = Integer.parseInt(properties.getProperty("nCons"));
        int sizeB = Integer.parseInt(properties.getProperty("bufSz"));
        int prodTime = Integer.parseInt(properties.getProperty("prodTime"));
        int consTime = Integer.parseInt(properties.getProperty("consTime"));
        int minProd = Integer.parseInt(properties.getProperty("minProd"));
        int maxProd = Integer.parseInt(properties.getProperty("maxProd"));
        
        System.out.println("BUFFER SIZE = "+Integer.toString(sizeB)+"\n");

        // Création du buffer
        ProdConsBufferLock buffer = new ProdConsBufferLock(sizeB,prodTime,consTime);

        // Création des producteurs
        for (int i=0; i<nProd; i++) {
            Producer p = new Producer(buffer,minProd,maxProd);
            p.setName(String.valueOf(i));
            threadsProd.add(p);
        }

        // Création des consommateurs
        for (int i=0; i<nCons; i++) {
            Consumer c = new Consumer(buffer);
            c.setName(String.valueOf(i));
            threadsCons.add(c);
        }

        for (Thread t : threadsProd) {
            t.start();
        }

        for (Thread t : threadsCons) {
            t.setDaemon(true);
            t.start();
        }

        for (Thread t : threadsProd) {
            try {t.join();} catch (InterruptedException e) {e.printStackTrace();}
        }

        System.out.println("All threads have finished. Terminating...");

    }
}
