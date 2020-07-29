package com.mediatek.factorymode;

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
import android.os.Build;

import com.mediatek.factorymode.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import android.os.SystemProperties;
import android.util.Log;
import android.app.AlertDialog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import android.content.Context;

public class HardwareInformation extends Activity implements View.OnClickListener {

    private TextView mInfo;
	Context mContext = null;
	private Button mBtnTeeOk = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.hardware_info);

	  
	  this.mBtnTeeOk = (Button)findViewById(R.id.hardware_info_bt_ok);
      this.mBtnTeeOk.setOnClickListener(this);
	  
	  mContext = HardwareInformation.this;
	  
	  showHardwareInfo(mContext);
    }

	public void onClick(View paramView) {
		finish();
	}

  //add by wangshengyuan for joya hardware info begin
    static String getHardwareFromKernel(String path) {
    int len = 0;
    char[] buffer = new char[1024];
    FileReader file = null; 
     
    try {
      file = new FileReader("/sys/devices/platform/HardwareInfo/" + path);
      len = file.read(buffer, 0, 1024);
      if (file != null) {
        file.close();
        file = null;
      }
    } catch (Exception e) {
      try {
        if (file != null) {
          file.close();
          file = null;
        }
      } catch (IOException io) {
        Log.e("wangshengyuan", "getHardwareFromKernel fail");
      }
    }
    
    return new String(buffer);
  }
     
  static String getJyinnerSW(Context context) {
    String jyinnerSW = SystemProperties.get("ro.build.joya.inner.sw");
    
    return jyinnerSW;
  }
  
  static String getMemoryInfo(Context context) {

    int count;
        byte[] bytes = new byte[256];
    InputStream inputst;        
    String flashType = context.getResources().getString(R.string.unknownflash);
    String FILE_CID = "/sys/block/mmcblk0/device/cid";
    
    try {
      inputst = new FileInputStream(FILE_CID);
      count = inputst.read(bytes);
      inputst.close();
      flashType = new String(bytes, 0, count);
    } catch (FileNotFoundException e) {
        e.printStackTrace();
    } catch (IOException ie) {
        ie.printStackTrace();
    }
                         
    String[] flashNameStr=context.getResources().getStringArray(R.array.flash_namestr_index);
    String[] flashCidStr=context.getResources().getStringArray(R.array.flash_cid_numbers_index);
    String flashcidstr=null;
    int i=0, n=0;
    
    for( i=0; i < flashCidStr.length; i++){
      Boolean compare = flashType.toLowerCase().contains(flashCidStr[i].toLowerCase());
      if(compare)
      {
        flashcidstr=flashNameStr[i];
        break;
      }
    }
    if(flashcidstr == null){
      flashcidstr=flashType;
    }
    
    return flashcidstr;
    }
    
    static void showHardwareInfo(Context context) {
         String info = getHardwareFromKernel("00_lcm");
         
         info += getHardwareFromKernel("01_ctp");
         info += getHardwareFromKernel("02_main_camera");
         info += getHardwareFromKernel("03_sub_camera");
         //info += getHardwareFromKernel("04_**");
         info += getHardwareFromKernel("05_gsensor");
         //info += getHardwareFromKernel("06_msensor");
         info += getHardwareFromKernel("07_alsps");
         //info += getHardwareFromKernel("08_gyro");
         info += getHardwareFromKernel("09_wifi");
         info += getHardwareFromKernel("10_bt");
         info += getHardwareFromKernel("11_gps");
         info += getHardwareFromKernel("12_fm");
         info += getHardwareFromKernel("13_fingerprint");
         info += "JoyaInternelSW: ";
         info += getJyinnerSW(context);
         info += "\n";
         info += "FlashInfomation: ";
         info += getMemoryInfo(context);

         AlertDialog alert = new AlertDialog.Builder(context).setTitle(context.getResources().getString(R.string.HardwareInformation))
             .setMessage(info).setPositiveButton(android.R.string.ok, null)
             .setCancelable(false).create();
         alert.show();
    }
    //add by wangshengyuan for joya hardware info end
}

