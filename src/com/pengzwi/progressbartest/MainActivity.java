package com.pengzwi.progressbartest;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.text.TextPaint;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * @author lvan
 * 
 */
public class MainActivity extends Activity implements Runnable, Callback {
	int progress = 0;
	private TextView tv_progress;
	private int width;
	private int height;
	private float scale = 0.5f;// 进度条占屏幕的比例
	private int max = 100;// 进度条分多少份
	private int item_width;// 进度条没份多宽
	private RelativeLayout rlt_loading;// 进度条背景的宽度
	private int rlt_width;
	private int item_height;
	private ImageView img_progress;
	private int pb_width;
	private Handler mHandler = new Handler(this);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
		setContentView(R.layout.activity_main);
		Display display = getWindowManager().getDefaultDisplay();
		width = display.getWidth();
		height = display.getHeight();
		rlt_width = (int) (width * scale);
		rlt_width = (rlt_width / 100) * 100 + 100;
		item_width = rlt_width / max;
		initView();
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	public void initView() {
		// TODO Auto-generated method stub
		item_height = dip2px(this, 24);
		pb_width = dip2px(this, 27);
		rlt_loading = (RelativeLayout) findViewById(R.id.rlt_loading);
		img_progress = (ImageView) findViewById(R.id.img_progress);
		rlt_loading.setLayoutParams(new RelativeLayout.LayoutParams(rlt_width,
				item_height));
		tv_progress = (TextView) findViewById(R.id.tv_progress);
		TextPaint tp = tv_progress.getPaint();
		tp.setFakeBoldText(true);
		tv_progress.setText(progress + "%");
		mHandler.postDelayed(this, 1000);
	}

	public void run() {
		// TODO Auto-generated method stub
		if (progress == max)
			progress = 0;
		progress++;
		tv_progress.setText(progress + "%");
		img_progress.setVisibility(View.VISIBLE);
		if (progress * item_width > pb_width) {
			img_progress.setLayoutParams(new RelativeLayout.LayoutParams(
					progress * item_width, item_height));
		} else {
			img_progress.setLayoutParams(new RelativeLayout.LayoutParams(
					pb_width, item_height));
		}
		mHandler.postDelayed(this, 100);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		mHandler.removeCallbacks(this);
		super.onDestroy();
	}

	@Override
	public boolean handleMessage(Message msg) {
		// TODO Auto-generated method stub
		return false;
	}
}
