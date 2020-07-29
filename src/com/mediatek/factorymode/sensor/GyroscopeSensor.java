package com.mediatek.factorymode.sensor;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import android.util.Log;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ComponentName;
import android.util.Log;
//import com.mediatek.common.featureoption.FeatureOption;

public class GyroscopeSensor extends Activity implements View.OnClickListener
{
	private static final int OFFSET = 2;
	private ImageView ivimg;
	private SensorManager mManager = null;
	private Sensor mSensor = null;
	private SensorEventListener mListener = null;
	SharedPreferences mSp;
	private Button mBtFailed;
	private Button mBtOk;
	
	private TextView xCoord;
	private TextView yCoord;
	private TextView zCoord;
	
	private TextView mResultX;
	private TextView mResultY;
	private TextView mResultZ;
	
	//Resources res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gyroscope_sensor);
		
		
		xCoord = (TextView) findViewById(R.id.gyr_x);
		yCoord = (TextView) findViewById(R.id.gyr_y);
		zCoord = (TextView) findViewById(R.id.gyr_z);
		
		mResultX = (TextView) findViewById(R.id.gyroscope_result_x);
		mResultY = (TextView) findViewById(R.id.gyroscope_result_y);
		mResultZ = (TextView) findViewById(R.id.gyroscope_result_z);
		
	    mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	    mSensor = mManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
	    mListener = new SensorEventListener(){

			public void onAccuracyChanged(Sensor sensor, int accuracy) {
				// TODO Auto-generated method stub
				
			}

			public void onSensorChanged(SensorEvent event) {
				// TODO Auto-generated method stub
				float x = event.values[SensorManager.DATA_X];
				float y = event.values[SensorManager.DATA_Y];
				float z = event.values[SensorManager.DATA_Z];
				
				
				Log.i("beijinyan", "x v alue = "+x);
				Log.i("beijinyan", "y value = "+y);
				Log.i("beijinyan", "z value = "+z);
				
				StringBuilder sbx = new StringBuilder();
				StringBuilder sby = new StringBuilder();
				StringBuilder sbz = new StringBuilder();
				
				
				sbx.append(getString(R.string.x_coordinate)).append(String.format("%7.4f",x));
				xCoord.setText(sbx);
				
				sby.append(getString(R.string.y_coordinate)).append(String.format("%7.4f",y));
				yCoord.setText(sby);
				
				sbz.append(getString(R.string.z_coordinate)).append(String.format("%7.4f",z));
				zCoord.setText(sbz);
				
				if ((double)x >= 3) {
					mResultX.setText(R.string.x_result_pass);
				} 
				
				if ((double)y >= 3) {
					mResultY.setText(R.string.y_result_pass);
				} 
				
				if ((double)z >= 3) {
					mResultZ.setText(R.string.z_result_pass);
				} 
			}
	    };
	    
	    mSp = getSharedPreferences("FactoryMode", 0);
	   // ivimg = (ImageView)findViewById(R.id.gsensor_iv_img);
	    mBtOk = (Button)findViewById(R.id.gsensor_bt_ok);
	    mBtOk.setOnClickListener(this);
	    mBtFailed = (Button)findViewById(R.id.gsensor_bt_failed);
	    mBtFailed.setOnClickListener(this);
	}


	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		mManager.unregisterListener(mListener);
		super.onPause();
	}



	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		mManager.registerListener(mListener, mSensor, SensorManager.SENSOR_DELAY_GAME);
		super.onResume();
	}



	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	    if(arg0.getId() == mBtOk.getId()){
	        Utils.SetPreferences(this, mSp, R.string.gyroscope, "success");
	        finish();
	    }
	    else{
	        Utils.SetPreferences(this, mSp, R.string.gyroscope, "failed");
	        //finish();
	        if(false)
	        {
    	        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
                localBuilder.setTitle(R.string.FMRadio_notice);
                localBuilder.setMessage(R.string.gsensor_cali_notice);
                localBuilder.setPositiveButton("YES", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent();
                         intent.setComponent(new ComponentName("com.joyatel.gsensorcalibrate",
                        "com.joyatel.gsensorcalibrate.GSensorCaliActivity"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
                localBuilder.setNegativeButton("NO", new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                });
                localBuilder.create().show();
            }
            else
            {
                finish();
            }
        }
	}


}
