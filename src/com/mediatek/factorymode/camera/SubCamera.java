package com.mediatek.factorymode.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Display;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.FactoryMode;
import com.mediatek.factorymode.Utils;
import android.provider.MediaStore;
import android.util.Log;
import com.mediatek.factorymode.AllTest;
public class SubCamera extends Activity implements View.OnClickListener {
  Camera.CameraInfo cameraInfo;
  private Button mBtFailed;
  private Button mBtFinish;
  private Button mBtOk;
  private Camera mCamera;
  SharedPreferences mSp;
  String subcameraUri="com.mediatek.factorymode.camera.SubCamera";
  private static final int RESULT_CODE = 0; 
  private final int SYSTEM_CAMERA_REQUESTCODE = 2;
  int face = 1;
  int alreadyStarted = 0;
  SharedPreferences spControl = null;
  	
  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
	
    requestWindowFeature(1);
	
	setContentView(R.layout.camera_sub);
    getWindow().setFlags(1024, 1024);
	
	this.mSp = getSharedPreferences("FactoryMode", 0);

	if (spControl == null) {
		spControl = getSharedPreferences(FactoryMode.CENTER_CONTROL, Context.MODE_PRIVATE);
	}
	alreadyStarted = spControl.getInt(FactoryMode.CENTER_CONTROL, 0);
	if (alreadyStarted == 1) {
		startSystemCamera();
	}
	spControl.edit().putInt(FactoryMode.CENTER_CONTROL, 0).commit();//add joya, reopen camera control
	Log.i("bbbb", "MainCamera onCreate #alreadyStarted=" + alreadyStarted);
	
    this.mBtOk = (Button)findViewById(R.id.vibrator_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.vibrator_bt_failed);
    this.mBtFailed.setOnClickListener(this);

	
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
  }

  private void startSystemCamera() {	
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra("android.intent.extras.CAMERA_FACING", face);
    intent.putExtra("CameraTest", true);
    startActivityForResult(intent, SYSTEM_CAMERA_REQUESTCODE);
	Log.i("bbbb", "SubCamera startActivityForResult #SYSTEM_CAMERA_REQUESTCODE=" + SYSTEM_CAMERA_REQUESTCODE);
  }


  @Override
  public void onResume() {
	super.onResume();
  }

	public void onClick(View paramView) {
	
    SharedPreferences localSharedPreferences = this.mSp;
		
	//setResult(AllTest.SimCardIndex);
    if(paramView.getId() == this.mBtOk.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.subcamera_name, "success");
        finish();
    }
    else{
        Utils.SetPreferences(this, localSharedPreferences, R.string.subcamera_name, "failed");
        finish();
    }
  }
     @Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data){  
        super.onActivityResult(requestCode, resultCode, data); 
        int i = R.string.subcamera_name;
		Log.i("bbbb", "SubCamera onActivityResult #requestCode=" + requestCode
			+ " #resultCode=" + resultCode);
		
        if(requestCode == SYSTEM_CAMERA_REQUESTCODE && resultCode == RESULT_OK) {
	        Context localContext1 = getApplicationContext();
	        SharedPreferences localSharedPreferences1 = this.mSp;
	        Utils.SetPreferences(localContext1, localSharedPreferences1, i, "success");
        } else {
			Context localContext2 = getApplicationContext();
			SharedPreferences localSharedPreferences2 = this.mSp;
			Utils.SetPreferences(localContext2, localSharedPreferences2, i, "failed");
        }
    }
  
 
}
