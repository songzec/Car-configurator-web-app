/**
 * @author Songze Chen (Andrew ID: songzec)
 */
package model;

import java.io.Serializable;
import java.util.ArrayList;

class OptionSet implements Serializable {

	private static final long serialVersionUID = -5034051678397008891L;
	private ArrayList<Option> opts;
	private String name;
	private Option choice;
	protected OptionSet() {
		name = null;
		opts = new ArrayList<Option>();
	}
	protected OptionSet(String n) {
		name = n;
		opts = new ArrayList<Option>();
	}
	protected OptionSet(String n, int size) {
		opts = new ArrayList<Option>();
		for(int i = 0; i < size; i++) {
			opts.add(new Option());
		}
		name = n;
	}
	
	/////////////////// getters and setters ///////////////////
	protected ArrayList<Option> getOpt() {
		return opts;
	}
	protected void setOpt(Option newOpt) {
		for (int i = 0; i < opts.size(); i++) {
			if (opts.get(i).name != null
					&& opts.get(i).name.equals(newOpt.name)) {
				opts.get(i).setName(newOpt.name);
				return;
			} else if (opts.get(i).name == null) {
				opts.get(i).setName(newOpt.name);
				opts.get(i).setPrice(newOpt.price);
				return;
			}
		}
		opts.add(newOpt);
	}
	protected void setOpt(String optName, float price) {
		setOpt(new Option(optName, price));
	}
	protected String getName() {
		return name;
	}
	protected void setName(String name) {
		this.name = name;
	}
	/////////////////// getters and setters end ///////////////////
	
	// find function
	protected boolean hasOption(String name) {
		for (Option o : opts) {
			if (o.name.equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	// update only if the old name exists
	protected boolean updateOption(String oldOptName,
									String newOptName, float price) {
		for (int i = 0; i < opts.size(); i++) {
			if (opts.get(i).name.equals(oldOptName)) {
				opts.get(i).setName(newOptName);
				opts.get(i).setPrice(price);
				return true;
			}
		}
		return false;
	}
	
	// set the deleted name as null
	protected void deleteOption(String name) {
		for (int i = 0; i < opts.size(); i++) {
			if (opts.get(i).name.equals(name)) {
				opts.get(i).setName(null);
				opts.get(i).setPrice(0);
			}
		}
	}
	
	protected Option getOptionChoice() {
		return choice;
	}
	/**
	 * print out if there is no such option name.
	 * @param optionName
	 */
	protected void setOptionChoice(String optionName) {
		if (hasOption(optionName)) {
			for (Option o : opts) {
				if (o.name.equals(optionName)) {
					choice = o;
					return;
				}
			}
		} else {
			System.out.println("no such option named " + optionName);
		}
	}
	
	@Override
	public String toString() {
		StringBuilder s = new StringBuilder();
		s.append(name);
		for (Option o : opts) {
			if (o.name != null) {
				s.append(" ").append(o.name);
			}
		}
		return s.toString();
	}
	/**
	 * Option inner class
	 */
	protected class Option implements Serializable {

		private static final long serialVersionUID = -6727726847050597579L;
		private String name;
		private float price;
		protected Option() {
			name = null;
		}
		protected Option(String name, float price) {
			this.name = name;
			this.price = price;
		}
		protected void setName(String name) {
			this.name = name;
		}
		protected String getName() {
			return name;
		}
		protected float getPrice() {
			return price;
		}
		protected void setPrice(float price) {
			this.price = price;
		}
		@Override
		public String toString() {
			if (name != null) {
				StringBuilder s = new StringBuilder();
				s.append(name).append(" $").append(price);
				return s.toString();
			}
			return "";
		}
	}

}
