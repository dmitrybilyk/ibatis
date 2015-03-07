package rest.exception;

/** Should be thrown when configuration is not complete or invalid
 * Radek Mensik, 21.8.12
 */
public class InvalidConfigurationException extends Exception {



  public InvalidConfigurationException() {
  }

  public InvalidConfigurationException(String s) {
    super(s);
  }

  public InvalidConfigurationException(String s, Throwable throwable) {
    super(s, throwable);
  }

  public InvalidConfigurationException(Throwable throwable) {
    super(throwable);
  }
}
