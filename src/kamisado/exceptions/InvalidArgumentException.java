package kamisado.exceptions;

public class InvalidArgumentException extends Exception {
	private static final long serialVersionUID = 1650861220381223799L;
	private Object arg;

	public InvalidArgumentException(String arg0, Object arg) {
		super(arg0);
		// TODO Auto-generated constructor stub
		this.arg = arg;
	}

	public Object getArg() {
		return arg;
	}

}
