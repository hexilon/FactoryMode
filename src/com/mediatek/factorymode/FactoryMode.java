package com.mediatek.factorymode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.content.ComponentName;
//import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
import android.util.Log;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;//add joyar
import android.content.BroadcastReceiver;//add joyar
import android.content.Intent;//add joyar
import android.content.IntentFilter;//add joyar

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import com.mediatek.common.featureoption.FeatureOption;
public class FactoryMode extends Activity
  implements AdapterView.OnItemClickListener
{
  public View.OnClickListener cl;
  final int[] itemString;
  private MyAdapter mAdapter;
  private Button mBtAll;
  private Button mBtAuto;
  private GridView mGrid;
  private boolean isHeadSetInSerted = false;
  private boolean hasPressedFMRadio = false;

  //@Signature({"Ljava/util/List", "<", "Ljava/lang/String;", ">;"})
  private List mListData;
  private SharedPreferences mSp = null;
  private static final int TEST = 1;
  private  TextView localTextView ;
  private Handler mHandler = new Handler() {
      @Override
      public void handleMessage(Message msg) {
          switch (msg.what) {
              case TEST:
				 if(localTextView!=null){
					FactoryMode.this.SetColor(localTextView,localTextView); 
					if(mAdapter!=null){
					  mAdapter.notifyDataSetChanged();
					}
                    					
				 }            
                  break;
          }
      }
};
  public FactoryMode() {
    int[] arrayOfInt = {
      R.string.touchscreen_name,
      R.string.lcd_name,
      R.string.gps_name,
      R.string.battery_name, 
	    R.string.KeyCode_name,
      R.string.speaker_name,
      R.string.headset_name,
      R.string.microphone_name,
      /*R.string.microphone_namesub,*/
	    R.string.earphone_name,
      R.string.wifi_name,
      R.string.bluetooth_name,
      R.string.vibrator_name, 
	    R.string.telephone_name,
      R.string.backlight_name, 
	    R.string.memory_name,
      R.string.gsensor_name,
      /*R.string.bg_touch_name,
      R.string.msensor_name,
      R.string.gyroscope,
      R.string.lsensor_name,  
	    R.string.psensor_name,*/
      R.string.sdcard_name,
      R.string.camera_name,
      R.string.subcamera_name, 
            R.string.second_maincamera,
	    R.string.fmradio_name,
      R.string.sim_name,
      R.string.version_information,
      R.string.led_name,
      R.string.calibration,
      /*R.string.hall,
      R.string.otg,
      R.string.led_prev_name,
      R.string.fingerprint,
      R.string.gsensor_calibrate*/
	  R.string.clear_all
	  };
    this.itemString = arrayOfInt;
  }

  private void SetColor(TextView paramTextView,TextView localTextView)
  {
      if(this.itemString.length == 0)return;
    SharedPreferences localSharedPreferences1 = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences1;
    int localObject = 0;
    int i = this.itemString.length;
    while (true)
    {
    if (localObject >= i)
      break;
    Resources localResources = getResources(); 
    int j = this.itemString[localObject];
    String str1 = localResources.getString(j);
    String str2 = paramTextView.getText().toString();
    String str4;
    if (str1.equals(str2))
    {
      SharedPreferences localSharedPreferences2 = this.mSp;
      int k = this.itemString[localObject];
      String str3 = getString(k);
      str4 = localSharedPreferences2.getString(str3, null);
      
      if (str4.equals("success"))
      {
         int l = getApplicationContext().getResources().getColor(R.color.Blue);
	   		 localTextView.setBackgroundResource(R.drawable.btn_default_normal_success);
         //paramTextView.setTextColor(l);
      }
      else if (str4.equals("default"))
      {
          int i1 = getApplicationContext().getResources().getColor(R.color.black);
           paramTextView.setTextColor(i1);
         }
      else if (str4.equals("failed"))
      {
        int i2 = getApplicationContext().getResources().getColor(R.color.Red);
        //paramTextView.setTextColor(i2);
				localTextView.setBackgroundResource(R.drawable.btn_default_normal_failed);
      }
     
    }
    ++localObject;
  }
  }

 // @Signature({"()", "Ljava/util/List", "<", "Ljava/lang/String;", ">;"})
  private List getData()
  {
      boolean bool1 = true;
      ArrayList localArrayList = new ArrayList();
      SharedPreferences localSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
      int localObject =0;
      while (true)
      {       
        int i = this.itemString.length;
        if (localObject >= i)
          break;
        int j = this.itemString[localObject];
        String str1 = getString(j);
        boolean bool2 = localSharedPreferences.getBoolean(str1, bool1);
        if (bool1 == bool2)
        {
          int k = this.itemString[localObject];
          String str2 = getString(k);
          localArrayList.add(str2);
        }
        ++localObject;
      }
      return localArrayList;
  }

  private void init()
  {
      String str1 = "default";
      SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
      this.mSp = localSharedPreferences;
      SharedPreferences.Editor localEditor = this.mSp.edit();
      int localObject = 0;
      while (true)
      {
        int i = this.itemString.length;
        if (localObject >= i)
          break;
        int j = this.itemString[localObject];
        String str2 = getString(j);
        String exist = localSharedPreferences.getString(str2, null);
	if(exist == null)
        	localEditor.putString(str2, str1);
        ++localObject;
      }
      String str3 = getString(R.string.headsethook_name);
      localEditor.putString(str3, str1);
      localEditor.commit();
  }

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    System.gc();
    Intent localIntent = new Intent();
    localIntent.setClassName(this, "com.mediatek.factorymode.Report");
    Log.d("wangshengyuan","FactoryMode-onActivityResult__________-");		
    startActivity(localIntent);
  }

  public void onCreate(Bundle paramBundle)
  {
    requestWindowFeature(1);
    super.onCreate(paramBundle);
    setContentView(R.layout.main);
    init();
    
   /* this.mBtAuto = (Button)findViewById(R.id.main_bt_autotest);
    this.mBtAuto.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            String str = "com.mediatek.factorymode";
            Intent intent = new Intent();
            intent.setClassName(str, "com.mediatek.factorymode.AutoTest");
            FactoryMode.this.startActivityForResult(intent, 4096);
        }
    });*/
   
	
    this.mBtAll = (Button)findViewById(R.id.main_bt_alltest);
    /*
    this.mBtAll.setOnClickListener(new View.OnClickListener(){
        public void onClick(View v) {
            String str = "com.mediatek.factorymode";
            Intent intent = new Intent();
            intent.setClassName(str, "com.mediatek.factorymode.AllTest");
            FactoryMode.this.startActivityForResult(intent, 8192);
        }
    });
    */
    
    this.mGrid = (GridView)findViewById(R.id.main_grid);
    this.mListData = getData();
    this.mAdapter = new MyAdapter(this);
	
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.HEADSET_PLUG");
        registerReceiver(mHeadSetReceiver, intentFilter);

	storeBatteryAverageElectric = getCurrent();//add joyar
	if (spControl == null) {
		spControl = getSharedPreferences(CENTER_CONTROL, Context.MODE_PRIVATE);
	}
	Log.i("bbbb", "FactoryMode onCreate #storeBatteryAverageElectric=" + storeBatteryAverageElectric);//add joyar
  }

  SharedPreferences spControl = null;

  protected void onDestroy()
  {
    super.onDestroy();
    //Process.killProcess(Process.myPid());
  }

  //@Signature({"(", "Landroid/widget/AdapterView", "<*>;", "Landroid/view/View;", "IJ)V"})
  public void onItemClick(AdapterView paramAdapterView, View paramView, int paramInt, long paramLong)
  {
      Intent localIntent = new Intent();
      localIntent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT );
      String str1 = (String)this.mListData.get(paramInt);
      String str2 = null;
		  hasPressedFMRadio = false;
          if (getString(R.string.speaker_name).equals(str1))
            str2 = "com.mediatek.factorymode.audio.AudioTest";       
          else if (getString(R.string.battery_name).equals(str1))
            str2 = "com.mediatek.factorymode.BatteryLog";
          else if (getString(R.string.touchscreen_name).equals(str1))
            str2 = "com.mediatek.factorymode.touchscreen.LineTest";
          else  if (getString(R.string.camera_name).equals(str1))
            str2 = "com.mediatek.factorymode.camera.CameraTest";
          else if (getString(R.string.wifi_name).equals(str1))
            str2 = "com.mediatek.factorymode.wifi.WiFiTest";
          else if (getString(R.string.bluetooth_name).equals(str1))
            str2 = "com.mediatek.factorymode.bluetooth.Bluetooth";
          else if (getString(R.string.headset_name).equals(str1))
            str2 = "com.mediatek.factorymode.headset.HeadSet";
          else if (getString(R.string.earphone_name).equals(str1))
            str2 = "com.mediatek.factorymode.earphone.Earphone";
          else if (getString(R.string.vibrator_name).equals(str1))
            str2 = "com.mediatek.factorymode.vibrator.Vibrator";
          else if (getString(R.string.telephone_name).equals(str1))
            str2 = "com.mediatek.factorymode.signal.Signal";
          else if (getString(R.string.gps_name).equals(str1))
            str2 = "com.mediatek.factorymode.gps.GPS";
          else if (getString(R.string.backlight_name).equals(str1))
            str2 = "com.mediatek.factorymode.backlight.BackLight";
          else if (getString(R.string.memory_name).equals(str1))
            str2 = "com.mediatek.factorymode.memory.Memory";
		  else  if (getString(R.string.microphone_name).equals(str1))
            str2 = "com.mediatek.factorymode.microphone.MicRecorder";
          else  if (getString(R.string.microphone_namesub).equals(str1))
            str2 = "com.mediatek.factorymode.microphone.MicRecordersub";
          else if (getString(R.string.gsensor_name).equals(str1))
            str2 = "com.mediatek.factorymode.sensor.GSensor";
		  else if (getString(R.string.bg_touch_name).equals(str1))
		  	str2 = "com.mediatek.factorymode.touchscreen.BGTouch";
          else if (getString(R.string.msensor_name).equals(str1))
            str2 = "com.mediatek.factorymode.sensor.MSensor";
          else if (getString(R.string.lsensor_name).equals(str1))
            str2 = "com.mediatek.factorymode.sensor.LSensor";
          else  if (getString(R.string.psensor_name).equals(str1))
            str2 = "com.mediatek.factorymode.sensor.PSensor";
          else if (getString(R.string.sdcard_name).equals(str1))
            str2 = "com.mediatek.factorymode.sdcard.SDCard";
          else  if (getString(R.string.fmradio_name).equals(str1)) {
		  	hasPressedFMRadio = true;
		  	Log.i("bbbb", " fmradio_name #isHeadSetInSerted=" + isHeadSetInSerted);
		  	if (isHeadSetInSerted) {
				str2 = "com.mediatek.factorymode.fmradio.FMRadio";
			} else if (!isHeadSetInSerted) {
				showToast(getString(R.string.show_info));
			}
		  }
            //str2 = "com.mediatek.factorymode.fmradio.FMRadio";
          else if (getString(R.string.KeyCode_name).equals(str1))
            str2 = "com.mediatek.factorymode.KeyCode";
          else if (getString(R.string.lcd_name).equals(str1))
            str2 = "com.mediatek.factorymode.lcd.LCD";
          else if (getString(R.string.sim_name).equals(str1))
            str2 = "com.mediatek.factorymode.simcard.SimCard";
          else if(getString(R.string.subcamera_name).equals(str1))
            str2 = "com.mediatek.factorymode.camera.SubCamera";
	  	  else if(getString(R.string.led_name).equals(str1))
            str2 = "com.mediatek.factorymode.led.Led";
	  	  else if(getString(R.string.calibration).equals(str1))
            str2 = "com.mediatek.factorymode.calibration.Calibration";

        else if(getString(R.string.second_maincamera).equals(str1))
            str2 = "com.mediatek.factorymode.camera.CameraSecond";
		/*
	  else if (getString(R.string.fingerprint).equals(str1))
            str2 = "com.mediatek.factorymode.fingerprint.fingerprint";
		*/
	
		else if(getString(R.string.led_prev_name).equals(str1))
            str2 = "com.mediatek.factorymode.led.LedPrev";
			
			else if (getString(R.string.otg).equals(str1))
              str2 = "com.mediatek.factorymode.otg.otg";
		 
          else if (getString(R.string.version_information).equals(str1))
            str2 = "com.mediatek.factorymode.version.Version";
          else if (getString(R.string.gyroscope).equals(str1))
              str2 = "com.mediatek.factorymode.sensor.GyroscopeSensor";
		  else if (getString(R.string.hall).equals(str1)) {
			str2 = "com.mediatek.factorymode.hall.hall";
		  }
		 else if (getString(R.string.clear_all).equals(str1)){
		      SharedPreferences mlocalSharedPreferences = getSharedPreferences("FactoryMode", 0);
			  FactoryMode.this.mSp = mlocalSharedPreferences;
              SharedPreferences.Editor mlocalEditor = FactoryMode.this.mSp.edit();
		      for(int i =0;i<FactoryMode.this.itemString.length;i++){
			    mlocalEditor.putString(getString(FactoryMode.this.itemString[i]), "default");
			 }
			 mlocalEditor.commit();
		     mHandler.sendEmptyMessage(FactoryMode.this.TEST);
		 }
          else if (getString(R.string.gsensor_calibrate).equals(str1))
          {
            if(false)
            {

                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.joyatel.gsensorcalibrate",
                "com.joyatel.gsensorcalibrate.GSensorCaliActivity"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
         }

      if(str2!=null)
      {
          localIntent.setClassName(this, str2);
          startActivity(localIntent);
      }
  }
	private Toast mToast = null;
	private void showToast(CharSequence text) {
		  if (null == mToast) {
			  mToast = Toast.makeText(this, text, Toast.LENGTH_SHORT);
		  }
		  mToast.setText(text);
		  mToast.show();
	}

  public static String storeBatteryAverageElectric = "";//add joyar
  public static final String CENTER_CONTROL = "sp_control_camera";
  private int canOpenCamera = 1;
  protected void onResume()
  {
    super.onResume();
	hasPressedFMRadio = false;
    this.mGrid.setAdapter(this.mAdapter);
    this.mGrid.setOnItemClickListener(this);
	spControl.edit().putInt(CENTER_CONTROL, 1).commit();//add joyar, reopen camera test
	canOpenCamera = spControl.getInt(FactoryMode.CENTER_CONTROL, 0);
	Log.i("bbbb", "FactoryMode onResume #canOpenCamera=" + canOpenCamera);
  }
    private String getCurrent() {//add joyar start
        String bat_acerage = "null";
		try {
			bat_acerage = readCurrentFile(new File("/sys/class/power_supply/battery/BatteryAverageCurrent"));
		} catch (IOException e) {
			e.printStackTrace();
		}
        return bat_acerage;
    }
	
	public String readCurrentFile(File file) throws IOException {
		InputStream input = new FileInputStream(file);
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
			    input));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} finally {
			input.close();
		}
	}//add joyar end
	   
	private final BroadcastReceiver mHeadSetReceiver = new BroadcastReceiver() {  
		@Override  
		public void onReceive(Context context, Intent intent) {
		    String action = intent.getAction();
		    if (action.equals(Intent.ACTION_HEADSET_PLUG)) {
		        if(intent.hasExtra("state")) {
		            if(intent.getIntExtra("state", 0) == 0) {
						Log.i("bbbb", " headset not connected");
						isHeadSetInSerted = false;
						if (hasPressedFMRadio) {
							Toast.makeText(context, getString(R.string.show_info_not_connected), Toast.LENGTH_LONG).show();
						}
		            } else if(intent.getIntExtra("state", 0) == 1) {
						Log.i("bbbb", " connected");
						isHeadSetInSerted = true;
						if (hasPressedFMRadio) {
		                	Toast.makeText(context, getString(R.string.show_info_connected), Toast.LENGTH_LONG).show();
						}
		            }
		        }
		    }
		}
	};

  public class MyAdapter extends BaseAdapter
  {
    private LayoutInflater mInflater;

    public MyAdapter(Context arg2)
    {
     // Context localContext;
      LayoutInflater localLayoutInflater = LayoutInflater.from(arg2);
      this.mInflater = localLayoutInflater;
    }

    public MyAdapter(FactoryMode paramInt, int arg3)
    {
    }

    public int getCount()
    {
        if(FactoryMode.this.mListData == null) return 0;
        return FactoryMode.this.mListData.size();
    }

    public Object getItem(int paramInt)
    {
      return Integer.valueOf(paramInt);
    }

    public long getItemId(int paramInt)
    {
      return paramInt;
    }

    public View getView(int paramInt, View paramView, ViewGroup paramViewGroup)
    {
      View localView = this.mInflater.inflate(R.layout.main_grid, null);
      localTextView = (TextView)localView.findViewById(R.id.factor_button);
      CharSequence localCharSequence = (CharSequence)FactoryMode.this.mListData.get(paramInt);
      localTextView.setText(localCharSequence);
      FactoryMode.this.SetColor(localTextView,localTextView);
      return localView;
    }
  }
}
