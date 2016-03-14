package exception;


public class AutoException extends Exception implements FixAuto {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4120805341625588161L;
	/**
	 * only one field
	 */
	Error error;
	/**
	 * pass Error to constructor.
	 * @param error
	 */
	public AutoException(Error error) {
		this.error = error;
	}
	/**
	 * If filename is not found, then fix it.
	 * @param errno
	 * @param filename
	 * @return new file name if fixed, old file name if errno is wrong
	 */
	public String fix(int errno, String filename) {
		Fix0to4 fix = new Fix0to4();
		if (errno == 3) {
			System.out.println(error.getMsg());
			filename = fix.fix3(filename);
		} else {
			System.out.println("errno is wrong!");
		}
		return filename;
	}
	/**
	 * @param errno
	 */
	public void fix(int errno) {
		Fix0to4 fix = new Fix0to4();
		switch(errno) {
			case 0:
				fix.fix0();
				break;
			case 1:
				fix.fix1();
				break;
			case 2:
				fix.fix2();
				break;
			case 4:
				fix.fix4();
				break;
		}
	}
	@Override
	public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("errno = ");
        builder.append(error.getErrno());
        builder.append(", errorMsg: ");
        builder.append(error.getMsg());
        return builder.toString();
    }
	
	public void print() {
		System.out.println(toString());
	}
	public int getErrorno() {
		return error.getErrno();
	}
}
