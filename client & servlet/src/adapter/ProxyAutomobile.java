package adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import client.CarModelOptionsIO;
import exception.AutoException;
import model.*;
import util.ReadSource;

public abstract class ProxyAutomobile {
	 private static LinkedHashMap<String, Automobile> autos
	 					= new LinkedHashMap<String, Automobile>();

	public void updateOptionSetName(String modelName, String opsetName, String newName) {
		if (autos.containsKey(modelName)) {
			Automobile auto = autos.get(modelName);
			auto.updateOpset(opsetName, newName);
		}
	}

	public void updateOptionPrice(String modelName, String opsetName, String optionName, float newprice) {
		if (autos.containsKey(modelName)) {
			Automobile auto = autos.get(modelName);
			auto.updateOption(optionName, optionName, newprice);
		}
	}

	public void buildAuto(String fileName, String fileType) throws AutoException {
		ReadSource rs = new ReadSource();
		Automobile auto = rs.buildAutoObject(fileName);
		autos.put(auto.getModel(), auto);
	}

	public void printAuto(String modelName) {
		if(autos.containsKey(modelName)) {
			autos.get(modelName).print();
		} else {
			System.out.println("No such model: " + modelName);
		}
	}
	
	public void buildAutoFromProperties(Properties prop) {
		ReadSource rs = new ReadSource();
		Automobile auto = rs.buildAutoByProperties(prop);
		if (auto != null) {
			autos.put(auto.getModel(), auto);
		}
		
	}


	public ArrayList<String> getModelList() {
		ArrayList<String> result = new ArrayList<String>();
		for(Entry<String, Automobile> entry : autos.entrySet()) {
			result.add(entry.getKey());
		}
		return result;
		
	}

	public Automobile getModelByRequest(String name) {
		return autos.get(name);
	}
	
	public LinkedHashMap<String, Automobile> getAutos() {
		return autos;
	}
}
