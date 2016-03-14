package exception;

public enum Error {
	MISSING_AUTOMOBILE_PRICE(0, "Automobile price in text file is missing"),
    MISSING_OPTIONSET_DATA(1, "OptionSet data is missing"),
    MISSING_OPTION_DATA(2, "Option data is missing"),
    FILENAME_ERROR(3, "File name is missing or wrong"),
    WRONG_PRICE_FORMAT(4, "Base price should be number");

    private int errno;
    private String msg;

    Error(int errno, String msg) {
      this.errno = errno;
      this.msg = msg;
    }

    public int getErrno() {
      return this.errno;
    }

    public String getMsg() {
      return this.msg;
    }
}
