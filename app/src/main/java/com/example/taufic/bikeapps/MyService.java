package com.example.taufic.bikeapps;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MyService extends Service {
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference ref = database.getReference("Event");

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return null;
    }

     @Override
     public int onStartCommand(Intent intent, int flags, int startId) {
         Intent bIntent = new Intent(MyService.this, MainActivity.class);
         final PendingIntent pbIntent = PendingIntent.getActivity(MyService.this, 0 , bIntent, 0);
         final Context contet = this.getBaseContext();

         ref.addChildEventListener(new ChildEventListener() {
             @Override
             public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                 Event event = dataSnapshot.getValue(Event.class);

                 NotificationCompat.Builder bBuilder =
                         new NotificationCompat.Builder(contet)
                                 .setSmallIcon(R.mipmap.ic_launcher)
                                 .setContentTitle(event.getName())
                                 .setContentText(event.getDescription())
                                 .setAutoCancel(true)
                                 .setOngoing(true)
                                 .setContentIntent(pbIntent);
                 Notification barNotif = bBuilder.build();
                 startForeground(1, barNotif);
             }

             @Override
             public void onChildChanged(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onChildRemoved(DataSnapshot dataSnapshot) {

             }

             @Override
             public void onChildMoved(DataSnapshot dataSnapshot, String s) {

             }

             @Override
             public void onCancelled(DatabaseError databaseError) {

             }
         });
        return Service.START_STICKY;
    }
}
