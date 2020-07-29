package com.mediatek.factorymode.fingerprint;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.content.ComponentName;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;


import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
//wangshengyuan add end

public class fingerprint extends Activity
  implements View.OnClickListener
{
  private Button mBtFailed;
  private Button mBtOk;
  SharedPreferences mSp;
  private boolean isFinger = false;
  //wangshengyuan add 
    private static final String TAG = "fingerpringtest";

    /**
     * Fingerprint state: During cancelling we got another request to start listening, so when we
     * receive the cancellation done signal, we should start listening again.
     */
    private TextView fingerTextView;
    //wangshengyuan add end


  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()&&isFinger){
        Utils.SetPreferences(this, localSharedPreferences, R.string.fingerprint, "success");
        finish();
    }
    else if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.fingerprint, "failed");
        finish();
    }
  }

  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.fingerprint);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.fingerprint_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.fingerprint_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    fingerTextView = (TextView) findViewById(R.id.txt_value_fsensor);
    fingerTextView.setText(R.string.fp_is_connect);
	mBtOk.setEnabled(isFinger);
  }
  
  @Override    
  public boolean dispatchKeyEvent(KeyEvent event){
	  android.util.Log.e("xluo","fingerprint:"+event.getKeyCode());
		if(event.getAction()==KeyEvent.ACTION_DOWN && (event.getKeyCode()== KeyEvent.KEYCODE_F10 || event.getKeyCode()== KeyEvent.KEYCODE_F11)){
			fingerTextView.setText(getString(R.string.finger_ok));
			isFinger = true ;
			mBtOk.setEnabled(isFinger);
			return true;  
		}
	return super.dispatchKeyEvent(event);  
  }
  
  @Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
  
  @Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	
	@Override
    public void onStop() {
        super.onStop();
    }

	@Override
	public void onDestroy(){
		super.onDestroy();
	}
}