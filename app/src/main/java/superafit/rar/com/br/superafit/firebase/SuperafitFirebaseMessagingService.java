package superafit.rar.com.br.superafit.firebase;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import org.greenrobot.eventbus.EventBus;

import superafit.rar.com.br.superafit.event.GetWodResponseEvent;
import superafit.rar.com.br.superafit.service.model.response.GetWodResponse;

/**
 * Created by ralmendro on 07/06/17.
 */

public class SuperafitFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessaging";

    private FirebaseManagerDataNotification firebaseManagerDataNotification;

    public SuperafitFirebaseMessagingService() {
        firebaseManagerDataNotification = new FirebaseManagerDataNotification(this);
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if (remoteMessage.getData().size() > 0) {
            String data = remoteMessage.getData().toString();
            Log.i(TAG, "Message data payload: " + data);
            firebaseManagerDataNotification.save(data);
        }

        if (remoteMessage.getNotification() != null) {
            Log.i(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
    }
}
