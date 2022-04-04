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

    public static final String ASSESSMENT_ALARM_TITLE = "com.example.dathan_stone_c196_task.utilities.ASSESSMENT_ALARM_TITLE";


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, AssessmentsActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int startAlarmId = intent.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START_ALARM_ID, -1);
        int endAlarmId = intent.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END_ALARM_ID, -1);

        if (startAlarmId > -1) {
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, intent.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_START_ALARM_ID, -1), i, 0);
            String title = intent.getStringExtra(ASSESSMENT_ALARM_TITLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "assess")
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_action_edit)
                    .setContentText("Assessment Start Date")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent1);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123, builder.build());
        } else if (endAlarmId > -1) {

            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, intent.getIntExtra(AddEditAssessmentsActivity.EXTRA_ASSESSMENT_END_ALARM_ID, -1), i, 0);
            String title = intent.getStringExtra(ASSESSMENT_ALARM_TITLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "assess")
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_action_edit)
                    .setContentText("Assessment End Date")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent2);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(123, builder.build());
        }
    }



}
