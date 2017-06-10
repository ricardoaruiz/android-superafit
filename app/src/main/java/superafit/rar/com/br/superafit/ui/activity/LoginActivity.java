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

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.net.HttpURLConnection;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.DeviceController;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.event.LoginFailureEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.Device;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    private LoginController loginController;

    private DeviceController deviceController;

    private EditText editLogin;
    private EditText editPassword;
    private ProgressDialog progressDialog;
    private Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);
        this.deviceController = new DeviceController(this);

        btnOk = (Button) findViewById(R.id.login_activity_btn_login);
        btnOk.setOnClickListener(btnOkClick);

        Button btnSignup = (Button) findViewById(R.id.login_activity_btn_signup);
        btnSignup.setOnClickListener(btnSignupClick);

        editLogin = (EditText) findViewById(R.id.login_activity_edit_login);
        editLogin.addTextChangedListener(UIUtil.getWatcher(this, 15));

        editPassword = (EditText) findViewById(R.id.login_activity_edit_password);
        editPassword.addTextChangedListener(UIUtil.getWatcher(this, 6));

        EventBus.getDefault().register(this);
    }

    /**
     * Será executado após o retorno da chamada a API de login
     * LoginController.doLogin.onResponse
     * @param event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLoginResponseEvent(LoginResponseEvent event) {
        if(event.hasErrorMessage()) {
            terminateProgressDialog();
            Snackbar.make(editLogin, event.getMessage(), Snackbar.LENGTH_LONG).show();
        } else {
            deviceController.syncronize();
            final Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
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

    private Button.OnClickListener btnSignupClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent signupActivity = new Intent(LoginActivity.this, SignupActivity.class);
            startActivity(signupActivity);
        }
    };

    @NonNull
    private User getUser() {
        return new User(editLogin.getText().toString(),
                editPassword.getText().toString());
    }

    private void initProgressDialog() {
        progressDialog = ProgressDialog.show(this, getString(R.string.processing), getString(R.string.login), true, false);
    }

    private void terminateProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        terminateProgressDialog();
    }
}
