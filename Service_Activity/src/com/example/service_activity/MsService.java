package com.example.service_activity;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MsService extends Service {

	public static final int MAX_PROGRESS = 100;

	private int progress = 0;

	private OnProgressListener onProgressListener;

	public void setOnProgressListener(OnProgressListener onProgressListener) {
		this.onProgressListener = onProgressListener;
	}

	public int getProgress() {
		return progress;
	}

	public void startDownLoad() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress < MAX_PROGRESS) {
					progress += 5;
					if (onProgressListener != null) {
						onProgressListener.onProgress(progress);
					}
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
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new MsBinder();
	}

	public class MsBinder extends Binder {
		/**
		 * 获取当前Service的实例
		 * 
		 * @return
		 */
		public MsService getService() {
			return MsService.this;
		}
	}
}
