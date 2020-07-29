package com.mediatek.factorymode.calibration;

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
import android.os.SystemProperties;//add joyar
import android.util.Log;//add joyar
import com.mediatek.factorymode.Utils;//add joyar
import com.mediatek.factorymode.R;//add joyar

public class Calibration extends Activity
  implements View.OnClickListener {

  private TextView mGSM;
  private TextView mWCDMA;
  private TextView mLTE;
  private Button mBtFailed;
  private Button mBtOK;

  private String mBarCode = "";//add joyar
  private int mBarCodeLength = 0;//add joyar
  private String mBarCodeLastChar = "";//add joyar

  private String gsmPassString = "";//add joyar
  private String wcdmaPassString = "";//add joyar
  private String ltePassString = "";//add joyar

  private SharedPreferences mSp;
  
  public void onClick(View paramView) {
	  SharedPreferences localSharedPreferences = this.mSp;
    int i = R.string.calibration;
	  //android.util.Log.i("zmj","isCharged="+isCharged);
    if(paramView.getId() == this.mBtOK.getId()){
        Utils.SetPreferences(this, localSharedPreferences, i, "success");
        finish();
    }else if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, i, "failed");
        finish();
    }
  }

  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.calibration);

    mGSM = (TextView)findViewById(R.id.gsm);
    mWCDMA = (TextView)findViewById(R.id.wcdma);
    mLTE = (TextView)findViewById(R.id.lte);

    Button localButton1 = (Button)findViewById(R.id.battery_bt_ok);
    Button localButton2 = (Button)findViewById(R.id.battery_bt_failed);

    this.mBtOK = localButton1;
    this.mBtOK.setOnClickListener(this);
    this.mBtFailed = localButton2;
    this.mBtFailed.setOnClickListener(this);

    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
  }

  public boolean onCreateOptionsMenu(Menu paramMenu) {
    return super.onCreateOptionsMenu(paramMenu);
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem) {
    finish();
    return true;
  }

  public void onPause() {
    super.onPause();
  }

  public void onResume() {
    super.onResume();
    String mRequestResult = "";

    mRequestResult = getCurrent();
    if (!mRequestResult.equals("") && !mBarCodeLastChar.equals("") && mBarCodeLastChar.contains("10")) {
      Log.i("onResume", "bbbb mRequestResult = pass");
      gsmPassString = getString(R.string.gsm_pass);
      wcdmaPassString = getString(R.string.wcdma_pass);
      ltePassString = getString(R.string.lte_pass);
      mGSM.setText(gsmPassString);
      mWCDMA.setText(wcdmaPassString);
      mLTE.setText(ltePassString);
      mLTE.setVisibility(View.GONE);//3G no contains this
    } else {
      Log.i("onResume", "bbbb mRequestResult = fail");
      gsmPassString = getString(R.string.gsm_fail);
      wcdmaPassString = getString(R.string.wcdma_fail);
      ltePassString = getString(R.string.lte_fail);
      mGSM.setText(gsmPassString);
      mWCDMA.setText(wcdmaPassString);
      mLTE.setText(ltePassString);
      mLTE.setVisibility(View.GONE);//3G no contains this
      mBtOK.setEnabled(false);
    }
  }
 
  private String getCurrent() {
    //add joyar start
    /*
    NASCO:/ $ getprop | grep serial
    getprop | grep serial
    [gsm.serial]: [YDF1711030000173                                            10 ]
    [ro.boot.serialno]: [0123456789ABCDEF]
    [ro.serialno]: [0123456789ABCDEF]
     */
    mBarCode = SystemProperties.get("gsm.serial");
    Log.i("Calibration", "bbbb getCurrent #mBarCode=" + mBarCode);
    if (mBarCode.equals("")) return "";
    mBarCodeLength = mBarCode.length();
    mBarCodeLastChar = mBarCode.substring(mBarCodeLength - 3, mBarCodeLength);//[gsm.serial]: [...10]
    Log.i("Calibration", "bbbb getCurrent #mBarCode.length()=" + mBarCodeLength);
    Log.i("Calibration", "bbbb getCurrent #mBarCode.lastString=" + mBarCodeLastChar);
    //add joyar end
    return mBarCodeLastChar;
  }
  
  @Override
  protected void onDestroy() {
      super.onDestroy();
  }
}
