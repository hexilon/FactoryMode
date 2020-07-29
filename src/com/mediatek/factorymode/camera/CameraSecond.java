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
import android.widget.TextView;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import android.provider.MediaStore;
public class CameraSecond extends Activity implements View.OnClickListener {
    private Button mBtFailed;
    private Button mBtFinish;
    private Button mBtOk;
    private TextView secondTextView;
    SharedPreferences mSp;
    private static final int RESULT_CODE = 1;
    private final int SYSTEM_CAMERA_REQUESTCODE = 1;
    int face = 0 ;

    public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
        setContentView(R.layout.camera_mainsecond);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra("CameraSecond",true);    //MMI modify  2017119
        intent.putExtra("android.intent.extras.CAMERA_FACING", face);
        startActivityForResult(intent, SYSTEM_CAMERA_REQUESTCODE);
        this.mSp = getSharedPreferences("FactoryMode", 0);
        this.mBtOk = (Button)findViewById(R.id.second_bt_ok);
        this.mBtOk.setOnClickListener(this);
        this.mBtFailed = (Button)findViewById(R.id.second_bt_failed);
        this.mBtFailed.setOnClickListener(this);
        this.secondTextView = (TextView) findViewById(R.id.txt_value_second);
        this.secondTextView.setText(R.string.second_maincamera_test);
        //Intent intent = new Intent();
        //intent.setClassName("com.mediatek.camera", "com.android.camera.CameraActivity");
        //intent.putExtra("SecondCamera",true);
        //intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //startActivityForResult(intent, SYSTEM_CAMERA_REQUESTCODE);
    }


@Override
  protected void onResume() {
    super.onResume();

   String str1 = getResources().getString(R.string.second_maincamera_test);
   String str2 = secondTextView.getText().toString();

     //if (str1.equals(str2)) {
     if ((str1.equals(str2)) || ("Second MainCamera Test Failed".equals(str2))){
    	  mBtOk.setEnabled(false);
    	}
    else
    	{
    	  mBtOk.setEnabled(true);
    	}
  }

    public void onClick(View paramView) {
        SharedPreferences localSharedPreferences = this.mSp;
        if(paramView.getId() == this.mBtOk.getId()) {
            Utils.SetPreferences(this, localSharedPreferences, R.string.second_maincamera, "success");
            finish();
        } else {
            Utils.SetPreferences(this, localSharedPreferences, R.string.second_maincamera, "failed");
            finish();
        }
    }

    @Override  
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SYSTEM_CAMERA_REQUESTCODE && resultCode == RESULT_OK){
            //android.util.Log.i("MMI",data.getStringExtra("SecondMainResult"));
            if(data!=null && data.getStringExtra("SecondMainResult")!=null)	{
                CameraSecond.this.secondTextView.setText(data.getStringExtra("SecondMainResult"));
            }
        }
    }
}
