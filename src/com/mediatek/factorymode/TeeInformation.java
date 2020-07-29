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

import android.view.View;
import android.view.View.OnClickListener;
import android.os.SystemProperties;
import android.util.Log;
import android.app.AlertDialog;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileReader;
import android.content.Context;

public class TeeInformation extends Activity implements View.OnClickListener {

    private TextView mInfo;
	Context mContext = null;
	private Button mBtnTeeOk = null;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
      super.onCreate(savedInstanceState);
      setContentView(R.layout.tee_info);

	  
	  this.mBtnTeeOk = (Button)findViewById(R.id.tee_bt_ok);
      this.mBtnTeeOk.setOnClickListener(this);

	  mContext = TeeInformation.this;
	  showFingerprintTEEInfo(mContext);
    }
	
  public void onClick(View paramView) {
      finish();
  }
  

    
  //add joyar start
  static void showFingerprintTEEInfo(Context context) {
      String info = getFingerprintTEEInfo("tee_obj");
      int infoLength = 0;
      String checkOK = "";

      info += "\n";
      Log.d("SpecialCharSequenceMgr", "bbbb showFingerprintTEEInfo #info=" + info);
       
      //if (info.equals("")) return ;
      infoLength = info.length();
      checkOK = info.substring(0, infoLength -1);//check over ,it's ok!
      Log.d("SpecialCharSequenceMgr", "bbbb showFingerprintTEEInfo #checkOK=" + checkOK
        + " #infoLength=" + infoLength);

      AlertDialog alert = new AlertDialog.Builder(context).setTitle(context.getResources().getString(R.string.TeeInformation))
        .setMessage(info).setPositiveButton(android.R.string.ok, null)
        .setCancelable(false).create();
      alert.show();
  }
  
    static String getFingerprintTEEInfo(String path) {
    int len = 0;
    char[] buffer = new char[64];
    FileReader file = null; 
     
    try {
      file = new FileReader("/data/" + path);
      Log.i("SpecialCharSequenceMgr", "bbbb getFingerprintTEEInfo #file=" + file);
      len = file.read(buffer, 0, 64);
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
        Log.e("bbb", "getFingerprintTEEInfo fail");
      }
    }
    
    return new String(buffer);
  }//add joyar end
}

