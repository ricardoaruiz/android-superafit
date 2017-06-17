package superafit.rar.com.br.superafit.ui.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.LoginController;
import superafit.rar.com.br.superafit.event.LoginFailureEvent;
import superafit.rar.com.br.superafit.event.LoginResponseEvent;
import superafit.rar.com.br.superafit.exception.InvalidLoginException;
import superafit.rar.com.br.superafit.model.User;
import superafit.rar.com.br.superafit.uitls.UIUtil;

public class LoginActivity extends FullscreenActivity {

    private static final String TAG = "LoginActivity";
    private LoginController loginController;

    private EditText editLogin;
    private EditText editPassword;
    private ProgressDialog progressDialog;
    private Button btnOk;

    private boolean retried = false;

    //Facebook Login
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private ProfileTracker profileTracker;
    private LoginFaceData loginFaceData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        facebookConfig();

        this.loginController = new LoginController(this);

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

    @Override
    protected void onResume() {
        super.onResume();
        //Facebook login
        Bundle extras = getIntent().getExtras();
        if(extras == null ) {
            Profile profile = Profile.getCurrentProfile();
            nextActivity(loginFaceData);
        }
    }

    protected void onStop() {
        super.onStop();
        //Facebook login
        accessTokenTracker.stopTracking();
        profileTracker.stopTracking();
    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode, Intent intent) {
        super.onActivityResult(requestCode, responseCode, intent);
        //Facebook login
        callbackManager.onActivityResult(requestCode, responseCode, intent);
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
            nextActivity();
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

    private void nextActivity() {
        nextActivity(null);
    }

    private void nextActivity(LoginFaceData loginFaceData) {
        if(loginFaceData != null) {
            User user = new User(loginFaceData.getEmail(), "111111", "111111");
            loginController.doLogin(user, true);
        }
        final Intent mainActivity = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainActivity);
        finish();
    }

    private void facebookConfig() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {

            }
        };

        profileTracker = new ProfileTracker() {
            @Override
            protected void onCurrentProfileChanged(Profile oldProfile, Profile newProfile) {
                nextActivity(loginFaceData);
            }
        };
        accessTokenTracker.startTracking();
        profileTracker.startTracking();

        LoginButton loginButton = (LoginButton)findViewById(R.id.login_button);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initProgressDialog();
            }
        });

        FacebookCallback<LoginResult> callback = new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                AccessToken accessToken = loginResult.getAccessToken();
                final Profile profile = Profile.getCurrentProfile();

                // Facebook Email address
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(
                                    JSONObject object,
                                    GraphResponse response) {
                                Log.v("LoginActivity Response ", response.toString());

                                try {
                                    String name = object.getString("name");
                                    String email = object.getString("email");
                                    loginFaceData = new LoginFaceData();
                                    loginFaceData.setProfile(profile);
                                    loginFaceData.setEmail(email);
                                    nextActivity(loginFaceData);
                                } catch (JSONException e) {
                                    Log.e(TAG, "onCompleted: ", e);
                                }
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender, birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                terminateProgressDialog();
            }

            @Override
            public void onError(FacebookException e) {
                terminateProgressDialog();
                Toast.makeText(getApplicationContext(), "Problema ao comunicar com o Fcebook:", Toast.LENGTH_LONG).show();
            }
        };
        loginButton.setReadPermissions("public_profile", "email", "user_friends");
        loginButton.registerCallback(callbackManager, callback);
    }

    private class LoginFaceData {

        private Profile profile;

        private String email;

        public Profile getProfile() {
            return profile;
        }

        public void setProfile(Profile profile) {
            this.profile = profile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
