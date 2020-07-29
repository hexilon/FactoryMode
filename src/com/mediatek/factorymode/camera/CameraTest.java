package com.mediatek.factorymode.camera;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.Size;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
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
import com.mediatek.factorymode.AllTest;
public class CameraTest extends Activity implements View.OnClickListener {
  private Button mBtFailed;
  private Button mBtFinish;
  private Button mBtOk;
  private Camera mCamera;
  SharedPreferences mSp;
  Camera.PictureCallback pictureCallback;
  private Camera.Parameters mCameraParam;
  SurfaceHolder.Callback surfaceCallback;
  private SurfaceHolder surfaceHolder;
  private SurfaceView surfaceView;
  private String mISO = "AUTO";
  private static final int RESULT_CODE = 1; 
private final int SYSTEM_CAMERA_REQUESTCODE = 1;
int face = 0 ;

  int canOpenMainCamera = 0;
  SharedPreferences spControl = null;

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
	
    requestWindowFeature(1);
	setContentView(R.layout.camera_main);
    getWindow().setFlags(1024, 1024);
	
	if (spControl == null) {
		spControl = getSharedPreferences(FactoryMode.CENTER_CONTROL, Context.MODE_PRIVATE);
	}
	canOpenMainCamera = spControl.getInt(FactoryMode.CENTER_CONTROL, 0);
	if (canOpenMainCamera == 1) {
		startSystemCamera();
	}
	spControl.edit().putInt(FactoryMode.CENTER_CONTROL, 0).commit();
	Log.i("bbbb", "MainCamera onCreate #canOpenMainCamera=" + canOpenMainCamera);
	//startSystemCamera();

	
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.vibrator_bt_ok);
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.vibrator_bt_failed);
    this.mBtFailed.setOnClickListener(this);
  }

  private void startSystemCamera() {
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra("android.intent.extras.CAMERA_FACING", face);
    intent.putExtra("CameraTest", true);
    startActivityForResult(intent, SYSTEM_CAMERA_REQUESTCODE);
  }

  @Override
  public void onResume() {
	super.onResume();
	Log.i("bbbb", "MainCamera onResume");
  }
  
  public void onClick(View paramView) {
	
	Log.i("bbbb", "MainCamera onClick");
	//setResult(AllTest.VersionIndex);
	
	  SharedPreferences localSharedPreferences = this.mSp;
	  if(paramView.getId() == this.mBtOk.getId()){
		  Utils.SetPreferences(this, localSharedPreferences, R.string.camera_name, "success");
		  finish();
	  }
	  else{
		  Utils.SetPreferences(this, localSharedPreferences, R.string.camera_name, "failed");
		  finish();
	  }
	}

     @Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data){  
        //super.onActivityResult(requestCode, resultCode, data); 
        int i = R.string.camera_name;
		
		Log.i("bbbb", "CameraTest onActivityResult #requestCode=" + requestCode
			+ " #resultCode=" + resultCode
			+ " #RESULT_OK=" + RESULT_OK);
		
        if(requestCode == SYSTEM_CAMERA_REQUESTCODE) {
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
