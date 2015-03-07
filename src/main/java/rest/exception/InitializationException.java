package rest.exception;

/** @author Stanislav Valenta, 20.5.2009 */
public class InitializationException extends Exception {
	private static final long serialVersionUID = 8686329961117217302L;
  public static final String INIT_ERROR_MESSAGE = "Not properly initialized";


  public InitializationException() {
  }


  public InitializationException(String message) {
    super(message);
  }


  public InitializationException(String message, Throwable cause) {
    super(message, cause);
  }


  public InitializationException(Throwable cause) {
    super(cause);
  }
}
