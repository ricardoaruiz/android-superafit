package superafit.rar.com.br.superafit.controller;

import android.content.Context;
import android.util.Log;

import org.greenrobot.eventbus.EventBus;

import java.net.HttpURLConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.event.CreateUserFailureEvent;
import superafit.rar.com.br.superafit.event.CreateUserResponseEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidSignupException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.service.ServiceFactory;
import superafit.rar.com.br.superafit.service.model.request.CreateUserRequest;
import superafit.rar.com.br.superafit.service.model.response.CreateUserResponse;
import superafit.rar.com.br.superafit.uitls.ErrorUtils;

/**
 * Created by ralmendro on 29/05/17.
 */

public class SignupController {

    private Context context;

    private LoginRepository loginRepository;

    private DeviceController deviceController;

    public SignupController(Context context) {
        this.context = context;
        this.loginRepository = new LoginRepository(context);
        this.deviceController = new DeviceController(context);
    }

    public void doSignup(final User user) {
        doSignup(user, false);
    }

    public void doSignup(final User user, final boolean facebook) {
        validateSignupData(user);
        Call<CreateUserResponse> createUserResponseCall = ServiceFactory.getInstance()
                .getUserService().create(getCreateUserRequest(user));

        createUserResponseCall.enqueue(new Callback<CreateUserResponse>() {
            @Override
            public void onResponse(Call<CreateUserResponse> call, Response<CreateUserResponse> response) {
                switch (response.code()) {
                    case HttpURLConnection.HTTP_UNAVAILABLE:
                        Log.e("doSignup", "onResponse: " + context.getString(R.string.msg_service_unavailable));
                        if(!facebook) {
                            EventBus.getDefault().post(new CreateUserResponseEvent(context.getString(R.string.msg_service_unavailable), true));
                        } else {
                            EventBus.getDefault().post(new LoginResponseEvent(context.getString(R.string.msg_service_unavailable), true));
                        }
                        break;
                    case 422:
                        Log.e("doSignup", "onResponse: Validações de criação de usuário.");
                        EventBus.getDefault().post(new CreateUserResponseEvent(ErrorUtils.parseError(response.errorBody())));
                        break;
                    default:
                        user.setId(response.body().getUserId());
                        loginRepository.login(user);
                        deviceController.syncronize();
                        EventBus.getDefault().post(new CreateUserResponseEvent(response.body()));
                        break;
                }
            }

            @Override
            public void onFailure(Call<CreateUserResponse> call, Throwable t) {
                EventBus.getDefault().post(new CreateUserFailureEvent());
            }
        });
    }

    private CreateUserRequest getCreateUserRequest(User user) {
        CreateUserRequest request = new CreateUserRequest();
        request.setLogin(user.getLogin());
        request.setPassword1(user.getPassword());
        request.setPassword2(user.getConfirmPassword());
        return request;
    }

    private void validateSignupData(User user) {
        StringBuilder msgError = new StringBuilder();

        if(!user.isLoginValid()) {
            msgError.append("- ").append(context.getString(R.string.msg_invalid_email_login));
        }

        if(!user.isPasswordValid()) {
            if(msgError.length() > 0) {
                msgError.append("\n\n");
            }
            msgError.append("- ").append(context.getString(R.string.msg_required_password));
        }

        if(!user.isConfirmPasswordValid()) {
            if(msgError.length() > 0) {
                msgError.append("\n\n");
            }
            msgError.append("- ").append(context.getString(R.string.msg_required_confirm_password));
        }

        if(!user.isPasswordsAreEquals()) {
            if(msgError.length() > 0) {
                msgError.append("\n\n");
            }
            msgError.append("- ").append(context.getString(R.string.msg_passwords_are_difrent));
        }

        if(msgError.length() > 0) {
            throw new InvalidSignupException(msgError.toString());
        }
    }

}
