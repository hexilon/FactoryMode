package com.mediatek.factorymode.vibrator;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

public class Vibrator extends Activity
  implements View.OnClickListener
{
  private Button mBtFailed;
  private Button mBtOk;
  private SharedPreferences mSp;
  private android.os.Vibrator mVibrator;

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.vibrator_name, "success");
        finish();
    }else{
        Utils.SetPreferences(this, localSharedPreferences, R.string.vibrator_name, "failed");
        finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.vibrator);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.vibrator_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.vibrator_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    this.mVibrator = (android.os.Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
    this.mVibrator.vibrate(new long[] { 100, 100, 100, 1000 }, 0);
  }

  public void onDestroy()
  {
    super.onDestroy();
    this.mVibrator.cancel();
  }
}