/**
 * @author Songze Chen (Andrew ID: songzec)
 */
package driver;

import adapter.BuildAuto;
import exception.AutoException;

public class Driver {

	public static void main(String[] args) throws AutoException {
		BuildAuto a = new BuildAuto();
		a.buildAuto("propfile.txt", "properties");
		a.printAuto("a");
	}
}
