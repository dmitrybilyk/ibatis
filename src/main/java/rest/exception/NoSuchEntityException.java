package rest.exception;

/** @author Stanislav Valenta, 20.5.2009 */

/**
 * throws if entity is not found in database
 */

public class NoSuchEntityException extends Exception {
	private static final long serialVersionUID = -3245826612238766361L;

  public NoSuchEntityException() {
    super();
  }

  public NoSuchEntityException(String message) {
		super(message);
	}

	public NoSuchEntityException(String message, Throwable cause) {
		super(message, cause);
	}
}
