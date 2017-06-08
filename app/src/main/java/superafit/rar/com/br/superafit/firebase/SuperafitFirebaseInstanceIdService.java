package superafit.rar.com.br.superafit.firebase;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import superafit.rar.com.br.superafit.controller.DeviceController;
import superafit.rar.com.br.superafit.model.Device;

/**
 * Created by ralmendro on 07/06/17.
 */

public class SuperafitFirebaseInstanceIdService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseInstanceId";

    @Override
    public void onTokenRefresh() {
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
        Log.i(TAG, "sendRegistrationToServer: salvando token gerado.");
        new DeviceController(getBaseContext()).save(getDevice(refreshedToken));
    }

    @NonNull
    private Device getDevice(String refreshedToken) {
        Device device = new Device();
        device.setToken(refreshedToken);
        return device;
    }
}
