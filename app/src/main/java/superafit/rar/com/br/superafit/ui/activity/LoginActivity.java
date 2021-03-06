package superafit.rar.com.br.superafit.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.DeviceController;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.event.LoginFailureEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    private static final String TAG = "LoginActivity";
    private LoginController loginController;
    private DeviceController deviceController;

    private EditText editLogin;
    private EditText editPassword;
    private ProgressDialog progressDialog;
    private Button btnOk;

    private boolean retried = false;

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
            if(event.isUnavaiable() && !retried) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retried = true;
                        loginController.doLogin(getUser());
                    }
                }, 10000);
            } else {
                retried = false;
                terminateProgressDialog();
                Snackbar.make(editLogin, event.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        } else {
            retried = false;
            //deviceController.syncronize();
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
        Snackbar.make(editLogin, R.string.msg_no_internet_connection, Snackbar.LENGTH_LONG).show();
    }

    private Button.OnClickListener btnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        try {
            initProgressDialog();
            loginController.doLogin(getUser());
        } catch(InvalidLoginException e) {
            terminateProgressDialog();
            createMessageErrorDialog(e.getMessage());
        }
        }
    };

    private void createMessageErrorDialog(String message) {
        final AlertDialog alertMessage = new AlertDialog.Builder(LoginActivity.this).create();
        alertMessage.setTitle(getString(R.string.login));
        alertMessage.setMessage(message);
        alertMessage.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertMessage.dismiss();
            }
        });
        alertMessage.setCancelable(false);
        alertMessage.show();
    }

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
