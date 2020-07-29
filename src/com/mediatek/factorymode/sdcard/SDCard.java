package com.mediatek.factorymode.sdcard;

import com.mediatek.factorymode.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import android.util.Log;
import com.mediatek.storage.StorageManagerEx;
import android.os.storage.StorageManager;
import android.content.Context;
import android.os.storage.StorageVolume;
import android.os.storage.VolumeInfo;
import android.os.storage.DiskInfo;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

public class SDCard extends Activity
  implements View.OnClickListener
{
  private static final String TAG = "SDCard";

  private Button mBtFailed;
  private Button mBtOk;
  private TextView mInfo;
  private SharedPreferences mSp;
  int i = 1 ;
  private boolean isSdcard =false;
  private Handler mHandler = new Handler();
  private Runnable myRunnable= new Runnable() {    
	        public void run() { 
	        	 SDCardSizeTest();
	        }  
	}; 
  public void SDCardSizeTest()
  {
	    StringBuilder localStringBuilder = new StringBuilder();
	    StatFs localStatFs;
		String sdPath = null;
		sdPath =getExternalSdPath(this);
		if (sdPath != null) 
		{
			localStatFs = new StatFs(sdPath);
			long l1 = localStatFs.getBlockCount();
			long l2 = localStatFs.getBlockSize();
			long l3 = localStatFs.getAvailableBlocks();
			long l4 = l1 * l2 / 1024L / 1024L;
			long l5 = l3 * l2 / 1024L / 1024L;
			if(l4 == 0){
				localStringBuilder.append(this.getString(R.string.sdcard_tips_failed));
				isSdcard=false;
			}else{
				localStringBuilder.append(this.getString(R.string.sdcard_tips_success)).append("\n\n");
				localStringBuilder.append(this.getString(R.string.sdcard_totalsize)).append(l4).append("MB").append("\n\n");
				localStringBuilder.append(this.getString(R.string.sdcard_freesize)).append(l5).append("MB").append("\n\n");
				isSdcard = true;
			}
		}else
		{
			localStringBuilder.append(this.getString(R.string.sdcard_tips_failed));
			isSdcard=false;
		}	  
      this.mInfo.setText(localStringBuilder.toString());
	  mBtOk.setEnabled(isSdcard);
  }
  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()&&isSdcard){
        Utils.SetPreferences(this, localSharedPreferences, R.string.sdcard_name, "success");
        finish();
    }
    else  if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.sdcard_name, "failed");
        finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.sdcard);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    TextView localTextView = (TextView)findViewById(R.id.sdcard_info);
    this.mInfo = localTextView;
    this.mBtOk = (Button)findViewById(R.id.sdcard_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.sdcard_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    SDCardSizeTest();
	registerReceiver();
  }

   private String getExternalSdPath(Context context) {
        Log.d(TAG, "-->getExternalSdPath()");
        String externalPath = null;
        StorageManager mStorageManager = (StorageManager) context
                .getSystemService(Context.STORAGE_SERVICE);
        StorageVolume[] volumes = mStorageManager.getVolumeList();
        for (StorageVolume volume : volumes) {
            String volumePathStr = volume.getPath();
            if (Environment.MEDIA_MOUNTED.equalsIgnoreCase(volume.getState())) {
                Log.d(TAG, volumePathStr + " is mounted!");
                VolumeInfo volumeInfo = mStorageManager.findVolumeById(volume.getId());
                if (volume.isEmulated()) {
                    String viId = volumeInfo.getId();
                    Log.d(TAG, "Is emulated and volumeInfo.getId() : " + viId);
                    // If external sd card, the viId will be like "emulated:179,130"
                    if (!viId.equalsIgnoreCase("emulated")) {
                        externalPath = volumePathStr;
                        break;
                    }
                } else {
                    DiskInfo diskInfo = volumeInfo.getDisk();
                    if (diskInfo == null) {
                        continue;
                    }
                    String diId = diskInfo.getId();
                    Log.d(TAG, "Is not emulated and diskInfo.getId() : " + diId);
                    // If external sd card, the diId will be like "disk:179,128"
                    if (!diId.equalsIgnoreCase("disk:179,0")) {
                        externalPath = volumePathStr;
                        break;
                    }
                }
            } else {
                Log.d(TAG, volumePathStr + " is not mounted!");
            }
        }
        //Log.d(TAG, "<--getExternalSdPath() : externalPath = " + externalPath);
        return externalPath;
    }
	private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
		    @Override
		    public void onReceive(Context context, Intent intent) {
			  // android.util.Log.d(TAG, "action=" + intent.getAction());
		       mHandler.post(myRunnable);
		    }
		};
	private void registerReceiver() {
        IntentFilter usbDeviceStateFilter = new IntentFilter();
		usbDeviceStateFilter.addAction(Intent.ACTION_MEDIA_MOUNTED);
		usbDeviceStateFilter.addAction(Intent.ACTION_MEDIA_UNMOUNTED);
		usbDeviceStateFilter.addAction(Intent.ACTION_MEDIA_REMOVED);
		usbDeviceStateFilter.addDataScheme("file");
        registerReceiver(mUsbReceiver, usbDeviceStateFilter);
    }
	@Override
	public void onDestroy(){
		super.onDestroy();
		if (mUsbReceiver != null) {
            unregisterReceiver(mUsbReceiver);
            mUsbReceiver = null;
        }
	}
}
