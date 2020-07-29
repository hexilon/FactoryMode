package com.mediatek.factorymode;
import android.util.Log;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.wifi.ScanResult;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import com.mediatek.factorymode.gps.GPSThread;
import com.mediatek.factorymode.wifi.WiFiTools;
import java.util.List;
import android.bluetooth.BluetoothDevice;
import com.mediatek.factorymode.FactoryMode;

public class AllTest extends Activity
{
  Runnable bluerunnable;
  boolean isregisterReceiver = false;
  private BluetoothAdapter mAdapter = null;
  boolean mBlueFlag = false;
  BlueHandler mBlueHandler;
  boolean mBlueResult = false;
  boolean mBlueStatus = false;
  HandlerThread mBlueThread;
  GPSThread mGPS;
  boolean mOtherOk = false;
  BroadcastReceiver mReceiver;
  boolean mSdCardResult = false;
  SharedPreferences mSp;
  boolean mWifiConReslut = false;
  WifiHandler mWifiHandler;

  List mWifiList = null;
  boolean mWifiResult = false;
  boolean mWifiStatus = false;
  HandlerThread mWifiThread;
  WiFiTools mWifiTools;
  Message msg = null;
  Runnable wifirunnable;
  
  class AllTest1 implements Runnable
{
	    public void run()
	    {
	      long l = 3000L;
	      if (!mWifiStatus)
	      {
	        if (WifiInit())
	        {
	          mWifiResult = true;
	          /*mWifiHandler.postDelayed(this, l);
	        if (mWifiTools.IsConnection().booleanValue())
	        {
	          mWifiResult = true;
	         mWifiTools.closeWifi();
	        }
	        mWifiHandler.postDelayed(this, l);*/
	      }
	        else
	        	mWifiHandler.postDelayed(wifirunnable, l);
	     }
	    }
}

 private BroadcastReceiver AllTest2 = new BroadcastReceiver()
  {
		@Override
    public void onReceive(Context paramContext, Intent intentData)
    {
      boolean i = true;
      String str = intentData.getAction();
      
      if (("android.bluetooth.device.action.FOUND".equals(str)) ||
    		  (((BluetoothDevice)intentData.getParcelableExtra("android.bluetooth.device.extra.DEVICE")).getBondState() != 12))
      {  
         mBlueResult = i;
      }		
     }
};
  class AllTest3 implements Runnable
{
  public void run()
  {
    BlueInit();
  }
}
  public AllTest()
  {
    HandlerThread localHandlerThread1 = new HandlerThread("blueThread");
    this.mBlueThread = localHandlerThread1;
    HandlerThread localHandlerThread2 = new HandlerThread("wifiThread");
    this.mWifiThread = localHandlerThread2;
    this.mGPS = null;
    AllTest1 local1 = new AllTest1();
    this.wifirunnable = local1;   
    this.mReceiver = AllTest2;
    AllTest3 local3 = new AllTest3();
    this.bluerunnable = local3;
  }

  public void BackstageDestroy()
  {
    this.mWifiTools.closeWifi();
    BlueHandler localBlueHandler = this.mBlueHandler;
    Runnable localRunnable1 = this.bluerunnable;
    localBlueHandler.removeCallbacks(localRunnable1);
    WifiHandler localWifiHandler = this.mWifiHandler;
    Runnable localRunnable2 = this.wifirunnable;
    localWifiHandler.removeCallbacks(localRunnable2);
    if (this.isregisterReceiver == true)
    {
      BroadcastReceiver localBroadcastReceiver = this.mReceiver;
      unregisterReceiver(localBroadcastReceiver);
      this.isregisterReceiver = false;
    }
    this.mAdapter.disable();
    this.mGPS.closeLocation();
  }

