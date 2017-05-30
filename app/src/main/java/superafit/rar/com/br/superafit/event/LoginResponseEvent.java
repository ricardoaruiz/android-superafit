package superafit.rar.com.br.superafit.event;

import superafit.rar.com.br.superafit.service.model.response.LoginResponse;

/**
 * Created by ralmendro on 27/05/17.
 */

public class LoginResponseEvent {

    private LoginResponse loginResponse;

    public LoginResponseEvent(LoginResponse loginResponse) {
        this.loginResponse = loginResponse;
    }

    public LoginResponse getLoginResponse() {
        return loginResponse;
    }

}
