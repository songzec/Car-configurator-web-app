package adapter;

import exception.AutoException;

public interface CreateAuto {
	public void buildAuto(String filename, String fileType) throws AutoException;
	public void printAuto(String Modelname);
}
