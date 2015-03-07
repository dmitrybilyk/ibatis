package rest.exception;

/** @author Stanislav Valenta, 20.5.2009 */
public class LoginFailedException extends Exception {
  private static final long serialVersionUID = -2109320575767946358L;

  private Status myStatus = Status.NOT_LOCKED;

  public static enum Status {
    NOT_LOCKED,
    ABOUT_TO_BE_LOCKED,
    LOCKED,
    USERNAME_NOT_FOUND,
    PASSWORD_INCORRECT,
    PASSWORD_EXPIRED,
    ACOUNT_DISABLED
  }

  public LoginFailedException() {
    super();
  }

  public LoginFailedException(String message) {
		super(message);
	}

	public LoginFailedException(String message, Throwable cause) {
    super(message, cause);
  }

  public LoginFailedException(String message, Status status) {
    super(message);
    setLockStatus(status);
  }

  public LoginFailedException(String message, Throwable cause, Status status) {
    super(message, cause);
    setLockStatus(status);
  }

	public void setLockStatus(Status status) {
	  myStatus = status;
	}

	public Status getLockStatus() {
	  return myStatus;
	}
}
