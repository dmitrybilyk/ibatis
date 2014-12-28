package rest.exception;

public class NotAllowedException extends Exception {
	private static final long serialVersionUID = -3245826612238766361L;

  public NotAllowedException() {
    super();
  }

  public NotAllowedException(String message) {
		super(message);
	}

	public NotAllowedException(String message, Throwable cause) {
		super(message, cause);
	}
}
