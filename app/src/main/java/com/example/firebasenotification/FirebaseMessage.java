package com.example.firebasenotification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.iid.internal.FirebaseInstanceIdInternal;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessage extends FirebaseMessagingService {
    
    public static int Notification_ID = 1;


    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken( s );
        Log.e("NEW_TOKEN",s);   
        FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener( new OnSuccessListener<InstanceIdResult>() {
            @Override
            public void onSuccess(InstanceIdResult instanceIdResult) {
                String newToken = instanceIdResult.getToken();
                Log.e( "newToken", newToken);
            }
        } );
    }
    
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        generateNotification( remoteMessage.getNotification().getBody(), remoteMessage.getNotification().getTitle());
    }
    
    private void generateNotification(String body, String title){
        Intent intent = new Intent( this, MainActivity.class );
        intent.addFlags( Intent.FLAG_ACTIVITY_CLEAR_TOP );
        PendingIntent contentIntent = PendingIntent.getActivity( 
                this, 0, intent, PendingIntent.FLAG_ONE_SHOT );
        Uri sound = RingtoneManager.getDefaultUri( RingtoneManager.TYPE_NOTIFICATION );
      
        NotificationCompat.Builder notificationCompat = new NotificationCompat.Builder( this )
                .setSmallIcon( R.drawable.ic_android_black_24dp )
                .setContentTitle( title )
                .setContentText( body )
                .setSound( sound )
                .setAutoCancel( true )
                .setContentIntent( contentIntent );
        NotificationManager notificationManager = (NotificationManager) 
                getSystemService( Context.NOTIFICATION_SERVICE );
        
        if (Notification_ID > 1222222222){
            Notification_ID = 0;
        }
        
        notificationManager.notify( Notification_ID++,notificationCompat.build() );
    }

}
