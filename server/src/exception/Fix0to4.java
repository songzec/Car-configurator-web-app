package exception;

public class Fix0to4 {

	/**
	 * Automobile price in text file is missing
	 */
	public void fix0() {
		System.exit(1);
	}
	
	/**
	 * OptionSet data is missing.
	 */
	public void fix1() {
		System.exit(1);
	}

	/**
	 * Required option data is missing.
	 */
	public void fix2() {
		System.exit(1);
	}

	/**
	 * file name is missing or wrong.
	 * @param 
	 */
	public String fix3(String filename) {
		int length = filename.split("\\.").length;
		if (length == 1) {
			System.out.println("change file name from " + filename + " to " 
								+ filename + ".dat");
			return filename + ".dat";
		} else if (!filename.split("\\.")[length - 1].equals("bat")) {
			System.out.println("change file name from " + filename + " to " 
					+ filename + ".dat");
			return filename + ".dat";
		} else {
			System.out.println("Cannot fix file name, exit with code 1");
			System.exit(1);
		}
		return null;
	}

	/**
	 * number format exception
	 */
	public void fix4() {
		System.exit(1);
	}

}
