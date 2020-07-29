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
 import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.Utils;
import android.os.SystemProperties;

  

public class VersionPa extends Activity {
private TextView mInfo;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.version);

       StringBuilder localStringBuilder = new StringBuilder();
	String  date = SystemProperties.get("ro.build.date");
	String  model = SystemProperties.get("ro.product.model");
	String  version = SystemProperties.get("ro.custom.sw.version");
   TextView localTextView = (TextView)findViewById(R.id.id_version);
   mInfo = localTextView;
    localStringBuilder.append(model).append("\n\n").append("build time:\n").append(date).append("\n").append("sw version:\n").append(version);     //value  S005_HH_4+2_P4_XD_TV+WIFI_CN
    mInfo.setText(localStringBuilder.toString());


    }




}

