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
public class VersionCheck extends Activity implements View.OnClickListener
{
private TextView tv_correct;
private Button mBtFailed;
private Button mBtOk;
private SharedPreferences mSp;
private String correctFlag;
private boolean isCheck = false;
   public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
   if(paramView.getId() == this.mBtOk.getId()&&isCheck)
    {
       Utils.SetPreferences(this, localSharedPreferences, R.string.correctinfo, "success");
       finish();
    }
    if (paramView.getId() == this.mBtFailed.getId())
    {
    	Utils.SetPreferences(this, localSharedPreferences, R.string.correctinfo, "failed");
    	 finish();
    }
   
  }
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.version_checkout);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.tv_correct= (TextView)findViewById(R.id.id_version_check);
	/*correctFlag= SystemProperties.get("gsm.serial");
	android.util.Log.d("CorrectInfoActivity", "correctFlagindex:" + correctFlag);
	if(correctFlag!= null && !correctFlag.equals("")&& correctFlag.length()>=61  ){
		correctFlag=correctFlag.substring(60,62);
		android.util.Log.d("CorrectInfoActivity", "correctFlagindex:=A" + correctFlag);
		if(correctFlag!=null && !correctFlag.equals("") && correctFlag.equals("10")){
			tv_correct.setText(R.string.correct);
			isCheck =true;
		}else{
			tv_correct.setText(R.string.uncorrect);
			isCheck=false;
		}
      }else{
	      tv_correct.setText(R.string.uncorrect);
		  isCheck=false;
    }*/
	String  version = android.os.SystemProperties.get("ro.build.display.id");
	if(version==null||version.equals("")){
		   isCheck=false;
	}else{
	    tv_correct.setText(version);
	    isCheck=true;
	}
    this.mBtOk = (Button)findViewById(R.id.version_check_bt_ok);
	 this.mBtOk.setEnabled(isCheck);
     this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.version_check_bt_failed);
    this.mBtFailed.setOnClickListener(this);
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
