package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.firebase.FirebaseManagerDataNotification;
import superafit.rar.com.br.superafit.repository.LoginRepository;

public class SplashActivity extends FullscreenActivity {

    private FirebaseManagerDataNotification firebaseManagerDataNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseManagerDataNotification = new FirebaseManagerDataNotification(this);
        manageFcmNotifications();

        callNextActivity();
    }

    private void manageFcmNotifications() {
        firebaseManagerDataNotification.save(getIntent().getExtras());
    }

    private void callNextActivity() {
        new Handler().postDelayed(new Runnable(){

            final LoginRepository loginRepository = new LoginRepository(SplashActivity.this);

            @Override
            public void run() {
                final Intent nextActivity;

                if(!loginRepository.isLogged()) {
                    nextActivity = new Intent(SplashActivity.this, LoginActivity.class);
                } else {
                    nextActivity = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(nextActivity);
                finish();
            }
        }, 2000);
    }
}
