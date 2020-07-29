package com.mediatek.factorymode.touchscreen;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.util.Log;



public class BGTouch extends Activity implements View.OnClickListener
{
	private boolean[] updatechannel = {false, false,false,false,false,false};
	 private static String TAG = "BGTouch";
	 private TextView channel1;
     private TextView channel2;
	 private TextView channel3;
	 private TextView channel4;
	 private TextView channel5;
	 private TextView channel6;
	 private Button mBtFailed;
	private  Button mBtOk;
	SharedPreferences mSp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.bgtouch);
		channel1 = (TextView) findViewById(R.id.idchannel1);
		channel2 = (TextView) findViewById(R.id.idchannel2);
		channel3 = (TextView) findViewById(R.id.idchannel3);
		channel4 = (TextView) findViewById(R.id.idchannel4);
		channel5 = (TextView) findViewById(R.id.idchannel5);
		channel6 = (TextView) findViewById(R.id.idchannel6);
		
		mSp = getSharedPreferences("FactoryMode", 0);
		mBtOk = (Button)findViewById(R.id.gbgtouch_bt_ok);
	    mBtOk.setOnClickListener(this);
	    mBtFailed = (Button)findViewById(R.id.gbgtouch_bt_failed);
	    mBtFailed.setOnClickListener(this);
	}

	public boolean onTouchEvent(MotionEvent event) {
       // Log.d(LOG_TAG, "onTouchEvent event = " + event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
            	update(event.getX(),event.getY());
                break;
        }
        return super.onTouchEvent(event);
    }

	public void update(float getX,float getY) {
		int x=(int)getX;
		int y=(int)getY;
		Log.e(TAG, "x="+x+" y="+y);
		int channelindex=0;
		if((x>0)&&(x<240)&&(y>0)&&(y<270))
		{
			channelindex=1;
		}else if((x>240)&&(x<720)&&(y>0)&&(y<270))
		{
			channelindex=2;
		}else if((x>0)&&(x<240)&&(y>270)&&(y<340))
		{
			channelindex=3;
		}else if((x>240)&&(x<720)&&(y>270)&&(y<340))
		{
			channelindex=4;
		}else if((x>0)&&(x<240)&&(y>300)&&(y<1280))
		{
			channelindex=5;
		}else if((x>240)&&(x<720)&&(y>300)&&(y<1280))
		{
			channelindex=6;
		}
		Log.e(TAG, "channelindex="+channelindex);
		if(channelindex>0)
		{
			updatechannel(channelindex);
		}
	}
	
	public void updatechannel(int channelindex) {
		Log.e(TAG, "updatechannel="+channelindex);
		if(updatechannel[channelindex-1])
		{
			return;
		}
		 switch (channelindex)
         {
             case 1:
            	 channel1.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             case 2:
            	 channel2.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             case 3:
            	 channel3.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             case 4:
            	 channel4.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             case 5:
            	 channel5.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             case 6:
            	 channel6.setTextColor(0xFFFF0000);
            	 updatechannel[channelindex-1]=true;
             break;
             default:
            	 break;
         }
	}
	public void onClick(View v) {
		// TODO Auto-generated method stub
	    if(v.getId() == mBtOk.getId()){
	        Utils.SetPreferences(this, mSp, R.string.bg_touch_name, "success");
	        finish();
	    }
	    else{
	        Utils.SetPreferences(this, mSp, R.string.bg_touch_name, "failed");
	        finish();
        }
	}



}
