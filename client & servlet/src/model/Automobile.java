/**
 * @author Songze Chen (Andrew ID: songzec)
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

import model.OptionSet.Option;

public class Automobile implements Serializable { 

	private static final long serialVersionUID = -7780408620114944970L;
	private String make;
	private String model;
	private float baseprice;
	private ArrayList<OptionSet> opsets;
	
	/**
	 * Constructor without arguments: 
	 * set opsets's size = 0, base price = 0, name = null
	 */
	public Automobile() {
		new Automobile(0, 0, null, null);
	}

	public Automobile(int size, float baseprice, String make, String model) {
		opsets = new ArrayList<OptionSet>();
		for(int i = 0; i < size; i++) {
			opsets.add(new OptionSet());
		}
		this.make = make;
		this.model = model;
		this.baseprice = baseprice;
	}
	
	/////////////////// getters and setters ///////////////////

	/**
	 * simply return make.
	 * @return make
	 */
	public synchronized String getMake() {
		return make;
	}
	/**
	 * simply set make.
	 * @param new make
	 */
	public synchronized void setMake(String make) {
		this.make = make;
	}
	/**
	 * simply return model.
	 * @return model
	 */
	public synchronized String getModel() {
		return model;
	}
	/**
	 * simply set model.
	 * @param new model
	 */
	public synchronized void setModel(String model) {
		this.model = model;
	}
	/**
	 * simply return base price.
	 * @return base price
	 */
	public synchronized float getBaseprice() {
		return baseprice;
	}
	/**
	 * simply set base price.
	 * @param baseprice
	 */
	public synchronized void setBaseprice(float baseprice) {
		this.baseprice = baseprice;
	}
	/**
	 * simply get option sets
	 * @return ArrayList of option sets
	 */
	public synchronized ArrayList<OptionSet> getOpsets() {
		return opsets;
	}
	
	public synchronized ArrayList<String> getOpsetsName() {
		ArrayList<String> opsetNames = new ArrayList<String>();
		for (OptionSet opset : opsets) {
			if (opset != null) {
				opsetNames.add(opset.getName());
			}
		}
		return opsetNames;
	}
	
	public synchronized String getOptionSetName(int index) {
		return opsets.get(index).getName();
	}
	
	public synchronized ArrayList<String> getOptionList(String opsetName) {
		if (opsetName == null) {
			return null;
		}
		ArrayList<String> optionList = new ArrayList<String>();
		for (Option opt : getOptionSet(opsetName).getOpt()) {
			if (opt != null) {
				optionList.add(opt.getName());
			}
		}
		return optionList;
	}
	/**
	 * get option set by name.
	 * @param optionSetName
	 * @return OptionSet
	 */
	public synchronized OptionSet getOptionSet(String optionSetName) {
		int index = findOpset(optionSetName);
		if (index != -1) {
			return opsets.get(index);
		}
		System.out.println("no such option set named " + optionSetName + ".");
		return null;
	}
	/**
	 * if there is no opsets has the same name, add it
	 * @param newopsets
	 */
	public synchronized void setOpset(OptionSet newOpset) {
		int index = findOpset(newOpset.getName());
		if (index != -1) {
			opsets.set(index, newOpset);
		} else {
			opsets.add(newOpset);
			System.out.println("new option set " + newOpset + " is created.");
		}
	}
	public synchronized void setOpset(String opsetsName, int numOfOption) {
		OptionSet opset = new OptionSet(opsetsName, numOfOption);
		setOpset(opset);
	}
	
	// if there's no such opsetName, create one and call it again
	public synchronized void setOption(String opsetName,
							String optName, float price) {
		OptionSet opset = getOptionSet(opsetName);
		if (opset != null) {
			opset.setOpt(optName, price);
		} else {
			opsets.add(new OptionSet(opsetName));
			System.out.println("new option set " + opsetName + " is created.");
			setOption(opsetName, optName, price);
		}
	}
	/////////////////// getters and setters end ///////////////////
	
	// find functions
	/**
	 * 
	 * @param optionSetName
	 * @return index of optionSetName, -1 if not found
	 */
	public synchronized int findOpset(String optionSetName) {
		for (int i = 0; i < opsets.size(); i++) {
			if (opsets.get(i).getName() != null
					&& opsets.get(i).getName().equals(optionSetName)) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * return the according option set's index
	 * @param name
	 * @return option set's index
	 */
	public synchronized int findOption(String name) {
		for (int i = 0; i < this.opsets.size(); i++) {
			if (opsets.get(i).hasOption(name)) {
				return i;
			}
		}
		return -1;
	}
	// find functions end
	
	// update functions: only update if old name exists 
	/**
	 * replace option set with new option set.
	 * @param opsetName
	 * @param newopsets
	 */
	public synchronized void updateOpset(String opsetName, OptionSet newopsets) {
		int index = findOpset(opsetName);
		if (index != -1) {
			opsets.set(index, newopsets);
		}
	}
	/**
	 * replace option set name with new name.
	 * @param opsetName
	 * @param newName
	 */
	public synchronized void updateOpset(String opsetName, String newName) {
		int index = findOpset(opsetName);
		if (index != -1) {
			opsets.get(index).setName(newName);
		}
	}
	/**
	 * replace option with a new one.
	 * @param oldOptName
	 * @param newOptName
	 * @param price
	 */
	public synchronized void updateOption(String oldOptName,
								String newOptName, float price) {
		int opsetsIndex = findOption(oldOptName);
		if (opsetsIndex != -1) {
			opsets.get(opsetsIndex).updateOption(oldOptName, newOptName, price);
		}
	}
	// update functions end
	
	// delete functions: set the deleted name as "BLANK"
	public synchronized void deleteOpset(String name) {
		opsets.remove(getOptionSet(name));
	}
	
	public synchronized void deleteOption(String name) {
		int opsetsIndex = findOption(name);
		if (opsetsIndex != -1) {
			opsets.get(opsetsIndex).deleteOption(name);
			return;
		}
	}
	/////// delete functions end ///////
	
	public synchronized String getOptionChoice (String optionSetName) {
		OptionSet opset = getOptionSet(optionSetName);
		if (opset == null) {
			return "Error: No such opsion set";
		} else {
			if (opset.getOptionChoice() != null) {
				return opset.getOptionChoice().getName();
			}
			return "No choice for this set yet.";
		}
	}
	
	/**
	 * 
	 * @param optionSetName
	 * @return the option price in the option set, return 0 if no choice
	 * in the option set.
	 */
	public synchronized float getOptionChoicePrice (String optionSetName) {
		OptionSet opset = getOptionSet(optionSetName);
		if (opset == null) {
			System.out.println("Error: No such opsion set.");
			return 0;
		} else {
			if (opset.getOptionChoice() != null) {
				return opset.getOptionChoice().getPrice();
			}
			System.out.println("No choice yet for this option set.");
			return 0;
		}
	}
	
	public synchronized void setOptionChoice(String optionSetName, String optionName) {
		OptionSet opset = getOptionSet(optionSetName);
		if (opset == null) {
			System.out.println("Error: No such opsion set.");
		} else {
			opset.setOptionChoice(optionName);
		}
	}
	/**
	 * simply return base price + every model price
	 * @return base price + every model price
	 */
	public synchronized float getTotalPrice() {
		float totalPrice = baseprice;
		for (OptionSet opset : opsets) {
			if (opset.getOptionChoice() != null) {
				totalPrice += opset.getOptionChoice().getPrice();
			}
		}
		return totalPrice;
	}
	
	@Override
	public synchronized String toString() {
		StringBuilder s = new StringBuilder();
		s.append("Make: ").append(make).append(", Model: ").append(model)
		 .append(", base price: $").append(baseprice);
		for (OptionSet opset : opsets) {
			if (opset.getName() != null) {
				s.append("\n").append(opset.getName()).append("(");
				
				for (int i = 0; i < opset.getOpt().size() - 1; i++) {
					if (opset.getOpt().get(i) != null) {
						s.append(opset.getOpt().get(i)).append(", ");
					}
				}
				s.append(opset.getOpt().get(opset.getOpt().size() - 1)).append(")");
			}
		}
		s.append("\n");
		return s.toString();
	}
	
	public synchronized void print() {
		System.out.println(toString());
	}


}

