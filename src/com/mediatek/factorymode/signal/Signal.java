package com.mediatek.factorymode.signal;

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

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

import com.mediatek.factorymode.FactoryMode;//add joyar
import android.content.Context;//add joyar
import android.util.Log;//add joyar
import android.provider.Settings;

public class Signal extends Activity
  implements View.OnClickListener
{
  private Button mBtFailed;
  private Button mBtOk;
  SharedPreferences mSp;
  int canOpenMainCamera = 0;//add joyar
  SharedPreferences spControl = null;//add joyar

  boolean fds_callToHangup = false;
  public final static String KEY_CALLTOHANGUP_STATUS         = "com.fds.callToHangup";

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
     if (paramIntent != null) {
        fds_callToHangup = paramIntent.getBooleanExtra("fds_callToHangup", false);
	  android.util.Log.d("fdsheng", " -- onActivityResult--paramIntent="+paramIntent);
     	}

    android.util.Log.d("fdsheng", " -- onActivityResult--fds_callToHangup="+fds_callToHangup);
   
    /*AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(R.string.FMRadio_notice);
    localBuilder.setMessage(R.string.HeadSet_hook_message);
    localBuilder.setPositiveButton(R.string.Success, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            Utils.SetPreferences(Signal.this, Signal.this.mSp, R.string.headsethook_name, "success");
        }
    });
    localBuilder.setNegativeButton(R.string.Failed, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            Utils.SetPreferences(Signal.this, Signal.this.mSp, R.string.headsethook_name, "failed");
        }
        
    });
    localBuilder.create().show();*/
  }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.telephone_name, "success");
        finish();
    }
    else{
        Utils.SetPreferences(this, localSharedPreferences, R.string.telephone_name, "failed");
        finish();
    }
  }

  @Override
  protected void onResume() {
    super.onResume();

    if (Settings.System.getInt(getContentResolver(), KEY_CALLTOHANGUP_STATUS, 0) == 0) {
    	  mBtOk.setEnabled(false);
    	}
    else
    	{
    	  mBtOk.setEnabled(true);
    	}
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.signal);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.signal_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.signal_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    Settings.System.putInt(this.getContentResolver(), KEY_CALLTOHANGUP_STATUS,0);
    Uri localUri = Uri.fromParts("tel", "112", null);
    Intent localIntent = new Intent("android.intent.action.CALL_PRIVILEGED", localUri);
    //startActivityForResult(localIntent, 5);

	if (spControl == null) {//add joyar start
		spControl = getSharedPreferences(FactoryMode.CENTER_CONTROL, Context.MODE_PRIVATE);
	}
	canOpenMainCamera = spControl.getInt(FactoryMode.CENTER_CONTROL, 0);
	if (canOpenMainCamera == 1) {
		startActivityForResult(localIntent, 5);
	}
	spControl.edit().putInt(FactoryMode.CENTER_CONTROL, 0).commit();
	Log.i("bbbb", "Signal onCreate #canOpenMainCamera=" + canOpenMainCamera);//add joyar end
  }
}