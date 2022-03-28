package com.example.dathan_stone_c196_task.utilities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.activities.AddEditAssessmentsActivity;
import com.example.dathan_stone_c196_task.activities.AssessmentsActivity;


public class AssessmentAlertReceiver extends BroadcastReceiver {

    public static final String ASSESSMENT_ALARM_TITLE = "com.example.dathan_stone_c196_task.utilities.ALARM_TITLE";


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AssessmentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, intent.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_ALARM_ID, -1), i, 0);

        String title = intent.getStringExtra(ASSESSMENT_ALARM_TITLE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "assessment_channel")
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_action_edit)
                .setContentText("here you go")
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(2, builder.build());

    }
}
