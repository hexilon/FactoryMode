package com.mediatek.factorymode.backlight;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.ShellExe;
import com.mediatek.factorymode.Utils;
import java.io.IOException;
import java.io.PrintStream;
import android.content.Intent;
import android.provider.Settings;
import android.content.ContentResolver;

public class BackLight extends Activity
  implements View.OnClickListener
{
  private final int ERR_ERR = 1;
  private final int ERR_OK = 0;
  private String lcdCmdOFF = "echo 50 > /sys/class/leds/lcd-backlight/brightness";
  private String lcdCmdON = "echo 250 > /sys/class/leds/lcd-backlight/brightness";
  private Button mBtFailed;
  private Button mBtOk;
  private Button mBtnLcdOFF;
  private Button mBtnLcdON;
  private TextView mLevel;
  private SharedPreferences mSp;
  private int currentBrightness = 102;
  private boolean mBtnLcdOFFClicked = false;
  private boolean mBtnLcdONClicked = false;
  static final String BACKLIGHT_STATE_CHANGE = "com.mediatek.factorymode.backlight.BACKLIGHT_STATE_CHANGE";
  static final String BACKLIGHT_LEVEl = "level";

  private void setLastError(int paramInt)
  {
    System.out.print(paramInt);
  }

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    String[] arrayOfString = new String[3];
    arrayOfString[0] = "/system/bin/sh";
    arrayOfString[1] = "-c";
      
    if(paramView.getId() == this.mBtnLcdON.getId() 
            || paramView.getId() == this.mBtnLcdOFF.getId()){

	Intent intent = new Intent(BACKLIGHT_STATE_CHANGE);
	if(paramView.getId() == this.mBtnLcdON.getId())
	{
		intent.putExtra(BACKLIGHT_LEVEl, "180");
      mLevel.setText("180");
      mBtnLcdONClicked = true;
	}else if(paramView.getId() == this.mBtnLcdOFF.getId()){
		intent.putExtra(BACKLIGHT_LEVEl, "80");
		 mLevel.setText("80");
     mBtnLcdOFFClicked = true;
	}
		sendBroadcast(intent);	

    mBtOk.setEnabled(mBtnLcdOFFClicked && mBtnLcdONClicked);
		
        /*
        if(paramView.getId() == this.mBtnLcdON.getId())
        {
            arrayOfString[2] = this.lcdCmdON;
        }else if(paramView.getId() == this.mBtnLcdOFF.getId()){
            arrayOfString[2] = this.lcdCmdOFF;
        }
        
        try {
            if(ShellExe.execCommand(arrayOfString) == 0)
                setLastError(0);
            else
                setLastError(1);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            setLastError(1);
        }
        */
    }

    if (paramView.getId() == this.mBtOk.getId())
    {
        Utils.SetPreferences(this, localSharedPreferences, R.string.backlight_name, "success");
        finish();
    }
    else if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.backlight_name, "failed");
        finish();
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.backlight);
    this.mBtnLcdON = (Button)findViewById(R.id.Display_lcd_on);
    this.mBtnLcdOFF =(Button)findViewById(R.id.Display_lcd_off);
    this.mBtOk = (Button)findViewById(R.id.display_bt_ok);
    this.mBtFailed = (Button)findViewById(R.id.display_bt_failed);
    this.mBtnLcdON.setOnClickListener(this);
    this.mBtnLcdOFF.setOnClickListener(this);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed.setOnClickListener(this);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
     mLevel=(TextView)findViewById(R.id.text_level);
    try {
	    currentBrightness = Settings.System.getInt( this.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
	    mLevel.setText(currentBrightness+"");
	    //mBtOk.setEnabled(currentBrightness>0);
      mBtOk.setEnabled(false);
	} catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
  }
}