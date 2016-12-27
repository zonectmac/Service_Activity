package com.example.service_activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	private MsService msgService;
	private ProgressBar mProgressBar;
	private int progress = 0;
	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		textView1 = (TextView) findViewById(R.id.textView1);
		Intent intent = new Intent("com.example.communication.MSG_ACTION");
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				msgService.startDownLoad();
				listenProgress();
			}
		});
	}

	/**
	 * 监听进度，每秒钟获取调用MsgService的getProgress()方法来获取进度，更新UI
	 */
	public void listenProgress() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				while (progress < MsService.MAX_PROGRESS) {
					progress = msgService.getProgress();
					mProgressBar.setProgress(progress);
					textView1.setText("" + mProgressBar.getProgress());
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

	ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			msgService = ((MsService.MsBinder) service).getService();
			msgService.setOnProgressListener(new OnProgressListener() {

				@Override
				public void onProgress(int progress) {
					mProgressBar.setProgress(progress);

				}
			});
		}
	};

	@Override
	protected void onDestroy() {
		unbindService(conn);
		super.onDestroy();
	};

}
