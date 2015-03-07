package rest.exception;

/** @author Stanislav Valenta, 27.4.2010 */
public class LicenseCountOfUsersException extends NotAllowedException {
  private static final long serialVersionUID = -812722863949864615L;

  public LicenseCountOfUsersException() {
  }

  public LicenseCountOfUsersException(String message) {
    super(message);
  }

  public LicenseCountOfUsersException(String message, Throwable cause) {
    super(message, cause);
  }
}
