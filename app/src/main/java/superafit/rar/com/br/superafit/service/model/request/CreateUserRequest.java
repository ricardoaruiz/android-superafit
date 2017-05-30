package superafit.rar.com.br.superafit.service.model.request;

import java.io.Serializable;

/**
 * Created by ralmendro on 29/05/17.
 */

public class CreateUserRequest implements Serializable {

    private static final long serialVersionUID = -5074292571592562579L;

    private String login;

    private String password1;

    private String password2;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword1() {
        return password1;
    }

    public void setPassword1(String password1) {
        this.password1 = password1;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }
}
