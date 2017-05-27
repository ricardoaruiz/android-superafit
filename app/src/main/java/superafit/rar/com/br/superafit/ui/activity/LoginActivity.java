package superafit.rar.com.br.superafit.ui.activity;

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

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.event.LoginSuccessEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.service.model.response.LoginResponse;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    private LoginController loginController;
    private EditText editLogin;
    private EditText editPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.loginController = new LoginController(this);

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
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //faz nada
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() == length) {
                    UIUtil.hideKeyboard(LoginActivity.this);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //faz nada
            }
        };
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(LoginSuccessEvent event) {
        Toast.makeText(this, "Sucesso", Toast.LENGTH_SHORT).show();
    }

    private Button.OnClickListener btnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                loginController.doLogin(new User(editLogin.getText().toString(),
                        editPassword.getText().toString()));

                final Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(mainActivity);
                finish();
            } catch(InvalidLoginException e) {
                Snackbar.make(editLogin, e.getMessage(), Snackbar.LENGTH_SHORT).show();
            }
        }
    };
}
