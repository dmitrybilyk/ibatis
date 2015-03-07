package rest.exception;

/**
 * Created by IntelliJ IDEA.
 * User: jelen
 * Date: 25.8.2009
 * Time: 16:27:22
 */
public class AuthorizationRequiredException extends Exception{

  public AuthorizationRequiredException() {
  }

  public AuthorizationRequiredException(Throwable cause) {
    super(cause);
  }

  public AuthorizationRequiredException(String message) {
    super(message);
  }

  public AuthorizationRequiredException(String message, Throwable cause) {
    super(message, cause);
  }
}
