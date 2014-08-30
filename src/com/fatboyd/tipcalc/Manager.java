package com.fatboyd.tipcalc;

import com.google.ads.AdRequest;
import com.google.ads.AdSize;
import com.google.ads.AdView;
import android.os.Bundle;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Manager extends Activity {

	LinearLayout layout;
	ImageButton pplUp;
	ImageButton pplDown;
	ImageButton percentUp;
	ImageButton percentDown;

	private EditText etBill;
	private EditText etPpl;
	private EditText etPercent;
	/* Publisher id for google AdMob */
	protected static String PUBLISHER_ID = "ca-app-pub-xxxxxxxxxxxxxxxxxxxxx";


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_manager);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		loadAdView();
		setViewElements();
		(new Calc()).start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.manager, menu);
		return true;
	}

	private void loadAdView() {
		layout = (LinearLayout) findViewById(R.id.loadd);
		AdView ad = new AdView(this, AdSize.BANNER, PUBLISHER_ID);
		layout.addView(ad);
		ad.loadAd(new AdRequest()); 
	}

	private void setViewElements() {

		pplUp = (ImageButton) findViewById(R.id.bplusppl);
		pplDown = (ImageButton) findViewById(R.id.bminusppl);
		percentUp = (ImageButton) findViewById(R.id.bplustip);
		percentDown = (ImageButton) findViewById(R.id.bminustip);


		setupUI(findViewById(R.id.parent));

		pplUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					etPpl.setText("" +(1+Integer.valueOf(etPpl.getText().toString())));
				} catch (Exception e) {}
			}
		});
		pplDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int current = Integer.valueOf(etPpl.getText().toString());
					if (current>1)
						etPpl.setText("" +(current-1));
				} catch (Exception e) {}
			}
		});
		percentUp.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					etPercent.setText("" +(1+Integer.valueOf(etPercent.getText().toString())));
				} catch (Exception e) {}
			}
		});
		percentDown.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					int current = Integer.valueOf(etPercent.getText().toString());
					if (current>1)
						etPercent.setText("" +(current-1));
				} catch (Exception e) {}
			}
		});
	}

	public void setupUI(View view) {
		//Set up touch listener for non-text box views to hide keyboard.
		if(!(view instanceof EditText)) {
			view.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					hideSoftKeyboard(Manager.this);
					return false;
				}
			});
		}
	}

	public static void hideSoftKeyboard(Activity activity) {
		InputMethodManager inputMethodManager = (InputMethodManager)  activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
	}

	private class Calc extends Thread {

		private int bill;
		private int ppl;
		private int percent;
		private TextView tvPer;
		private TextView tvTot;
		private TextView tvTip;
		private int onePpl;
		private int totalBill;
		private int totalTip;

		@Override
		public void run() {

			etBill = (EditText) findViewById(R.id.bill);
			etPpl = (EditText) findViewById(R.id.numOfPpl);
			etPercent = (EditText) findViewById(R.id.percent);
			tvPer = (TextView) findViewById(R.id.txt1ans);
			tvTot = (TextView) findViewById(R.id.txt2ans);
			tvTip = (TextView) findViewById(R.id.txt3ans);
			String tempString;
			double tempDouble;

			while (true){
				try {
					Thread.sleep(50);
					/* Get values */
					tempString = etBill.getText().toString();
					bill = Integer.valueOf(tempString);
					tempString = etPpl.getText().toString();
					ppl = Integer.valueOf(tempString);
					tempString = etPercent.getText().toString();
					percent = Integer.valueOf(tempString);	

					tempDouble = (double)bill * (((double)percent + 100.)/100.);
					totalBill = (int) tempDouble;
					onePpl = (int) (tempDouble / ppl);
					totalTip = totalBill-bill;

					if (totalBill<1 || totalBill>9999999)
						continue;
					if (ppl<1 || ppl>99999)
						continue;
					if (percent<1 || percent>99999)
						continue;
					if ((totalTip<1) || (totalTip>999999))
						continue;

					runOnUiThread(new Runnable() {  
						@Override
						public void run() {
							tvPer.setText("₪ " + Integer.toString(onePpl));
							tvTot.setText("₪ " + Integer.toString(totalBill));
							tvTip.setText("₪ " + Integer.toString(totalTip));
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {  
						@Override
						public void run() {
							tvPer.setText("?");
							tvTot.setText("?");
							tvTip.setText("?");
						}
					});
				}
			}
		}
	}
}









