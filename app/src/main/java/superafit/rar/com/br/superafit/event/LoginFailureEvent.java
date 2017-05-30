package superafit.rar.com.br.superafit.event;

/**
 * Created by ralmendro on 28/05/17.
 */

public class LoginFailureEvent {

    private String message;

    public LoginFailureEvent() {
    }

    public LoginFailureEvent(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
