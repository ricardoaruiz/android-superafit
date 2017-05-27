package superafit.rar.com.br.superafit.exception;

/**
 * Created by ralmendro on 27/05/17.
 */

public class InvalidLoginException extends RuntimeException {

    public InvalidLoginException(String message) {
        super(message);
    }

    public InvalidLoginException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidLoginException(Throwable cause) {
        super(cause);
    }

}
