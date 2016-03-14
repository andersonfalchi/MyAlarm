package br.com.gorio.alarm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.util.Log;

import java.util.Calendar;

/**
 * Created by gorio on 11/03/16.
 */
public class AlarmManagerUtil {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public void initAlarmNotification(Context context) {

        Calendar calendar = getAlarmDate();

        if (calendar == null) {
            return;
        }

        Intent myIntent = new Intent(context, AlarmReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, myIntent, 0);

        alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
    }

    public void cancelAlarm(Context context) {
        // If the alarm has been set, cancel it.
        if (alarmManager != null) {
            alarmManager.cancel(pendingIntent);
        }

        // Disable {@code SampleBootReceiver} so that it doesn't automatically restart the
        // alarm when the device is rebooted.
        ComponentName receiver = new ComponentName(context, BootReceiver.class);
        PackageManager pm = context.getPackageManager();

        pm.setComponentEnabledSetting(receiver,
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                PackageManager.DONT_KILL_APP);
    }

    private Calendar getAlarmDate() {
        Calendar calendar = Calendar.getInstance();

        boolean setAlarm = false;
        int hour = Const.ALARM_HOUR_TIME[0];
        int minute = Const.ALARM_MINUTE_TIME[0];

        int currentHour = calendar.get(Calendar.HOUR_OF_DAY);
        int currentMinute = calendar.get(Calendar.MINUTE);

        for (int i = 0; i < Const.ALARM_HOUR_TIME.length; i++) {

            if (currentHour <= Const.ALARM_HOUR_TIME[i] && currentMinute < Const.ALARM_MINUTE_TIME[i] && !setAlarm) {
                hour = Const.ALARM_HOUR_TIME[i];
                minute = Const.ALARM_MINUTE_TIME[i];
                setAlarm = true;
            } else if (i == (Const.ALARM_HOUR_TIME.length - 1) && !setAlarm) {
                calendar.add(Calendar.DATE, 1);
                hour = Const.ALARM_HOUR_TIME[0];
                minute = Const.ALARM_MINUTE_TIME[0];
            }
        }

        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Log.d("MyAlarm", "Next Alarm: " + hour + ":" + minute);

        return calendar;
    }
}
