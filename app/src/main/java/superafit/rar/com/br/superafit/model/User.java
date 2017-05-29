package superafit.rar.com.br.superafit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * Created by ralmendro on 27/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public static final int PASSWORD_LENGTH = 6;

    private String id;

    private String login;

    private String password;

    public User() {
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginValid() {
        return !this.login.isEmpty();
    }

    public boolean isPasswordValid() {
        return !this.password.isEmpty() && this.password.length() == PASSWORD_LENGTH;
    }

    public boolean isUserLoginValid() {
        return isLoginValid() && isPasswordValid();
    }
}
