package superafit.rar.com.br.superafit.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ralmendro on 27/05/17.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class User implements Serializable {

    public static final int PASSWORD_LENGTH = 6;

    private String id;

    private String login;

    private String password;

    private String confirmPassword;

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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public boolean isLoginValid() {
        return this.login != null &&
                !this.login.isEmpty() &&
                isEmailValid(this.login);
    }

    public boolean isPasswordValid() {
        return this.password != null && !this.password.isEmpty()
                && this.password.length() == PASSWORD_LENGTH;
    }

    public boolean isConfirmPasswordValid() {
        return this.confirmPassword != null && !confirmPassword.isEmpty()
                && confirmPassword.length() == PASSWORD_LENGTH;
    }

    public boolean isPasswordsAreEquals() {
        return this.password.equals(this.confirmPassword);
    }

    public boolean isUserLoginValid() {
        return isLoginValid() && isPasswordValid();
    }

    private boolean isEmailValid(String email)
    {
        String regExpn =
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                        +"((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        +"([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        +"[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                        +"([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$";

        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(regExpn,Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);

        if(matcher.matches())
            return true;
        else
            return false;
    }
}
