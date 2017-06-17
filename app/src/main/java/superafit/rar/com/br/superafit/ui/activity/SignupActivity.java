package superafit.rar.com.br.superafit.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.DeviceController;
import superafit.rar.com.br.superafit.controller.SignupController;
import superafit.rar.com.br.superafit.event.CreateUserFailureEvent;
import superafit.rar.com.br.superafit.event.CreateUserResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidSignupException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.repository.LoginRepository;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class SignupActivity extends FullscreenActivity {

    private static final String TAG = "SignupActivity";

    private SignupController signupController;

    private EditText editLogin;
    private EditText editPassword;
    private EditText editConfirmPassord;

    private ProgressDialog progressDialog;

    private boolean retired = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        this.signupController = new SignupController(this);

        Button btnOk = (Button) findViewById(R.id.signup_activity_btn_confirm);
        btnOk.setOnClickListener(btnOkClick);

        Button btnBack = (Button) findViewById(R.id.signup_activity_btn_back);
        btnBack.setOnClickListener(btnBackClick);

        editLogin = (EditText) findViewById(R.id.signup_activity_edit_login);
        editLogin.addTextChangedListener(UIUtil.getWatcher(this, 15));

        editPassword = (EditText) findViewById(R.id.signup_activity_edit_password);
        editPassword.addTextChangedListener(UIUtil.getWatcher(this, 6));

        editConfirmPassord = (EditText) findViewById(R.id.signup_activity_edit_confirm_password);
        editConfirmPassord.addTextChangedListener(UIUtil.getWatcher(this, 6));

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateUserResponseEvent(CreateUserResponseEvent event) {
        if(event.hasData()) {
            retired = false;

            final Intent mainActivity = new Intent(SignupActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        } else {
            if(event.isUnavaiable() && !retired) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        retired = true;
                        signupController.doSignup(getUser());
                    }
                },10000);
            } else if(event.hasValidations()) {
                terminateProgressDialog();
                createMessageErrorDialog(event.getValidations().getFormatedErrors());
            } else {
                retired = false;
                terminateProgressDialog();
                Snackbar.make(editLogin, event.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onCreateUserFailureEvent(CreateUserFailureEvent event) {
        terminateProgressDialog();
        Snackbar.make(editLogin, R.string.msg_no_internet_connection, Snackbar.LENGTH_LONG).show();
    }

    private User getUser() {
        return getUser(null);
    }

    private User getUser(String id) {
        User user = new User();
        if(id != null) {
            user.setId(id);
        }
        user.setLogin(editLogin.getText().toString());
        user.setPassword(editPassword.getText().toString());
        user.setConfirmPassword(editConfirmPassord.getText().toString());
        return user;
    }

    private Button.OnClickListener btnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            try {
                initProgressDialog();
                signupController.doSignup(getUser());
            }catch(InvalidSignupException e) {
                terminateProgressDialog();
                createMessageErrorDialog(e.getMessage());
            }
        }
    };

    private void createMessageErrorDialog(String message) {
        final AlertDialog alertMessage = new AlertDialog.Builder(SignupActivity.this).create();
        alertMessage.setTitle(getString(R.string.create_your_user));
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

    private Button.OnClickListener btnBackClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            finish();
        }
    };

    private void initProgressDialog() {
        progressDialog = ProgressDialog.show(this, getString(R.string.processing), getString(R.string.create_your_user), true, true);
    }

    private void terminateProgressDialog() {
        if(progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        terminateProgressDialog();
    }
}
