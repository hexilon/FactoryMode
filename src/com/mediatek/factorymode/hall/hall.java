//Demand:MMI modify(start) add hall test in factorymode by zmj 2017/08/29
package com.mediatek.factorymode.hall;

import android.app.Activity;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;


import android.widget.TextView;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.NumberFormatException;

public class hall extends Activity implements View.OnClickListener
 {
  private Button mBtFailed;
  private Button mBtOk;
  SharedPreferences mSp;
  private boolean isHallOpen =false;
  private static final String TAG = "hall";
  private LinearLayout layout;
    /**
     * Fingerprint state: During cancelling we got another request to start listening, so when we
     * receive the cancellation done signal, we should start listening again.
     */
    private TextView hallTextView;


  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId()&&isHallOpen){
        Utils.SetPreferences(this, localSharedPreferences, R.string.hall, "success");
        finish();
    }else if(paramView.getId() == this.mBtFailed.getId()){
        Utils.SetPreferences(this, localSharedPreferences, R.string.hall, "failed");
        finish();
    }
  }

  @Override
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.hall);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mBtOk = (Button)findViewById(R.id.hall_bt_ok);
    this.mBtOk.setOnClickListener(this);
	mBtOk.setEnabled(false);
    this.mBtFailed = (Button)findViewById(R.id.hall_bt_failed);
    this.mBtFailed.setOnClickListener(this);
    hallTextView = (TextView) findViewById(R.id.txt_value_hall);
    hallTextView.setText(R.string.hall_toast);
	layout=(LinearLayout)findViewById(R.id.txt_layout);
  }
   private Handler mHandler = new Handler();
   private Runnable myRunnable= new Runnable() {    
	        public void run() { 
	            if(getCurrent()==0){
				  hallTextView.setText(R.string.hall_is_open);
                  isHallOpen = true;
                 if(layout!=null){
				   layout.setBackgroundColor(getResources().getColor(R.color.Blue));
				 }				  
				}
				mBtOk.setEnabled(isHallOpen);
	        	mHandler.postDelayed(this, 100); 
	            
	        }  
	}; 
  @Override
	protected void onResume() {
		super.onResume();
		mHandler.post(myRunnable);
	}
  
  @Override
	protected void onPause() {
		super.onPause();
	}
	
	@Override
    public void onStop() {
        super.onStop();
    }

	@Override
	public void onDestroy(){
		super.onDestroy();
	}
	  private int getCurrent() {
        String hallState = null;
		int hallOpen  = 1;
		try {
			hallState = readCurrentFile(new File("/sys/class/switch/hall/state"));
			//android.util.Log.i("zmj","hallState="+hallState);
			hallOpen =  Integer.parseInt(hallState);
			//android.util.Log.i("zmj","hallOpen="+hallOpen);
		} catch (IOException e) {
			e.printStackTrace();
		}catch (NumberFormatException e1) {
            e1.printStackTrace();
        }
		
        return hallOpen;
    }
	
	public String readCurrentFile(File file) throws IOException {
        InputStream input = new FileInputStream(file);
       try {
           BufferedReader reader = new BufferedReader(new InputStreamReader(input));
           StringBuilder sb = new StringBuilder();
           String line = null;
           while ((line = reader.readLine()) != null) {
               sb.append(line);
           }
           return sb.toString();
       } finally {
           input.close();
       }
 }
}
//Demand:MMI modify(start) add hall test in factorymode by zmj 2017/08/29