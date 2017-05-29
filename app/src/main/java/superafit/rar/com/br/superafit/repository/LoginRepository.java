package superafit.rar.com.br.superafit.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

import superafit.rar.com.br.superafit.model.User;

/**
 * Created by ralmendro on 28/05/17.
 */

public class LoginRepository {

    private static final String LOGIN_PREFERENCE = "superafit.rar.com.br.superafit.repository.LoginRepository";
    private static final String USER_LOGGED = "user";

    private Context context;

    private ObjectMapper mapper;

    public LoginRepository(Context context) {
        this.context = context;
        this.mapper = new ObjectMapper();
    }

    public void login(User user) {
        SharedPreferences.Editor editor = getPreference().edit();
        try {
            String userJson = mapper.writeValueAsString(user);
            editor.putString(USER_LOGGED, userJson);
        } catch (JsonProcessingException e) {
            editor.putString(USER_LOGGED, null);
        }
        editor.commit();
    }

    public void logoff() {
        SharedPreferences.Editor editor = getPreference().edit();
        editor.remove(USER_LOGGED);
        editor.apply();
    }

    public boolean isLogged() {
        return getUserLogged() != null;
    }

    public User getUserLogged() {
        try {
            String userJson = getPreference().getString(USER_LOGGED, null);
            if(userJson == null) {
                return null;
            }
            return mapper.readValue(userJson, User.class);
        } catch (IOException e) {
            return null;
        }
    }

    private SharedPreferences getPreference() {
        return context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE);
    }

}
