package com.mediatek.factorymode.fmradio;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
//import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
//import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

public class FMRadio extends Activity implements View.OnClickListener
{
  private Button mBtFailed;
  private Button mBtOk;
  SharedPreferences mSp;
  private final static int REQUESTCODE = 701;
  
  private static final String FACTORYMODE_FMRADIO_TEST_END = "com.mediatek.factorymode.FMRADIO.FMRADIO_TEST_END";

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
    localBuilder.setTitle(R.string.FMRadio_notice);
    localBuilder.setMessage(R.string.FMRadio_success_message);
    localBuilder.setPositiveButton(R.string.Success, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            Utils.SetPreferences(FMRadio.this, FMRadio.this.mSp, R.string.headsethook_name, "success");
        }
    });
    localBuilder.setNegativeButton(R.string.Failed, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            Utils.SetPreferences(FMRadio.this, FMRadio.this.mSp, R.string.headsethook_name, "failed");
        }
        
    });
    localBuilder.create().show();
  }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    Intent intent = new Intent(FACTORYMODE_FMRADIO_TEST_END);
    sendBroadcast(intent);	
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.fmradio_name, "success");
        finish();
    }
    else{
        Utils.SetPreferences(this, localSharedPreferences, R.string.fmradio_name, "failed");
        finish();
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
    Intent localIntent = new Intent();   
    localIntent.setClassName("com.android.fmradio", "com.android.fmradio.FmMainActivity");
    //startActivityForResult(localIntent, 5);
    startActivity(localIntent);
  }
}
