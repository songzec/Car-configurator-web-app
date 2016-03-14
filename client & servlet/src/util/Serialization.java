package util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import model.Automobile;

public class Serialization {
	Automobile[] autos;
	public Serialization(Automobile[] autos) {
		this.autos = autos;
	}

	public File output(String filename) {
		File file = new File(filename);
		try {
			ObjectOutputStream out = 
					new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(autos);
			out.close();
		} catch (Exception e) { 
			System.out.print("Error: " + e);
			System.exit(1);
		}
		return file;
	}

	public void readFile(File file) {
		try {
			ObjectInputStream in = 
					new ObjectInputStream(new FileInputStream(file));
			Automobile[] newAutos = (Automobile[]) in.readObject();
			for (Automobile s : newAutos) {
				s.print();
			}
			in.close();
		} catch (Exception e) { 
			System.out.print("Error: " + e);
			e.printStackTrace();
			System.exit(1);
		}
	}

}
