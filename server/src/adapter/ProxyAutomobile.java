package adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Properties;

import database.Database;
import exception.AutoException;
import model.*;
import server.BuildCarModelOptions;
import util.ReadSource;

public abstract class ProxyAutomobile {
	 private static LinkedHashMap<String, Automobile> autos
	 					= new LinkedHashMap<String, Automobile>();

	public void updateOptionSetName(String modelName, String opsetName, String newName) {
		if (autos.containsKey(modelName)) {
			Automobile auto = autos.get(modelName);
			auto.updateOpset(opsetName, newName);
			Database db = new Database();
			db.updateAutomobile(modelName, auto);
		}
	}

	public void updateOption(String modelName, String opsetName, String optionName, float newprice) {
		if (autos.containsKey(modelName)) {
			Automobile auto = autos.get(modelName);
			auto.updateOption(optionName, optionName, newprice);
			Database db = new Database();
			db.updateAutomobile(modelName, auto);
		}
	}
	
	public void deleteAuto(String modelName) {
		autos.remove(modelName);
		Database db = new Database();
		db.deleteAutomobile(modelName);
	}
	
	public void buildAuto(String fileName, String fileType) throws AutoException {
		Automobile auto;
		if (fileType.equals("properties")) {
			BuildCarModelOptions bcm = new BuildCarModelOptions();
			auto = buildAutoFromProperties(bcm.buildProperties(fileName));
			autos.put(auto.getModel(), auto);
		} else {
			ReadSource rs = new ReadSource();
			auto = rs.buildAutoObject(fileName);
			autos.put(auto.getModel(), auto);
		}
		Database db = new Database();
		db.insertAutomobile(auto);
	}

	public void printAuto(String modelName) {
		if(autos.containsKey(modelName)) {
			autos.get(modelName).print();
		} else {
			System.out.println("No such model: " + modelName);
		}
	}
	
	public Automobile buildAutoFromProperties(Properties prop) {
		ReadSource rs = new ReadSource();
		Automobile auto = rs.buildAutoByProperties(prop);
		return auto;
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
