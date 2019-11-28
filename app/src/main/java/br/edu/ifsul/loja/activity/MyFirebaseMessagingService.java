package br.edu.ifsul.loja.activity;

//import android.app.NotificationManager;
import android.content.Context;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import br.edu.ifsul.loja.R;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "myFirebaseService";

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());

            if (/* Check if data needs to be processed by long running job */ true) {
                // For long-running tasks (10 seconds or more) use Firebase Job Dispatcher.
                scheduleJob();
            } else {
                // Handle message within 10 seconds
               // handleNow();
            }

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            Notification.Builder builder = new Notification.Builder(this,"1")

                    .setSmallIcon(R.drawable.images)
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(Notification.PRIORITY_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    /// NotificationManagerCompat.from(this);

// notificationId is a unique int for each notification that you must define
            Log.d(TAG, "buider " + builder);
            notificationManager.notify(1, builder.build());
        }


        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void scheduleJob() {
    }

}
