/**
 * @author Songze Chen (Andrew ID: songzec)
 */
package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import exception.AutoException;
import exception.Error;
import model.Automobile;


public class ReadSource {
	
	public Automobile buildAutoByProperties(Properties prop) {
		String carMake = prop.getProperty("make");
		if(!carMake.equals(null)) {
			float basePrice = Float.parseFloat(prop.getProperty("basePrice"));
			String modelName = prop.getProperty("model");
			int opsetNum = 0;
			while (true) {
				opsetNum++;
				String opset = "optionSet" + opsetNum;
				String optionSetName = prop.getProperty(opset);
				if (optionSetName == null) {
					break;
				}
			}
			opsetNum--;
			Automobile auto = new Automobile(opsetNum, basePrice, carMake, modelName);
			
			for (int i = 1; i <= opsetNum; i++) {
				String opset = "optionSet" + i;
				String optionSetName = prop.getProperty(opset);
				
				int optNum = 0;
				while (true) {
					optNum++;
					String opt = "optionSet" + i + "option" + optNum;

					String optionName = prop.getProperty(opt);
					
					if (optionName == null) {
						break;
					}
					auto.setOption(optionSetName,
								optionName.split(",")[0],
								Float.parseFloat(optionName.split(",")[1]));
				}
			}
			auto.print();
			return auto;
		}
		return null;
	}
	
	
	// parse and return an corresponding Automobile
//////////////////////////////File Format ///////////////////////////////////
//Focus Wagon,ZTW,1234.56,OptionSet:3									   //
//color:red$0,yellow$0												  	   //
//Transmission:automatic$0,standard$-815								   //
//Brakes/Traction Control:standard,ABS$400,ABS with Advance Trac$1625      //
/////////////////////////////////////////////////////////////////////////////
	public Automobile buildAutoObject(String filename) {
		Automobile auto = null;
		try {
			FileReader file = null;
			try {
				file = new FileReader(filename);
			} catch (FileNotFoundException e) {
				String logname = "log-" + System.currentTimeMillis();
				File log = new File(logname);
				try {
					BufferedWriter writer
								= new BufferedWriter(new FileWriter(log));
					writer.write("File named \"" + filename + "\" not found");
					writer.close();
				} catch (IOException e1) {
					e1.printStackTrace();
					System.exit(1);
				}
				AutoException e1 = 
							new AutoException(Error.FILENAME_ERROR);
				filename = e1.fix(e1.getErrorno(), filename);
				file = new FileReader(filename);
			} 
			BufferedReader buf = new BufferedReader(file);
			
			int numberOfOptionSet;
			int lineNo = 0;
			String[] line;
			line = buf.readLine().split(",");
			String autoMake = line[0];
			String autoModel = line[1];
			if (line.length < 3) {
				System.out.println("In file " + filename
						+ ", line " + (lineNo+1));
				buf.close();
				throw new AutoException(Error.MISSING_AUTOMOBILE_PRICE);
			}
			float basePrice;
			
			try {
				basePrice = Float.parseFloat(line[2]);
			} catch (NumberFormatException e) {
				System.out.println("In file " + filename
						+ ", line " + (lineNo+1));
				buf.close();
				throw new AutoException(Error.WRONG_PRICE_FORMAT);
			}
			
			numberOfOptionSet = Integer.parseInt(line[3].split(":")[1]);
			auto = new Automobile(numberOfOptionSet, basePrice,
									autoMake, autoModel);
			
			while (lineNo < numberOfOptionSet) {
				line = buf.readLine().split(":");
				if (line.length != 2) {
					System.out.println("In file " + filename
							+ ", line " + (lineNo+1));
					buf.close();
					throw new AutoException(Error.MISSING_OPTIONSET_DATA);
				}
				String opsetName = line[0];
				String[] ops = line[1].split(",");
				int optNum = ops.length;
				auto.setOpset(opsetName, optNum);
				for (int i = 0; i < optNum; i++) {
					String[] nameAndPrice = ops[i].split("\\$");
					String optName = nameAndPrice[0];
					float price = 0;
					if (nameAndPrice.length == 2) {
						price = Float.parseFloat(nameAndPrice[1]);
					} else {
						System.out.println("In file " + filename
											+ ", line " + (lineNo+1));
						buf.close();
						throw new AutoException(Error.MISSING_OPTION_DATA);
					}
					auto.setOption(opsetName, optName, price);
				}
				lineNo++;
			}
			buf.close();
		} catch (AutoException e) {
			String logname = "log-" + System.currentTimeMillis();
			File log = new File(logname);
			e.print();
			try {
				BufferedWriter writer
							= new BufferedWriter(new FileWriter(log));
				writer.write(e.toString());
				writer.close();
			} catch (IOException e1) {
				e1.printStackTrace();
				System.exit(1);
			}
			e.fix(e.getErrorno());
		} catch (IOException e) {
			if (e instanceof FileNotFoundException) {
				System.out.println("Fix failed");
				System.exit(1);
			}
			e.printStackTrace();
		}
		return auto;
	}

}
