package com.mediatek.factorymode.version;

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
import android.view.View.OnClickListener;
import com.mediatek.factorymode.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import android.os.SystemProperties;
import android.os.Build;
public class Version extends Activity
implements View.OnClickListener
{
private TextView mInfo;
private TextView mBtFailed;
private TextView mBtOk;
private SharedPreferences mSp;
public void VersionTest()
 {
  StringBuilder localStringBuilder = new StringBuilder();
	String  date = SystemProperties.get("ro.build.date");
	String  model = SystemProperties.get("ro.product.model");
	String  version = SystemProperties.get("ro.build.display.id");
	String  modem = SystemProperties.get("gsm.version.baseband");
//	String  barcode = SystemProperties.get("gsm.serial");
	String  barcode =getSerialNumber();

    localStringBuilder.append(model).append("\n\n").append("modem version:\n").append(modem).append("\n").append("build time:\n").append(date).append("\n\n").append("sw version:\n").append(version).append("\n\n Barcode:\n").append(barcode);     //value  S005_HH_4+2_P4_XD_TV+WIFI_CN
    mInfo.setText(localStringBuilder.toString());
 }
 
    
    String getSerialNumber() {
        return Build.getSerial();
    }
 
 //wangshengyuan add 
   public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    //int i = 2131230850; 	
    int j = paramView.getId();
    int k = this.mBtOk.getId();
    int kk = this.mBtFailed.getId();
    if (j == k)
    {
       Utils.SetPreferences(this, localSharedPreferences, R.string.version_information, "success");
       finish();
    }
    if (j == kk)
    {
    	Utils.SetPreferences(this, localSharedPreferences, R.string.version_information, "failed");
    	 finish();
    }
   
  }
 //wangshengyuan add end
 
  public void onCreate(Bundle paramBundle)
  {
  super.onCreate(paramBundle);
   setContentView(R.layout.version);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
   this.mSp = localSharedPreferences;
   TextView localTextView = (TextView)findViewById(R.id.id_version);
   mInfo = localTextView;
   //wangshengyuan add 
   TextView localTextView1 = (TextView)findViewById(R.id.version_bt_ok);
    this.mBtOk = localTextView1;
    this.mBtOk.setOnClickListener(this);
    TextView localTextView2 = (TextView)findViewById(R.id.version_bt_failed);
    this.mBtFailed = localTextView2;
    this.mBtFailed.setOnClickListener(this);
  VersionTest();
  }
}
/*{




  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.version);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    TextView localTextView = (TextView)findViewById(R.id.id_version);


 // StringBuilder localStringBuilder = new StringBuilder();
//	String  value = SystemProperties.get("ro.build.date");
   //       localStringBuilder.append("S006-SSD-P4-FM-ZH").append(value);
    //    mInfo.setText(localStringBuilder.toString());

localTextView.setText(ro.custom.build.version);

  }
}*/
/*
package com.mediatek.factorymode.version;
import com.mediatek.factorymode.R;
import android.app.Activity;
import android.content.SharedPreferences;
 import android.os.Bundle;
import android.os.Environment;
 import android.view.View;
 import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import android.os.SystemProperties;
 public class version extends Activity {
private TextView mInfo;
private SharedPreferences mSp;
     public void VersionTest()
 {
  StringBuilder localStringBuilder = new StringBuilder();
	String  value = SystemProperties.get("ro.build.date");
          localStringBuilder.append("S005_HH_P2_TV+WIFI_EN_L2").append(value);
        mInfo.setText(localStringBuilder.toString());
 }
  public void onCreate(Bundle paramBundle)
  {
  super.onCreate(paramBundle);
   setContentView(R.layout.version);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
   this.mSp = localSharedPreferences;
   TextView localTextView = (TextView)findViewById(R.id.version_info);
   mInfo = localTextView;
  VersionTest();
  }
}
*/
