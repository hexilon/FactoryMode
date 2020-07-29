package com.mediatek.factorymode.factoryreset;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.mediatek.factorymode.R;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
public class FactoryReset extends Activity implements OnClickListener {

	private static final String TAG = "bbbb";
	Context mContext;
	Button take_picture;
	AlertDialog alert;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.factory_reset);
		mContext = FactoryReset.this;
		setTitle(getResources().getString(R.string.factory_name));
		
		take_picture = (Button) findViewById(R.id.factory_btn);
		take_picture.setText(R.string.factory_name);
		take_picture.setOnClickListener(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setMessage(getResources().getString(R.string.note_title))
		       .setCancelable(false) 
		       .setPositiveButton(getResources().getString(R.string.btn_yes), new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		               /*Intent intent = new Intent("android.intent.action.MASTER_CLEAR");
		               intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
		               intent.putExtra("android.intent.extra.REASON", "MasterClearConfirm");
		               Log.d(TAG, "send broadcast ACTION_MASTER_CLEAR");
		               mContext.sendBroadcast(intent);*/
		               doMasterClear();
		        	   FactoryReset.this.finish(); 
		           } 
		       }) 
		       .setNegativeButton(getResources().getString(R.string.btn_no), new DialogInterface.OnClickListener() { 
		           public void onClick(DialogInterface dialog, int id) { 
		        	   FactoryReset.this.finish(); 
		           } 
		       }); 
		alert = builder.create(); 
	}
	
	@Override
	public void onClick(View arg0) {
		alert.show();
	}
	
    private boolean mEraseSdCard = true;
    private boolean mEraseEsims = true;
	private void doMasterClear() {
        Intent intent = new Intent(Intent.ACTION_FACTORY_RESET);
        intent.setPackage("android");
        intent.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        intent.putExtra(Intent.EXTRA_REASON, "MasterClearConfirm");
        intent.putExtra(Intent.EXTRA_WIPE_EXTERNAL_STORAGE, mEraseSdCard);
        intent.putExtra(Intent.EXTRA_WIPE_ESIMS, mEraseEsims);
        mContext.sendBroadcast(intent);
        // Intent handling is asynchronous -- assume it will happen soon.

		//Intent intent = new Intent();
		//intent.setAction("android.intent.action.MasterClear");
		//mContext.startActivity(intent);
    }

}
