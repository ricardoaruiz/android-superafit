package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Button btnOk = (Button) findViewById(R.id.login_activity_btn_login);
        btnOk.setOnClickListener(btnOkClick);

        EditText editLogin = (EditText) findViewById(R.id.login_activity_edit_login);
        editLogin.addTextChangedListener(getWatcher(15));

        final EditText editSenha = (EditText) findViewById(R.id.login_activity_edit_senha);
        editSenha.addTextChangedListener(getWatcher(6));

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

    private Button.OnClickListener btnOkClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            final Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(mainActivity);
            finish();
        }
    };
}
