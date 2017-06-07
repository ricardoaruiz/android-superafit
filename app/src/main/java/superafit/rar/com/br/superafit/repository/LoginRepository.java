package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.uitls.JsonUtil;

/**
 * Created by ralmendro on 28/05/17.
 */

public class LoginRepository extends BaseSharedPreferenceRepository {

    private static final String USER_LOGGED = "user";

    private Context context;

    public LoginRepository(Context context) {
        super(context);
        this.context = context;
    }

    @Override
    String getPreferenceKey() {
        return "superafit.rar.com.br.superafit.repository.LoginRepository";
    }

    public void login(User user) {
       if(user != null) {
           String userJson = JsonUtil.toJson(user);
           if (userJson != null) {
               save(userJson, USER_LOGGED);
           }
       }
    }

    public void logoff() {
        remove(USER_LOGGED);
    }

    public boolean isLogged() {
        return getUserLogged() != null;
    }

    public User getUserLogged() {
        String userJson = getValue(USER_LOGGED, null);
        if(userJson == null) {
            return null;
        }
        return (User) JsonUtil.fromJson(userJson, User.class);
    }

}
