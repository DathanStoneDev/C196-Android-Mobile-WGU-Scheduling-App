package com.example.dathan_stone_c196_task.utilities;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.dathan_stone_c196_task.R;
import com.example.dathan_stone_c196_task.activities.CourseDetailsActivity;
import com.example.dathan_stone_c196_task.activities.CoursesActivity;

public class CourseAlertReceiver extends BroadcastReceiver {

    public static final String ALARM_TITLE = "com.example.dathan_stone_c196_task.utilities.ALARM_TITLE";


    @Override
    public void onReceive(Context context, Intent intent) {

        Intent i = new Intent(context, CoursesActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        int startAlarmId = intent.getIntExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, -1);
        int endAlarmId = intent.getIntExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, -1);

        if(startAlarmId > -1) {
            PendingIntent pendingIntent1 = PendingIntent.getActivity(context, intent.getIntExtra(CourseDetailsActivity.EXTRA_START_COURSE_ALARM_ID, -1), i, 0);
            String title = intent.getStringExtra(ALARM_TITLE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "course_channel")
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_action_edit)
                    .setContentText("Course Start Alarm")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent1);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1, builder.build());
        } else if(endAlarmId > -1){
            PendingIntent pendingIntent2 = PendingIntent.getActivity(context, intent.getIntExtra(CourseDetailsActivity.EXTRA_END_COURSE_ALARM_ID, -1), i, 0);

            String title = intent.getStringExtra(ALARM_TITLE);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context.getApplicationContext(), "course_channel")
                    .setContentTitle(title)
                    .setSmallIcon(R.drawable.ic_action_edit)
                    .setContentText("Course End Alarm")
                    .setAutoCancel(true)
                    .setDefaults(NotificationCompat.DEFAULT_ALL)
                    .setContentIntent(pendingIntent2);

            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            notificationManagerCompat.notify(1, builder.build());
        }

    }
}
