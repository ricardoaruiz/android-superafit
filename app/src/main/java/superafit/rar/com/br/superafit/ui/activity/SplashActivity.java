package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.controller.DeviceController;
import superafit.rar.com.br.superafit.firebase.FirebaseManagerDataNotification;

public class SplashActivity extends FullscreenActivity {

    private FirebaseManagerDataNotification firebaseManagerDataNotification;

    private DeviceController deviceController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        firebaseManagerDataNotification = new FirebaseManagerDataNotification(this);
        deviceController = new DeviceController(this);

        manageFcmNotifications();
        deviceController.syncronize();
        callNextActivity();
    }

    private void manageFcmNotifications() {
        firebaseManagerDataNotification.save(getIntent().getExtras());
    }

    private void callNextActivity() {
        new Handler().postDelayed(new Runnable(){

            @Override
            public void run() {
                final Intent nextActivity = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(nextActivity);
                finish();
            }
        }, 2000);
    }
}
