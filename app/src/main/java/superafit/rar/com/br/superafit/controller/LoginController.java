package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.event.LoginFailureEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.request.LoginRequest;
import superafit.rar.com.br.superafit.service.model.response.LoginResponse;

/**
 * Created by ralmendro on 27/05/17.
 */

public class LoginController {

    private Context context;

    private LoginRepository loginRepository;

    public LoginController(Context context) {
        this.context = context;
        this.loginRepository = new LoginRepository(context);
    }

    public void doLogin(User user) {
        validateLogin(user);
        callLoginApi(user);
    }

    public void logoff() {
        loginRepository.logoff();
    }

    private void validateLogin(User user) {
        if(!user.isUserLoginValid()) {

            StringBuilder msgError = new StringBuilder();

            if(!user.isLoginValid()) {
                msgError.append(context.getString(R.string.msg_required_login));
            }
            if(!user.isPasswordValid()) {
                if(msgError.length() > 0) {
                    msgError.append("\n");
                }
                msgError.append(context.getString(R.string.msg_required_password));
            }

            throw new InvalidLoginException(msgError.toString());
        }
    }

    private void callLoginApi(final User user) {

        Call<LoginResponse> loginResponseCall = ServiceFactory.getInstance().getLoginService().doLogin(
                getLoginRequest(user));

        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if(response.isSuccessful()) {
                    Log.i("doLogin", "onResponse: sucesso");

                    user.setId(response.body().getUserId());
                    loginRepository.login(user);

                    EventBus.getDefault().post(new LoginResponseEvent(response.body()));
                } else {
                    Log.e("doLogin", "onResponse: erro");
                    EventBus.getDefault().post(new LoginFailureEvent(context.getString(R.string.msg_invalid_login)));
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Log.e("doLogin", "onFailure: erro" + t.getMessage());
                EventBus.getDefault().post(new LoginFailureEvent());
            }
        });
    }

    private LoginRequest getLoginRequest(User user) {
        LoginRequest request = new LoginRequest();
        request.setLogin(user.getLogin());
        request.setPassword(user.getPassword());
        return request;
    }

}
