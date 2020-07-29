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

public class GSensor extends Activity implements View.OnClickListener
{
	private static final int OFFSET = 2;
	private ImageView ivimg,ivimg0,ivimg1,ivimg2,ivimg3,ivimg4;
	private SensorManager mManager = null;
	private Sensor mSensor = null;
	private SensorEventListener mListener = null;
	SharedPreferences mSp;
	private Button mBtFailed;
	private Button mBtOk;
	
	private TextView xCoord;
	private TextView yCoord;
	private TextView zCoord;
	private boolean isivimg1,isivimg2,isivimg3,isivimg4;
	//Resources res;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gsensor);
		
		
		xCoord = (TextView) findViewById(R.id.xcoor);
		yCoord = (TextView) findViewById(R.id.ycoor);
		zCoord = (TextView) findViewById(R.id.zcoor);
		
	    mManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
	    mSensor = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
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
				
				
				sbx.append(getString(R.string.x_coordinate)).append(String.format("%1.4f",x));
				xCoord.setText(sbx);
				
				sby.append(getString(R.string.y_coordinate)).append(String.format("%1.4f",y));
				yCoord.setText(sby);
				
				sbz.append(getString(R.string.z_coordinate)).append(String.format("%1.4f",z));
				zCoord.setText(sbz);
				
				
		//		xCoord.setText(this.getString(R.string.x_coordinate)+String.format("%1.4f",x));
		//		yCoord.setText(this.getString(R.string.y_coordinate)+String.format("%1.4f",y));
		//		zCoord.setText(this.getString(R.string.z_coordinate)+String.format("%1.4f",z));
				
				//Log.i("Gsensor", "x = " + x + "y = " + y + "z = " + z);
/* bei ++
				if(x > -4 && x < 4 && y < 4 && z > 7){
            				GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_z);
        			}
        			else if(x > -4 && x < 4 && y > 4 && z < 7){
            				GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_y);
				}
				else if (x < -4 && y > -1 && y < 4 && z < 7){
				    GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x_2);
				}
				else if(x > 4 && y > -1 && y < 4 && z < 7){
				    GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x);  
				}
bei -- */
				
		   if(x > -2 && x < 2 && y < 2 && y > -2  && z < 11){
			   
			 
    			GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_z);
    			
			}
		 
        			else if(x > -4 && x < 4 && y > 4 && z < 7){
				
    				GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_y);
    				ivimg4.setVisibility(View.VISIBLE);
    				isivimg4=true;
		    }
				else if (x < -4 && y > -4 && y < 4 && z < 7){
			   
		  GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x_2);
		  ivimg3.setVisibility(View.VISIBLE);
		  isivimg3= true;
		    }
				else if(x > 4 && y > -4 && y < 4 && z < 7){
			   
		      GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_x);
		      ivimg2.setVisibility(View.VISIBLE);
		      isivimg2=true;  
		    }	
		 
          else if(x > -4 && x < 4 && y < 0 && z < 8)
        	{
			   
			   GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_h);
			   ivimg1.setVisibility(View.VISIBLE);
			   isivimg1=true;
			   
		   }
		   /*
          else if( y < -1 && z > 9  ){
			   
			   GSensor.this.ivimg.setBackgroundResource(R.drawable.gsensor_z);
			   
		   }
	*/
	   if(isivimg1&&isivimg2&&isivimg3&&isivimg4){
	   	 mBtOk.setEnabled(true);
	   	}
			}
	    };
	    
	    mSp = getSharedPreferences("FactoryMode", 0);
	    ivimg = (ImageView)findViewById(R.id.gsensor_iv_img);
	    ivimg1 = (ImageView)findViewById(R.id.gsensor_iv_img1);
	    ivimg2 = (ImageView)findViewById(R.id.gsensor_iv_img2);
	    ivimg3 = (ImageView)findViewById(R.id.gsensor_iv_img3);
	    ivimg4 = (ImageView)findViewById(R.id.gsensor_iv_img4);
	    mBtOk = (Button)findViewById(R.id.gsensor_bt_ok);
	    mBtOk.setOnClickListener(this);
	    mBtOk.setEnabled(false);
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
	        Utils.SetPreferences(this, mSp, R.string.gsensor_name, "success");
	        finish();
	    }
	    else  if(arg0.getId() == mBtFailed.getId()){
	        Utils.SetPreferences(this, mSp, R.string.gsensor_name, "failed");
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
