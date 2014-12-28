package rest.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author filip
 */
public class RequestFailedExceptionLogged extends RequestFailedException {
  private static final Logger log = LoggerFactory.getLogger(RequestFailedException.class);

  public RequestFailedExceptionLogged() {
    super();
    log.error(RequestFailedException.class.toString());
  }

  public RequestFailedExceptionLogged(String message) {
    super(message);
    log.error(message);
  }

  public RequestFailedExceptionLogged(String message, Throwable cause) {
    super(message, cause);
    log.error(message, cause);
  }
}
