package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.LoginResponse;

/**
 * Created by ralmendro on 27/05/17.
 */

public class LoginResponseEvent {

    private LoginResponse loginResponse;

    private String message;

    public LoginResponseEvent(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public LoginResponseEvent(String message) {
        this.message = message;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasErrorMessage() {
        return this.message != null;
    }
}
