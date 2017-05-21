package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import superafit.rar.com.br.superafit.R;

public class SplashActivity extends FullscreenActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        callMainActivity();
    }

    private void callMainActivity() {
        final Intent myintent = new Intent(this, LoginActivity.class);

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                startActivity(myintent);
                finish();
            }
        }, 2000);
    }
}