  public void BlueInit()
  {
    BluetoothAdapter localBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    this.mAdapter = localBluetoothAdapter;
    this.mAdapter.enable();
    if (this.mAdapter.isEnabled() == true)
    {
      StartReciver();
      
        if (!this.mAdapter.startDiscovery())       
        this.mAdapter.startDiscovery();
    }
    else
    {
       BlueHandler localBlueHandler = this.mBlueHandler;
       Runnable localRunnable = this.bluerunnable;
       localBlueHandler.postDelayed(localRunnable, 3000L);
    }
  }

  public void OnFinish()
  {
  //  boolean bool1 = true;
    String str1 = "failed";
    String str2 = "success";
    SharedPreferences localSharedPreferences = this.mSp;
    Utils.SetPreferences(this, localSharedPreferences, R.string.memory_name, str2);//memory_name
    int i = 0;
    if(mGPS.isSuccess())
    {
    	i = R.string.gps_name;
        Utils.SetPreferences(this, localSharedPreferences, i, str2);
    }
    else
    	 Utils.SetPreferences(this, localSharedPreferences, R.string.gps_name, str1);
    	
    if(mWifiResult)
    {
    	i = R.string.wifi_name;
        Utils.SetPreferences(this, localSharedPreferences, i, str2);
    }
    else
    	Utils.SetPreferences(this, localSharedPreferences, R.string.wifi_name, str1);
    if(mBlueResult)
    {
    	i = R.string.bluetooth_name;
        Utils.SetPreferences(this, localSharedPreferences, i, str2);
    }
    else
    	Utils.SetPreferences(this, localSharedPreferences,  R.string.bluetooth_name, str1);
    finish();
  }

 /* public void SdCardInit()
  {
    if (!Environment.getExternalStorageState().equals("mounted"))
      return;
    this.mSdCardResult = true;
  }*/

  public void StartReciver()
  {
    IntentFilter localIntentFilter = new IntentFilter("android.bluetooth.device.action.FOUND");
    BroadcastReceiver localBroadcastReceiver = this.mReceiver;
    registerReceiver(localBroadcastReceiver, localIntentFilter);
    this.isregisterReceiver = true;
  }

  public boolean WifiInit() {
    boolean bool1 = false; 
    boolean mResult = false;
    String str = "";
    String mNetWorkName ;
		List<ScanResult> scanResults = this.mWifiTools.scanWifi();
	    this.mWifiList = (List)scanResults;
    mNetWorkName = str;
	    if(scanResults!=null){		    	
	    for (int i = 0; i < scanResults.size(); i++) {
         ScanResult localScanResult = scanResults.get(i);
			if (!localScanResult.capabilities.equals(str))
	          {
				StringBuilder StringBuilder1 = new StringBuilder()
						.append(mNetWorkName).append(localScanResult.SSID)
						.append("\n");
				mNetWorkName = StringBuilder1.toString();}}
			for (int i = 0; i < scanResults.size(); i++) {
				ScanResult localScanResult = scanResults.get(i);
				if (!localScanResult.capabilities.equals("[WPS]")) {
	            if (! localScanResult.capabilities.equals(str))
	              continue;
	          }
				mResult = mWifiTools.addWifiConfig(mWifiList,
						localScanResult, str);
          if(!mResult)
         	 continue;
        }
			if(mNetWorkName!=null&&mNetWorkName.length()!=0)
				bool1 = true;
	    }

	    	return bool1;


    
  }

