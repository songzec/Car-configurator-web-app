package scale;

import adapter.BuildAuto;

public class EditOptions extends BuildAuto implements Runnable {
	private String modelName;
	private String opsetName;
	private String newOpsetName;
	private String optName;
	private float price;
	private long priority;
	private static long curr = Long.MAX_VALUE;
	/**
	 * constructor to edit option set name.
	 * @param modelName
	 * @param opsetName
	 * @param newOpsetName
	 */
	public EditOptions
			(long p, String modelName, String opsetName, String newOpsetName) {
		this.modelName= modelName;
		this.opsetName = opsetName;
		this.newOpsetName = newOpsetName;
		this.priority = p;
		if (curr > this.priority) {
			curr = this.priority;
		}
	}
	/**
	 * constructor to edit option price.
	 * @param modelName
	 * @param opsetName
	 * @param optName
	 * @param price
	 */
	public EditOptions
			(long p, String modelName, String opsetName, String optName, float price) {
		this.modelName= modelName;
		this.opsetName = opsetName;
		this.optName = optName;
		this.price = price;
		this.priority = p;
		if (curr > this.priority) {
			curr = this.priority;
		}
	}
	
	@Override
	public void run() {
		while (priority > curr);
		synchronized (this.getAutos().get(modelName)) {
			if (this.optName != null) {
				updateOption
					(this.modelName, this.opsetName, this.optName, this.price);
			} else if (this.opsetName != null){
				updateOptionSetName
					(this.modelName, this.opsetName, this.newOpsetName);
			}
			this.printAuto(this.modelName);
			curr++;
		}
	}
}