package com.mediatek.factorymode.led;

import android.app.Activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.os.Process;
import android.view.WindowManager;
import android.widget.ToggleButton;
import android.graphics.drawable.Icon;


import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

public class Led extends Activity implements View.OnClickListener, Runnable
{
  private Button mBtFailed;
  private Button mBtOk;
  private SharedPreferences mSp;
  private NotificationManager nM;
  private Notification n;
  private int ledColor = 0x00ff0000;//start red;
  private boolean bExit = false;
  private Camera camera = null;

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.led_name, "success");
        bExit = true;
        
        Camera.Parameters param = camera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(param);
        camera.release();
        cancellLedShow();
        finish();
    }else{
        Utils.SetPreferences(this, localSharedPreferences, R.string.led_name, "failed");
        bExit = true;
        
        Camera.Parameters param = camera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(param);
        camera.release();
        cancellLedShow();
        finish();
        
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.led);
    try {
        camera = Camera.open();
        Camera.Parameters param = camera.getParameters();
        param.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(param);
    } catch (Exception e) {  
        finish();
        Toast.makeText(Led.this, this.getString(R.string.led_test_checkout_camera), Toast.LENGTH_SHORT).show();
    }
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.led_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.led_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    nM = (NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);
    //n = new Notification();
    n = new Notification.Builder(this)
            .setSmallIcon(R.drawable.stat_notify_led).build();
    n.flags |= Notification.FLAG_SHOW_LIGHTS;
    n.ledOnMS = 1000;
    n.ledOffMS = 0;
    //n.tickerText = "Led";
    new Thread(this).start();
    
  }
  
  public void onDestroy()
  {
    super.onDestroy();
    if (null != camera) {
        camera.release();
        camera = null;
    }
    bExit = true;
    cancellLedShow();
  }
  
  public void updateLedState(){
      n.ledARGB = 0xff000000|ledColor;
      Log.i("LED", "argb="+n.ledARGB);
      nM.notify(1, n);

      ledColor = ledColor>>8;
      if(ledColor == 0)ledColor = 0x00ff0000;
  }
  
  private void cancellLedShow(){
      nM.cancel(1);
  }

    public void run() {
        while(!Thread.currentThread().isInterrupted() && (!bExit)){
	    //change led
            updateLedState();
            //sleep 1 s
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            //cancell
            cancellLedShow();
            
        }
        
        
    }
}
