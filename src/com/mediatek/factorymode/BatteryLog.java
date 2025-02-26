package com.mediatek.factorymode;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.mediatek.factorymode.FactoryMode;

public class BatteryLog extends Activity
  implements View.OnClickListener
{
  private static final int EVENT_TICK = 1;
  private static final int EVENT_AVERAGE = 2;
  private boolean mIsStart = true;
  String flag = "";
  private Button mBtFailed;
  private Button mBtOK;

  private TextView mHealth;
  private IntentFilter mIntentFilter;
  private TextView mLevel;
  private TextView mScale;
  private SharedPreferences mSp;
  private TextView mStatus;
  private TextView mTechnology;
  private TextView mTemperature;
  private TextView mUptime;
  private TextView mVoltage;
  private boolean isCharged =false;
  

  private final String tenthsToFixedString(int paramInt)
  {
	  int tens = paramInt / 10;
      return new String("" + tens + "." + (paramInt - 10*tens));
   /* int i = paramInt / 10;
    StringBuilder localStringBuilder = new StringBuilder().append("").append(i).append(".");
    int j = i * 10;
    int k = paramInt - j;
    String str = k;
    return new String(str);*/
  }
  private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case EVENT_TICK:
                  updateBatteryStats();
                  sendEmptyMessageDelayed(EVENT_TICK, 1000);                   
                  break;
			case  EVENT_AVERAGE:
				if (mIsStart) {
					mHandler.sendEmptyMessageDelayed(2, 100);
				}
          }
      }

		private void updateBatteryStats() {
			// TODO Auto-generated method stub
			long uptime = SystemClock.elapsedRealtime();
	        mUptime.setText(DateUtils.formatElapsedTime(uptime / 1000));
		}
  };
  
  private BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
 	 
		@Override
		public void onReceive(Context arg0, Intent arg1) {
			// TODO Auto-generated method stub
			String action = arg1.getAction();
          if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
              int plugType = arg1.getIntExtra("plugged", 0);

              mLevel.setText("" + arg1.getIntExtra("level", 0));
              mScale.setText("" + arg1.getIntExtra("scale", 0));
              mVoltage.setText("" + arg1.getIntExtra("voltage", 0) + " "
                      + getString(R.string.battery_info_voltage_units));
              mTemperature.setText("" + tenthsToFixedString(arg1.getIntExtra("temperature", 0))
                      + getString(R.string.battery_info_temperature_units));
              mTechnology.setText("" + arg1.getStringExtra("technology"));
              
              int status = arg1.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
              String statusString;
              if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                  statusString = getString(R.string.battery_info_status_charging);
                  if (plugType > 0) {
                      statusString = statusString + " " + getString(
                              (plugType == BatteryManager.BATTERY_PLUGGED_AC)
                                      ? R.string.battery_info_status_charging_ac
                                      : R.string.battery_info_status_charging_usb);
                  }
			     isCharged =true;
              } else if (status == BatteryManager.BATTERY_STATUS_DISCHARGING) {
                  statusString = getString(R.string.battery_info_status_discharging);
				  isCharged =false;
              } else if (status == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
                  statusString = getString(R.string.battery_info_status_not_charging);
				  isCharged =false;
              } else if (status == BatteryManager.BATTERY_STATUS_FULL) {
                  statusString = getString(R.string.battery_info_status_full);
				  isCharged =true;
              } else {
                  statusString = getString(R.string.battery_info_status_unknown);
				  isCharged =false;
              }
              mStatus.setText(statusString);
			  if(mBtOK!=null){
			    mBtOK.setEnabled(isCharged);
			  }
              int health = arg1.getIntExtra("health", BatteryManager.BATTERY_HEALTH_UNKNOWN);
              String healthString;
              if (health == BatteryManager.BATTERY_HEALTH_GOOD) {
                  healthString = getString(R.string.battery_info_health_good);
              } else if (health == BatteryManager.BATTERY_HEALTH_OVERHEAT) {
                  healthString = getString(R.string.battery_info_health_overheat);
              } else if (health == BatteryManager.BATTERY_HEALTH_DEAD) {
                  healthString = getString(R.string.battery_info_health_dead);
              } else if (health == BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE) {
                  healthString = getString(R.string.battery_info_health_over_voltage);
              } else if (health == BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE) {
                  healthString = getString(R.string.battery_info_health_unspecified_failure);
              } else {
                  healthString = getString(R.string.battery_info_health_unknown);
              }
              mHealth.setText(healthString);
          }
                      
			
		}
   };
  public void onClick(View paramView)
  {
	  SharedPreferences localSharedPreferences = this.mSp;
	    int i = R.string.battery_name;
		//android.util.Log.i("zmj","isCharged="+isCharged);
	    if(paramView.getId() == this.mBtOK.getId()&&isCharged){
	        Utils.SetPreferences(this, localSharedPreferences, i, "success");
	        finish();
	    }else if(paramView.getId() == this.mBtFailed.getId()){
	        Utils.SetPreferences(this, localSharedPreferences, i, "failed");
	        finish();
       }
  }
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.battery_info);
    mIntentFilter  = new IntentFilter();
    this.mIntentFilter.addAction("android.intent.action.BATTERY_CHANGED");
	mHandler.sendEmptyMessage(2);
  }

  public boolean onCreateOptionsMenu(Menu paramMenu)
  {
    paramMenu.add(0, 1, 1, 2131230885);
    return super.onCreateOptionsMenu(paramMenu);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    setResult(1);
    finish();
    return true;
  }

  public void onPause()
  {
    super.onPause();
    this.mHandler.removeMessages(1);
    BroadcastReceiver localBroadcastReceiver = this.mIntentReceiver;
    unregisterReceiver(localBroadcastReceiver);
  }

  public void onResume()
  {
    super.onResume();
    mStatus = (TextView)findViewById(R.id.status);
    mLevel = (TextView)findViewById(R.id.level);
    mScale = (TextView)findViewById(R.id.scale);
    mHealth = (TextView)findViewById(R.id.health);
    mTechnology = (TextView)findViewById(R.id.technology);
    mVoltage = (TextView)findViewById(R.id.voltage);
    mTemperature = (TextView)findViewById(R.id.temperature);
    mUptime = (TextView) findViewById(R.id.uptime);
    Button localButton1 = (Button)findViewById(2131165207);
    this.mBtOK = localButton1;
	this.mBtOK.setOnClickListener(this);
    Button localButton2 = (Button)findViewById(2131165208);
    this.mBtFailed = localButton2;
    this.mBtFailed.setOnClickListener(this);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    this.mHandler.sendEmptyMessageDelayed(1, 1000L);
    BroadcastReceiver localBroadcastReceiver = this.mIntentReceiver;
    IntentFilter localIntentFilter = this.mIntentFilter;
    registerReceiver(localBroadcastReceiver, localIntentFilter);
  }
 
  private String getCurrent() {
        String bat_acerage = "null";
		try {
			bat_acerage = readCurrentFile(new File("/sys/class/power_supply/battery/BatteryAverageCurrent"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return bat_acerage;
    }
	
	public String readCurrentFile(File file) throws IOException {
        InputStream input = new FileInputStream(file);
       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(
                   input));
           StringBuilder sb = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
               sb.append(line);
           }
           return sb.toString();
       } finally {
           input.close();
       }
 }
  
   @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(2);
        mIsStart = false;
    }
}
