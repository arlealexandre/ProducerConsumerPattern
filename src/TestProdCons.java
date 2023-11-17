import java.io.FileInputStream;
import java.io.IOException;
import java.util.InvalidPropertiesFormatException;
import java.util.Properties;

public class TestProdCons {

    public static void main(String[] args) throws InvalidPropertiesFormatException, IOException {

        Properties properties = new Properties();
        properties.loadFromXML(new FileInputStream("options.xml"));

        int nProd = Integer.parseInt(properties.getProperty("nProd"));
        int nCons = Integer.parseInt(properties.getProperty("nCons"));

    }
}
