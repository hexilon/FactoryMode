//Demand:MMI modify(start) add otg test in factorymode by zmj 2017/08/29
package com.mediatek.factorymode.otg;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import java.io.*;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
//import android.hardware.usb.UsbDevice;
//import android.hardware.usb.UsbManager;
import android.os.Environment;
import android.os.storage.DiskInfo;
import android.os.storage.VolumeInfo;
import android.os.storage.StorageManager;
import android.os.storage.StorageVolume;

import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.text.TextUtils;
import android.os.storage.StorageManager;
import java.util.List;
import android.os.ServiceManager;
//import android.os.storage.IMountService;
import android.os.RemoteException;
import android.util.Log;
public class otg extends Activity implements View.OnClickListener
 {
  private Button mBtFailed;
  private Button mBtOk;
  SharedPreferences mSp;
  private boolean isOtgOpen =false;
  private static final String TAG = "joyar-factory-otg";
  private TextView mInsert;
  private static boolean sHasChecked = false;
  private static boolean sHasIsUSBOTGFunction = false;
  private Context context;
  private int usbDeviceLength;
  private String path="/storage/usbotg";
  private TextView hallTextView;
  public static final String ACTION_VOLUME_STATE_CHANGED =
            "android.os.storage.action.VOLUME_STATE_CHANGED";
  
  private StorageManager mStorageManager;
  
  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()&&isOtgOpen){
        Utils.SetPreferences(this, localSharedPreferences, R.string.otg, "success");
        finish();
    }else if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.otg, "failed");
        finish();
    }
  }

  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.otg);
    mStorageManager = this.getSystemService(StorageManager.class);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.otg_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.otg_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    hallTextView = (TextView) findViewById(R.id.txt_value_otg);
    hallTextView.setText(R.string.otg_toast);
    mInsert =(TextView)findViewById(R.id.value_otg); 
    isOtgOpen=isUSBOTG();	
    mBtOk.setEnabled(isOtgOpen);
    registerReceiver();
  }
   private Handler mHandler = new Handler();
   private Runnable myRunnable= new Runnable() {    
	        public void run() { 
	        	if(isOtgOpen){
				      hallTextView.setText(R.string.otg_is_connected);
				      mInsert.setText(R.string.otg_state);		
	          }else{
			          hallTextView.setText(R.string.otg_toast);
				      mInsert.setText(R.string.otg_unstate);		
			  }
	           mBtOk.setEnabled(isOtgOpen);		
	        }  
	}; 
  @Override
	protected void onResume() {
		super.onResume();
		mHandler.post(myRunnable);
		
	}
  
  @Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
    public void onStop() {
        super.onStop();
    }

	@Override
	public void onDestroy(){
		super.onDestroy();
		if (mUsbReceiver != null) {
            unregisterReceiver(mUsbReceiver);
            mUsbReceiver = null;
        }
	}

   private static final String MOUNTS_FILE = "/proc/mounts";

    public static boolean isMounted(String path) {
        boolean blnRet = false;
        String strLine = null;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(MOUNTS_FILE));

            while ((strLine = reader.readLine()) != null) {
                if (strLine.contains(path)) {
                    blnRet = true;
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                reader = null;
            }
        }
        return blnRet;
    }
	 private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		
		    @Override
		    public void onReceive(Context context, Intent intent) {
    		    String action = intent.getAction();
    				Log.d(TAG, "action=" + action);
    				if(action.equals(ACTION_VOLUME_STATE_CHANGED)){
				     isOtgOpen=isUSBOTG();
    					    mHandler.post(myRunnable);
    				}
		    }
		};
	private void registerReceiver() {
        IntentFilter usbDeviceStateFilter = new IntentFilter();
		usbDeviceStateFilter.addAction(ACTION_VOLUME_STATE_CHANGED);
        registerReceiver(mUsbReceiver, usbDeviceStateFilter);
    }


	 public boolean isUSBOTG() {
        boolean result = false;
        for(VolumeInfo volume:mStorageManager.getVolumes()) {
        	String diskID = volume.getDiskId();
            ///android.util.Log.d("liuxinwen", "isUSBOTG, diskID=" + diskID);
            if (diskID != null) {
                // for usb otg, the disk id same as disk:8:x
                String[] idSplit = diskID.split(":");
                if (idSplit != null && idSplit.length == 2) {
                    if (idSplit[1].startsWith("8,")) {
                        //Log.d("liuxinwen", "this is a usb otg");
                        return true;
                    }
                }
            }

        }
        /*try {
            IMountService mountService =
              IMountService.Stub.asInterface(ServiceManager.getService("mount"));
            if (mountService == null) {
                Log.e(TAG, "mount service is not initialized!");
                return false;
            }
            VolumeInfo[] volumeInfos = mountService.getVolumes(0);
            for (int i = 0; i < volumeInfos.length; ++i) {
                VolumeInfo vol = volumeInfos[i];
				android.util.Log.d(TAG, "vol.path="+vol.path);
                if (vol.path != null) {
                    String diskID = vol.getDiskId();
                    android.util.Log.d(TAG, "isUSBOTG, diskID=" + diskID);
                    //result = vol.isUSBOTG();
                    if (result) {
                        break;
                    }
                }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException when invoke isUSBOTG:" + e);
        }*/
        android.util.Log.d(TAG, "isUSBOTG return=" + result);
        return result ;
    }
}
//Demand:MMI modify(start) add otg test in factorymode by zmj 2017/08/29