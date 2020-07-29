package com.mediatek.factorymode.nfctest;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.ComponentName;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

import android.app.Activity;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.MifareClassic;
import android.os.Bundle;
import android.widget.TextView;
import android.util.Log;
import android.app.PendingIntent;
import android.content.IntentFilter;
import android.view.View;
import android.widget.Button;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.widget.Toast;
import android.os.Handler;
import android.os.Message;
//import com.itrus.nfccard.card.CardManager;

public class nfctest extends Activity implements View.OnClickListener {
	private Button mBtFailed;
	private Button mBtOk;
	SharedPreferences mSp;
    private boolean isBooleanNFC;
	private boolean isDetectCard;
	private static final String TAG = "nfcfactorytest";
	NfcAdapter nfcAdapter;
	private TextView promt;
	private PendingIntent pendingIntent;	
	private IntentFilter mIntentFilter;
	private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case 1:
			      if(!isDetectCard && nfcAdapter!=null && nfcAdapter.isEnabled()){
		              promt.setText(R.string.nfc_is_connect);
					  Log.d("zmj", "nfcAdapter.isEnabled()="+nfcAdapter.isEnabled());
		          }else{
				      mHandler.sendEmptyMessageDelayed(1, 100);
				  }
                  break;
			case  2:
			     Log.d("zmj", "isDetectCard="+isDetectCard);
				if(isDetectCard) {
				   promt.setText(getString(R.string.nfc_tag_ok_string));
				}else{
				   mHandler.sendEmptyMessageDelayed(2, 100);
				}
				 break;
          }
      }
	 };
	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		if (paramView.getId() == this.mBtOk.getId()) {
			this.mBtOk.setBackgroundColor(getResources().getColor(R.color.Blue));
			Utils.SetPreferences(this, localSharedPreferences, R.string.nfctest, "success");
			if (nfcAdapter.isEnabled()&&isBooleanNFC){
				nfcAdapter.disable();
			}
			finish();
		} else {
			this.mBtFailed.setBackgroundColor(getResources().getColor(R.color.Blue));
			Utils.SetPreferences(this, localSharedPreferences, R.string.nfctest, "failed");
			if (nfcAdapter.isEnabled()&&isBooleanNFC){
				nfcAdapter.disable();
			}
			finish();
		}
	}

	@Override
	protected void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		setContentView(R.layout.nfctest);
		Log.d("wangshengyuan-nfctest", "onCreate");
		promt = (TextView) findViewById(R.id.txt_value_nfc);
		nfcAdapter = NfcAdapter.getDefaultAdapter(this);
		Log.d("wangshengyuan-nfctest", "nfcAdapter = " + nfcAdapter);
		if(nfcAdapter == null) {
			return;
		}
		this.mSp = getSharedPreferences("FactoryMode", 0);
		this.mBtOk = (Button) findViewById(R.id.nfc_bt_ok);
		this.mBtOk.setOnClickListener(this);
		this.mBtFailed = (Button) findViewById(R.id.nfc_bt_failed);
		this.mBtFailed.setOnClickListener(this);
		pendingIntent = PendingIntent.getActivity(this, 0, new Intent(this, getClass()).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),0);
	    mIntentFilter = new IntentFilter(); 
        mIntentFilter.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED);
	    mIntentFilter.addAction(NfcAdapter.ACTION_TECH_DISCOVERED);
	    mIntentFilter.addAction(NfcAdapter.ACTION_TAG_DISCOVERED);
		
  }
  
  @Override
	protected void onResume() {
		super.onResume();
		Log.d("wangshengyuan-nfctest", "nfcAdapter.isEnabled()1= " + nfcAdapter.isEnabled());
		if(!nfcAdapter.isEnabled()) {
		    isBooleanNFC = true;
		    nfcAdapter.enable();
			promt.setText(R.string.nfc_isnot_connect);
			Log.d("wangshengyuan-nfctest", "nfcAdapter.isEnabled()2= " + nfcAdapter.isEnabled());
		}
		mHandler.sendEmptyMessage(1);
        nfcAdapter.enableForegroundDispatch(this, pendingIntent ,new IntentFilter[] { mIntentFilter }, null);
	 }
  @Override  
    protected void onNewIntent(Intent intent) {  
        super.onNewIntent(intent);  
		String action = intent.getAction();
		Log.i("zmj","action="+action);
        if(NfcAdapter.ACTION_NDEF_DISCOVERED.equals(action)||
		   NfcAdapter.ACTION_TECH_DISCOVERED.equals(action)||
		   NfcAdapter.ACTION_TAG_DISCOVERED.equals(action)) {  
           Toast.makeText(this, "read card", Toast.LENGTH_LONG).show();
		   isDetectCard = true;
		   mHandler.sendEmptyMessage(2);
          
        }  
    }  
  
	@Override
	protected void onPause() {
		super.onPause();
        nfcAdapter.disableForegroundDispatch(this);
	}

	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		if (nfcAdapter.isEnabled()&&isBooleanNFC)
			nfcAdapter.disable();
		super.onDestroy();
	}
}
