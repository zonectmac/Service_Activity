package com.example.service_activity;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class CopyOfMsService extends Service {

	private static final int MAX_PROGRESS = 100;
	private Intent intent = new Intent("com.example.communication.RECEIVER");
	private int progress = 0;

	public int getProgress() {
		return progress;
	}

	public void startDownLoad() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress < MAX_PROGRESS) {
					progress += 5;
					// 发送Action为com.example.communication.RECEIVER的广播
					intent.putExtra("progress", progress);
					sendBroadcast(intent);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}).start();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		System.out.println("-----fff--");
		startDownLoad();
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
}
