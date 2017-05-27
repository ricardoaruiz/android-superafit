package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.event.LoginSuccessEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.service.RetrofitFactory;
import superafit.rar.com.br.superafit.service.model.request.LoginRequest;
import superafit.rar.com.br.superafit.service.model.response.LoginResponse;

/**
 * Created by ralmendro on 27/05/17.
 */

public class LoginController {

    private Context context;

    private RetrofitFactory serviceFactory;

    public LoginController(Context context) {
        this.context = context;
        this.serviceFactory = new RetrofitFactory();
    }

    public void doLogin(User user) {

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

        } else {

            LoginRequest request = new LoginRequest();
            request.setLogin(user.getLogin());
            request.setPassword(user.getPassword());

            Call<LoginResponse> loginResponseCall = serviceFactory.getLoginService().doLogin(request);

            loginResponseCall.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    Log.i("Login", "onResponse: sucesso");
                    EventBus.getDefault().post(new LoginSuccessEvent(response.body()));
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Log.i("Login", "onResponse: erro" + t.getMessage());
                }
            });

        }

    }

}