  Intent localIntent = null;
  public final static int defaultIndex = 6;
  public final static int startTestIndex = 401;
  public final static int SubCameraIndex = 402;
  public final static int LCDIndex = 403;
  public final static int LedIndex = 404;
  public final static int GPSIndex = 405;
  public final static int WiFiTestIndex = 406;
  public final static int BluetoothIndex = 407;
  public final static int LineTestIndex = 408;
  public final static int BackLightIndex = 409;
  public final static int SDCardIndex = 410;
  public final static int VibratorIndex = 411;
  public final static int SignalIndex = 412;
  public final static int EarphoneIndex = 413;
  public final static int SimCardIndex = 414;
  public final static int CameraTestIndex = 415;
  public final static int MicRecorderIndex = 416;
  public final static int HeadSetIndex = 417;
  public final static int AudioTestIndex = 418;
  public final static int FMRadioIndex = 419;
  public final static int GSensorIndex = 420;
  public final static int VersionIndex = 421;
  public final static int CalibrationIndex = 422;
  public final static int otgIndex = 423;
  public final static int MSensorIndex = 424;
  public final static int PSensorIndex = 425;
  public final static int LSensorIndex = 426;
  public final static int GyroscopeSensorIndex = 427;
  public final static int fingerprintIndex = 428;
  public final static int HallIndex = 429;
  public final static int CameraSecondIndex = 430;
  public final static int MicRecordersubIndex = 8;
  public final static int finishTestIndex = 8;
  protected void onActivityResult(int requestCode, int resultCode, Intent intentData) {
  	
  	if (localIntent == null) {
		localIntent = new Intent();
	}
	//localIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT );
	Log.d("bbbb", "AllTest onActivityResult #RequestCode=" + requestCode
		+ " #ResultCode=" + resultCode);
	
	int i = defaultIndex;
	if (requestCode == startTestIndex) {
		if (resultCode == finishTestIndex) {
			finish();
			return;
		} else {
			localIntent.setClassName(this, "com.mediatek.factorymode.KeyCode");
			i = LCDIndex;
		}
	}
	if (requestCode == LCDIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.lcd.LCD");
		i = LedIndex;
	}
	if (requestCode == LedIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.led.Led");
		i = GPSIndex;
	}
	if (requestCode == GPSIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.gps.GPS");
		i = WiFiTestIndex;
	} 
	if (requestCode == WiFiTestIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.wifi.WiFiTest");
		i = BluetoothIndex;
	} 
	if (requestCode == BluetoothIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.bluetooth.Bluetooth");
		i = LineTestIndex;
	}
	if (requestCode == LineTestIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.touchscreen.LineTest");
		i = BackLightIndex;
	}
	if (requestCode == BackLightIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.backlight.BackLight");
		i = SDCardIndex;
	}
	if (requestCode == SDCardIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sdcard.SDCard");
		i = VibratorIndex;
	}
	if (requestCode == VibratorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.vibrator.Vibrator");
		i = SignalIndex;
	}
	if (requestCode == SignalIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.signal.Signal");
		i = EarphoneIndex;
	}
	if (requestCode == EarphoneIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.earphone.Earphone");
		i = MicRecorderIndex;
	}
	if (requestCode == MicRecorderIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.microphone.MicRecorder");
		i = HeadSetIndex;
	}
	/*if (requestCode == MicRecordersubIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.microphone.MicRecordersub");
		i = HeadSetIndex;
	}*/
	if (requestCode == HeadSetIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.headset.HeadSet");
		i = AudioTestIndex;
	}
	if (requestCode == AudioTestIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.audio.AudioTest");
		i = FMRadioIndex;
	}
	if (requestCode == FMRadioIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.fmradio.FMRadio");
		i = GSensorIndex;
	}
	if (requestCode == GSensorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sensor.GSensor");
		i = SubCameraIndex;
	}
	/*if (requestCode == MSensorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sensor.MSensor");
		i = PSensorIndex;
	}
	if (requestCode == PSensorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sensor.PSensor");
		i = LSensorIndex;
	}
	if (requestCode == LSensorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sensor.LSensor");
		i = SubCameraIndex;
	}*/
	if (requestCode == SubCameraIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.camera.SubCamera");
		i = SimCardIndex;
	}
	if (requestCode == SimCardIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.simcard.SimCard");
		i = CameraTestIndex;
	}
	if (requestCode == CameraTestIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.camera.CameraTest");
		i = CameraSecondIndex;
	}
	if (requestCode == CameraSecondIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.camera.CameraSecond");
		i = VersionIndex;
	}
	/*if (requestCode == VersionIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.otg.otg");
		i = GyroscopeSensorIndex;
	}*/
	if (requestCode == GyroscopeSensorIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.sensor.GyroscopeSensor");
		i = fingerprintIndex;
	}
	if (requestCode == fingerprintIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.fingerprint.fingerprint");
		i = VersionIndex;
	}
	if (requestCode == VersionIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.version.Version");
		i = CalibrationIndex;
	}
	if (requestCode == CalibrationIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.calibration.Calibration");
		i = finishTestIndex;
	}
	if (requestCode == HallIndex) {
		localIntent.setClassName(this, "com.mediatek.factorymode.hall.hall");
		i = finishTestIndex;
	}
	if (requestCode == finishTestIndex) {
		Log.d("bbbb","AllTest OnFinish");
		OnFinish(); 
	} else {
		localIntent.putExtra("alltest", i);
		startActivityForResult(localIntent, i);
		Log.d("bbbb", "AllTest startActivityForResult #RequestCode=" + i);
	}
  }


  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.alltest);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    WiFiTools localWiFiTools = new WiFiTools(this);
    this.mWifiTools = localWiFiTools;
    this.mWifiTools.openWifi();
    this.mWifiThread.start();
    Looper localLooper1 = this.mWifiThread.getLooper();
    WifiHandler localWifiHandler1 = new WifiHandler(localLooper1);
    this.mWifiHandler = localWifiHandler1;
    WifiHandler localWifiHandler2 = this.mWifiHandler;
    Runnable localRunnable1 = this.wifirunnable;
    localWifiHandler2.post(localRunnable1);
    this.mBlueThread.start();
    Looper localLooper2 = this.mBlueThread.getLooper();
    BlueHandler localBlueHandler1 = new BlueHandler(localLooper2);
    this.mBlueHandler = localBlueHandler1;
    BlueHandler localBlueHandler2 = this.mBlueHandler;
    Runnable localRunnable2 = this.bluerunnable;
    localBlueHandler2.post(localRunnable2);
    GPSThread localGPSThread = new GPSThread(this);
    this.mGPS = localGPSThread;
    this.mGPS.start();
	
	if (spControl == null) {
		spControl = getSharedPreferences(FactoryMode.CENTER_CONTROL, Context.MODE_PRIVATE);
	}
	canOpenCamera = spControl.getInt(FactoryMode.CENTER_CONTROL, 1);
	
    Intent localIntent = new Intent();
    localIntent.setClassName(this, "com.mediatek.factorymode.BatteryLog");
    //startActivityForResult(localIntent, startTestIndex);
    if (canOpenCamera == 1) {
    	startActivityForResult(localIntent, startTestIndex);
	}
	Log.i("bbbb", "AllTest onCreate #canOpenCamera=" + canOpenCamera);
	spControl.edit().putInt(FactoryMode.CENTER_CONTROL, 0).commit();
  }

  SharedPreferences spControl = null;
  private int canOpenCamera = 1;
  protected void onResume()
  {
    super.onResume();
	spControl.edit().putInt(FactoryMode.CENTER_CONTROL, 1).commit();
	canOpenCamera = spControl.getInt(FactoryMode.CENTER_CONTROL, 0);
	Log.i("bbbb", "AllTest onResume #canOpenCamera=" + canOpenCamera);
  }

  public void onDestroy()
  {
    super.onDestroy();
    BackstageDestroy();
  }

  class BlueHandler extends Handler
  {
    public BlueHandler()
    {
    }

    public BlueHandler(Looper arg2)
    {
     super(arg2);
    }

    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
    }
  }

  class WifiHandler extends Handler
  {
    public WifiHandler()
    {
    }

    public WifiHandler(Looper arg2)
    {
     super(arg2);
    }

    public void handleMessage(Message paramMessage)
    {
      super.handleMessage(paramMessage);
    }
  }
}
