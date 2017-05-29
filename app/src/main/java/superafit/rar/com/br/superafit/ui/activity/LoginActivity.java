package superafit.rar.com.br.superafit.ui.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.HttpURLConnection;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.event.LoginFailureEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    private LoginController loginController;
    private EditText editLogin;
    private EditText editPassword;
    private ProgressDialog progressDialog;
    private LoginRepository loginRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);
        this.loginRepository = new LoginRepository(this);

        Button btnOk = (Button) findViewById(R.id.login_activity_btn_login);
        btnOk.setOnClickListener(btnOkClick);

        editLogin = (EditText) findViewById(R.id.login_activity_edit_login);
        editLogin.addTextChangedListener(getWatcher(15));

        editPassword = (EditText) findViewById(R.id.login_activity_edit_password);
        editPassword.addTextChangedListener(getWatcher(6));

        EventBus.getDefault().register(this);
    }

    @NonNull
    private TextWatcher getWatcher(final int length) {
        return new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { /* Nothing */ }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == length) {
                    UIUtil.hideKeyboard(LoginActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) { /* Nothing */ }
        };
    }

    /**
     * Será executado após o retorno da chamada a API de login
     * LoginController.doLogin.onResponse
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginResponseEvent(LoginResponseEvent event) {

        if(event.getHttpCode() == HttpURLConnection.HTTP_UNAUTHORIZED) {
            Snackbar.make(editLogin, R.string.msg_invalid_login, Snackbar.LENGTH_LONG).show();
        } else {
            User user = getUser();
            user.setId(event.getLoginResponse().getUserId());
            loginRepository.login(user);

            final Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }

        terminateProgressDialog();
    }

    /**
     * Será executado caso a chamada a API de login falhe.
     * LoginController.doLogin.onFailure
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginFailureEvent(LoginFailureEvent event) {
        terminateProgressDialog();
        Snackbar.make(editLogin, R.string.msg_failed_login, Snackbar.LENGTH_LONG).show();
    }

    private Button.OnClickListener btnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        try {
            initProgressDialog();
            loginController.doLogin(getUser());
        } catch(InvalidLoginException e) {
            terminateProgressDialog();
            Snackbar.make(editLogin, e.getMessage(), Snackbar.LENGTH_SHORT).show();
        }
        }
    };

    @NonNull
    private User getUser() {
        return new User(editLogin.getText().toString(),
                editPassword.getText().toString());
    }

    private void initProgressDialog() {
        progressDialog = ProgressDialog.show(this, getString(R.string.processando), getString(R.string.login), true, false);
    }

    private void terminateProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
}
