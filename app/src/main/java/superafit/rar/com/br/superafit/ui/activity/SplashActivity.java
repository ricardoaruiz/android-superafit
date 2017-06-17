package superafit.rar.com.br.superafit.ui.activity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import superafit.rar.com.br.superafit.R;
import superafit.rar.com.br.superafit.firebase.FirebaseManagerDataNotification;
import superafit.rar.com.br.superafit.repository.LoginRepository;

public class SplashActivity extends FullscreenActivity {

    private FirebaseManagerDataNotification firebaseManagerDataNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        generateFacebookHashKey();

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
                    nextActivity.putExtra("login", true);
                } else {
                    nextActivity = new Intent(SplashActivity.this, MainActivity.class);
                }
                startActivity(nextActivity);
                finish();
            }
        }, 2000);
    }

    private void generateFacebookHashKey() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "superafit.rar.com.br.superafit",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("Your Tag", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }
}
