/**
 * BuildCarModelOptions class.
 */
package server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;

import adapter.BuildAuto;
import model.Automobile;

public class BuildCarModelOptions implements AutoServer {

	@Override
	public Automobile buildAutoFromProperties(Properties prop) {
		AutoServer as = new BuildAuto();
		return as.buildAutoFromProperties(prop);
	}

	@Override
	public ArrayList<String> getModelList() {
		AutoServer as = new BuildAuto();
		return as.getModelList();
	}

	@Override
	public Automobile getModelByRequest(String name) {
		AutoServer as = new BuildAuto();
		return as.getModelByRequest(name);
	}
	
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