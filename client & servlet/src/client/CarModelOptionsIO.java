package client;

/**
 * CarModelOptionsIO to read data from the Properties file.
 */
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Properties;

public class CarModelOptionsIO extends DefaultSocketClient {
	public Properties buildProperties(String fileName) {
		try {
			Properties prop = new Properties();
			FileInputStream in = new FileInputStream(fileName);
			prop.load(in);
			prop.list(System.out);
			return prop;
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
