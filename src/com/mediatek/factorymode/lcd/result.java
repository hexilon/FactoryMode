package com.mediatek.factorymode.lcd;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import android.content.Context;
import android.app.AlertDialog;
import android.graphics.Color;
import android.view.Window;
import android.widget.Button;
import android.view.View;
import android.view.View.OnClickListener;
public class result extends Activity implements View.OnClickListener
{
  private Button mBtFailed;
  private Button mBtOK;
  private SharedPreferences mSp;
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.result);
	this.mBtOK  = (Button)findViewById(R.id.result_check_bt_ok);
	this.mBtOK.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.result_check_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
}
   public void onClick(View paramView)
  {
	  SharedPreferences localSharedPreferences = this.mSp;
	    if(paramView.getId() == this.mBtOK.getId()){
	        Utils.SetPreferences(this, localSharedPreferences, R.string.lcd_name, "success");
	        finish();
	    }else if(paramView.getId() == this.mBtFailed.getId()){
	        Utils.SetPreferences(this, localSharedPreferences, R.string.lcd_name, "failed");
	        finish();
       }
  }
}
