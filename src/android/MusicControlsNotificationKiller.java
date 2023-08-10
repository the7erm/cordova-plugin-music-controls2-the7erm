package com.homerours.musiccontrols;

import android.app.Notification;
import android.app.Service;
import android.os.Build;
import android.os.IBinder;
import android.os.Binder;
import android.os.PowerManager;
import android.app.NotificationManager;
import android.content.Intent;

public class MusicControlsNotificationKiller extends Service {

	private static int NOTIFICATION_ID;
	private NotificationManager mNM;
	private final IBinder mBinder = new KillBinder(this);

	private PowerManager.WakeLock wakeLock;

	@Override
	public IBinder onBind(Intent intent) {
		this.NOTIFICATION_ID=intent.getIntExtra("notificationID",1);
		return mBinder;
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;
	}

	@Override
	public void onCreate() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.cancel(NOTIFICATION_ID);

		if (this.wakeLock == null) {
			PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE);
			this.wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Adonia::MediaPlayer");
		}

		this.wakeLock.acquire();
	}

	@Override
	public void onDestroy() {
		mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
		mNM.cancel(NOTIFICATION_ID);

		if (this.wakeLock != null) {
			this.wakeLock.release();
		}
	}

	public void setForeground(Notification notification) {
		this.startForeground(this.NOTIFICATION_ID, notification);
	}

	public void clearForeground() {
		if (android.os.Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
			return;
		}

		this.stopForeground(STOP_FOREGROUND_DETACH);
	}
}
