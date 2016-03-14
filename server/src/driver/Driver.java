/**
 * @author Songze Chen (Andrew ID: songzec)
 */
package driver;

import adapter.BuildAuto;
import database.Database;
import exception.AutoException;

public class Driver {

	public static void main(String[] args) throws AutoException {

		
		Database db = new Database();
		db.initialize();
		BuildAuto a = new BuildAuto();
		a.buildAuto("propfile.txt", "properties");
		a.printAuto("a");
		
		a.buildAuto("propfile1.txt", "properties");
		a.printAuto("b");
		
	}
}
