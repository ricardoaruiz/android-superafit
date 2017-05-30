package superafit.rar.com.br.superafit.exception;

/**
 * Created by ralmendro on 29/05/17.
 */

public class InvalidSignupException extends RuntimeException {

    private static final long serialVersionUID = 4228241972261515804L;

    public InvalidSignupException(String message) {
        super(message);
    }

}
