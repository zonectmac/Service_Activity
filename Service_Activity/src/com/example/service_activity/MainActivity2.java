package com.example.service_activity;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity2 extends Activity {
	private ProgressBar mProgressBar;
	private Intent mIntent;
	private MsgReceiver msgReceiver;
	private TextView textView1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		textView1 = (TextView) findViewById(R.id.textView1);
		// 动态注册广播接收器
		msgReceiver = new MsgReceiver();
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.example.communication.RECEIVER");
		registerReceiver(msgReceiver, intentFilter);

		mProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
		Button mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 启动服务
				mIntent = new Intent("com.example.communication.MSG_ACTION2");
				startService(mIntent);
			}
		});

	}

	@Override
	protected void onDestroy() {
		// 停止服务
		stopService(mIntent);
		// 注销广播
		unregisterReceiver(msgReceiver);
		super.onDestroy();
	}

	/**
	 * 广播接收器
	 * 
	 * @author len
	 * 
	 */
	public class MsgReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			// 拿到进度，更新UI
			int progress = intent.getIntExtra("progress", 0);
			mProgressBar.setProgress(progress);
			textView1.setText("" + progress);
		}
	}
}
