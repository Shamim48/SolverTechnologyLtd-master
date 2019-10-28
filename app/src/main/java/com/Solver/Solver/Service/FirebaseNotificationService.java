package com.Solver.Solver.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.Solver.Solver.GroupChatAtv;
import com.Solver.Solver.MessageActivity;
import com.Solver.Solver.ModelClass.GroupMessage;
import com.Solver.Solver.Notifications.OreoNotification;
import com.Solver.Solver.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import java.util.Map;
import java.util.Random;

public class FirebaseNotificationService extends FirebaseMessagingService {
    private String Channel_Id="Notification channel";
    DatabaseReference groupMsgREf;
    Query lastQuery;
    String gName;
    String msg;
/*
    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);

        groupMsgREf= FirebaseDatabase.getInstance().getReference().child("Group").child("Sales");
        lastQuery = groupMsgREf.orderByKey().limitToLast(1);

        lastQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                GroupMessage groupMessage =dataSnapshot.getValue(GroupMessage.class);
                gName=groupMessage.getGroupName();
                msg=groupMessage.getMessage();


            }//0716399301

            Intent intent = new Intent(FirebaseNotificationService.this, GroupChatAtv.class);
          // intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(FirebaseNotificationService.this, 0, intent, 0);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(FirebaseNotificationService.this,Channel_Id)
                    .setSmallIcon(R.drawable.solver logo)
                    .setContentTitle(gName)
                    .setContentText(msg)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    // Set the intent that will fire when the user taps the notification
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(FirebaseNotificationService.this);

// notificationId is a unique int for each notification that you must define

       // notificationManager.notify(notificationId, builder.build());



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
 }*/


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //String sented = remoteMessage.getData().get("sented");
       // String user = remoteMessage.getData().get("user");


/*
        groupMsgREf.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot data:dataSnapshot.getChildren()){
                    GroupMessage groupMessage =dataSnapshot.getValue(GroupMessage.class);
                    String groupName=groupMessage.getGroupName();
                    String msg=groupMessage.getMessage();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


*/

        String sented = remoteMessage.getData().get("sented");
        String user = remoteMessage.getData().get("user");
        // if (remoteMessage != null) {
        Map<String, String> root = remoteMessage.getData();
        Log.e("data", root.toString());
        String groupName = root.get("groupName");
        String date = root.get("date");
        String message = root.get("message");
        String name = root.get("name");
        String tagClientName = root.get("tagClientName");
        String sub = root.get("sub");
        String time = root.get("time");
        // Random random=new Random(100);
     //   if (!groupName.equals("")) {

            Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Intent intent = new Intent(FirebaseNotificationService.this, GroupChatAtv.class);
            intent.putExtra("GroupName",groupName);
            intent.setAction("showMessage");
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_Id);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            builder.setSmallIcon(R.drawable.solverlogo)
                    .setContentTitle(groupName)
                    .setContentText(message)
                    .setAutoCancel(true)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                    .setLights(Color.BLUE, 3000, 3000)
                    .setSound(uri);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(Channel_Id, "Notification", NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Description");
                notificationManager.createNotificationChannel(channel);
            }
        notificationManager.notify(101, builder.build());

           /* if (remoteMessage.getNotification() != null) {
                Map<String, String> root = remoteMessage.getData();
                Log.e("data", root.toString());
                String groupName = root.get("groupName");
                String date = root.get("date");
                String message = root.get("message");
                String name = root.get("name");
                String tagClientName = root.get("tagClientName");
                String sub = root.get("sub");
                String time = root.get("time");
                // Random random=new Random(100);

                String group = remoteMessage.getData().get("groupName");

               //  if (remoteMessage.getData().containsKey("groupName")) {

                Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                Intent intent = new Intent(FirebaseNotificationService.this, GroupChatAtv.class);
                intent.putExtra("GroupName", groupName);
                intent.setAction("showMessage");
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this, Channel_Id);
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                builder.setSmallIcon(R.drawable.solverlogo)
                        .setContentTitle(groupName)
                        .setContentText(message)
                        .setAutoCancel(true)
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setContentIntent(pendingIntent)
                        .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                        .setLights(Color.BLUE, 3000, 3000)
                        .setSound(uri);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(Channel_Id, "Notification", NotificationManager.IMPORTANCE_HIGH);
                    channel.setDescription("Description");
                    notificationManager.createNotificationChannel(channel);
                }

                    notificationManager.notify(101, builder.build());

            }*/

          //  Toast.makeText(getApplicationContext(),"notify",Toast.LENGTH_SHORT).show();
/*
               }
            else {
*/
/*
                    SharedPreferences preferences = getSharedPreferences("PREFS", MODE_PRIVATE);
                    String currentUser = preferences.getString("currentuser", "none");

                    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

                    if (firebaseUser != null && sented.equals(firebaseUser.getUid())){
                        if (!currentUser.equals(user)) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                sendOreoNotification(remoteMessage);
                            } else {
                                sendNotification(remoteMessage);
                            }
                        }
                    }*//*
        }*/
    }

    private void sendOreoNotification(RemoteMessage remoteMessage){
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        OreoNotification oreoNotification = new OreoNotification(this);
        Notification.Builder builder = oreoNotification.getOreoNotification(title, body, pendingIntent,
                defaultSound, icon);

        int i = 0;
        if (j > 0){
            i = j;
}
        oreoNotification.getManager().notify(i, builder.build());

    }

    private void sendNotification(RemoteMessage remoteMessage) {
        String user = remoteMessage.getData().get("user");
        String icon = remoteMessage.getData().get("icon");
        String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");

        RemoteMessage.Notification notification = remoteMessage.getNotification();
        int j = Integer.parseInt(user.replaceAll("[\\D]", ""));
        Intent intent = new Intent(this, MessageActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("userid", user);
        intent.putExtras(bundle);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, j, intent, PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.solverlogo)
                .setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(true)
                .setSound(defaultSound)
                .setContentIntent(pendingIntent);
        NotificationManager noti = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

        int i = 0;
        if (j > 0){
            i = j;
        }

        noti.notify(i, builder.build());
    }
}
