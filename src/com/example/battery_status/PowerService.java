package com.example.battery_status;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.IBinder;

/**
 * Created with IntelliJ IDEA.
 * User: mraviteja
 * Date: 8/6/14
 * Time: 12:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class PowerService extends Service {

    private final BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context arg0, Intent intent) {

            /*int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);

            float batteryPct = level / (float)scale;*/

            int currentLevel = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int level = -1;
            if (currentLevel >= 0 && scale > 0) {
                level = (currentLevel * 100) / scale;
            }


            // Toast.makeText(arg0,"Battery Broadcast"+level,Toast.LENGTH_SHORT).show();
            showNotification(arg0, level);


        }

    };
    public PendingIntent contentIntent;
    public Notification notification = new Notification(R.drawable.one,
            "Battery Manager Service Started.", System.currentTimeMillis());
    PowerService ps;

    @Override
    public IBinder onBind(Intent intent) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        registerReceiver(this.mBatInfoReceiver, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
    }

    @Override
    public void onStart(final Intent intent, int startid) {
      /*  notification = new Notification(R.drawable.ic_launcher,
                "Rolling text on statusbar", System.currentTimeMillis());

        contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        ps = this;
        notification.setLatestEventInfo(this,
                "Battery Manager", "Battery charging is.", contentIntent);

        startForeground(1, notification);*/


    }

    @Override
    public void onDestroy() {
        unregisterReceiver(this.mBatInfoReceiver);

    }

    private void showNotification(Context arg0, int level) {
        String l = Integer.toString(level);
        String buttonID = "cs" + l;
        int resID = getResources().getIdentifier(buttonID, "drawable", "com.example.battery_status");


        notification = new Notification(resID,
                "Battery Manager Service Started", System.currentTimeMillis());


        contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MyActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);
        ps = this;
        notification.setLatestEventInfo(this,
                "Battery Manager", "Battery charging is." + level, contentIntent);

        startForeground(1, notification);

    }


}