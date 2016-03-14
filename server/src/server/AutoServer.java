/**
 * AutoServer interface to build Automobile.
 */
package server;

import java.util.ArrayList;
import java.util.Properties;

import model.Automobile;

public interface AutoServer {

	public Automobile buildAutoFromProperties(Properties prop);

	public ArrayList<String> getModelList();

	public Automobile getModelByRequest(String name);

}
