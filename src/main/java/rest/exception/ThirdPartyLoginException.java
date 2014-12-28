/**
 *
 */
package rest.exception;

/**
 *
 * Karel Tejnora <karel.tejnora@zoomint.com>; Zoom International, s. r. o.
 * Created At: 10:52:10 AM
 */
public class ThirdPartyLoginException extends LoginFailedException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public ThirdPartyLoginException() {
        this("", null, null);
    }

    /**
     * @param message
     * @param status
     */
    public ThirdPartyLoginException(String message, Status status) {
        this( message, null, status );
    }

    /**
     * @param message
     */
    public ThirdPartyLoginException(String message) {
        this( message, null, null );
    }

    /**
     * @param message
     * @param cause
     */
    public ThirdPartyLoginException(String message, Throwable cause) {
        this( message, cause, null );
    }

    /**
     * @param message
     * @param cause
     * @param status
     */
    public ThirdPartyLoginException(String message, Throwable cause, Status status) {
        super( message, cause, Status.NOT_LOCKED );
    }



}
