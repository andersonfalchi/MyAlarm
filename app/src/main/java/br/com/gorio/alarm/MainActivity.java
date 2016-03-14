package br.com.gorio.alarm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

/**
 * Created by gorio on 11/03/16.
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Start Alarm
        AlarmManagerUtil alarmUtil = new AlarmManagerUtil();
        alarmUtil.initAlarmNotification(this);
    }
}
