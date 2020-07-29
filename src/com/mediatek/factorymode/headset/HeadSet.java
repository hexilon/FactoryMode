package com.mediatek.factorymode.headset;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;
import android.util.Log;//add joyar

import android.view.KeyEvent;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.mediatek.factorymode.VUMeter;
import java.io.File;
import java.io.IOException;
import android.widget.TextView;
public class HeadSet extends Activity
  implements View.OnClickListener
{
  private static final int STATE_HEADSET_PLUG = 0;
  private static final int STATE_HEADSET_UNPLUG = 1;
  Handler mHandler;
  private Button mBtFailed;
  private Button mBtOk;
  boolean mMicClick = false;
  private MediaPlayer mPlayer = null;
  BroadcastReceiver mReceiver;
  private Button mRecord;
  private MediaRecorder mRecorder = null;
  SharedPreferences mSp;
  boolean mSpkClick = false;
  VUMeter mVUMeter;
  Handler myHandler;
  Runnable mRunnable;
  private String recordpath;
  private boolean isHeadSet =false;
   private TextView mHeadSet;
   private TextView mHeadsetHookTextView;
  class HeadSet1 implements Runnable
  {
  public void run()
  {
	  if(mVUMeter!=null){
	      mVUMeter.invalidate();
	  }
     mHandler.postDelayed(this, 100L);
  }
  }

  class HeadSet2 extends Handler
  {
    public void handleMessage(Message paramMessage)
    {
    	  MediaRecorder localMediaRecorder = null;
      boolean bool = true;
      super.handleMessage(paramMessage);

   switch (paramMessage.what)
      {
      //default:
      case 0:
    	  mRecord.setText(R.string.HeadSet_tips);
          mRecord.setEnabled(false);
          break;
      case 1:
    	  String str1 = Environment.getExternalStorageState();
          String str2 = "mounted";
          if(str1.equals(str2))
    	  {
        	  mRecord.setText(R.string.Mic_start);
              mRecord.setEnabled(true);

    	  }
    	  else
    	  {
    		  mRecord.setText(R.string.sdcard_tips_failed);
    		  mRecord.setEnabled(false);
    	  }

    	  break;
      }

    }
  }
  class HeadSet3 extends BroadcastReceiver
  {
    public void onReceive(Context paramContext, Intent paramIntent)
    {
      String str = paramIntent.getAction();
      if ("android.intent.action.HEADSET_PLUG".equals(str))
      {
	  
        if (paramIntent.getIntExtra("state", 0) == 1)
        {
		  isHeadSet = true;
		  mHeadSet.setText(getResources().getString(R.string.HeadSet_plugged));
          myHandler.sendEmptyMessage(1);
        }
        else {
		    isHeadSet = false;
			mHeadSet.setText(getResources().getString(R.string.HeadSet_unplugged));
        	myHandler.sendEmptyMessage(0);
		 }
      }


    }
  }

  public HeadSet()
  {
    Handler localHandler = new Handler();
    this.mHandler = localHandler;
    HeadSet1 local1 = new HeadSet1();
    this.mRunnable = local1;
    HeadSet2 local2 = new HeadSet2();
    this.myHandler = local2;
    HeadSet3 local3 = new HeadSet3();
    this.mReceiver = local3;
  }

  private void start()
  {
    Handler localHandler = this.mHandler;
    Runnable localRunnable = this.mRunnable;
    localHandler.post(localRunnable);
    
    this.mHandler.post(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			if (HeadSet.this.mPlayer != null)
				HeadSet.this.mPlayer.stop();
		}
	});
    if (!Environment.getExternalStorageState().equals("mounted"))
      this.mRecord.setText(2131230866);

      try
      {
        MediaRecorder localMediaRecorder1 = new MediaRecorder();
        this.mRecorder = localMediaRecorder1;
        this.mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        this.mVUMeter.setRecorder(this.mRecorder);

        if (!new File(recordpath).exists())
          new File(recordpath).createNewFile();
        this.mRecorder.setOutputFile(recordpath);
        this.mRecorder.prepare();
        this.mRecorder.start();
        this.mRecord.setTag("ing");
        this.mRecord.setText(R.string.Mic_stop);

      }
      catch (Exception localException)
      {
        String str3 = localException.getMessage();
        Toast.makeText(this, str3, 0);
        //break;
      }

  }

  private void stopAndSave()
  {
    Handler localHandler = this.mHandler;
    Runnable localRunnable = this.mRunnable;
    localHandler.removeCallbacks(localRunnable);
    this.mRecord.setText(R.string.Mic_start);
    this.mRecord.setTag("");
    this.mVUMeter.SetCurrentAngle(0);
   
    
    this.mHandler.post(new Runnable() {
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			 HeadSet.this.mRecorder.stop();
			 HeadSet.this.mRecorder.release();
			 HeadSet.this.mRecorder = null;
		}
	});
    try
    {
      MediaPlayer localMediaPlayer = new MediaPlayer();
      this.mPlayer = localMediaPlayer;
      this.mPlayer.setAudioStreamType(3);
      this.mPlayer.setDataSource(recordpath);
      this.mPlayer.prepare();
      this.mPlayer.start();
      return;
    }
    catch (IllegalArgumentException localIllegalArgumentException)
    {
      localIllegalArgumentException.printStackTrace();
    }
    catch (IllegalStateException localIllegalStateException)
    {
      localIllegalStateException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      localIOException.printStackTrace();
    }
  }

  public void onClick(View paramView)
  {
    int i = R.string.headset_name;
    int j = paramView.getId();
    int k = this.mRecord.getId();
    if (j == k)
    {
      if ((this.mRecord.getTag() != null) && (this.mRecord.getTag().equals("ing"))) {
    	  VUMeter.canExit = true;//add joyar
    	  stopAndSave();
	  }
      else {
    	  VUMeter.canExit = false;//add joyar
          start();
	  }
    }
    int l = paramView.getId();
    int i1 = this.mBtOk.getId();
    if (l == i1)
    {
      SharedPreferences localSharedPreferences1 = this.mSp;
      Utils.SetPreferences(this, localSharedPreferences1, i, "success");
      finish();
    }
    int i2 = paramView.getId();
    int i3 = this.mBtFailed.getId();
    if (i2 == i3)
    {
        SharedPreferences localSharedPreferences1 = this.mSp;
        Utils.SetPreferences(this, localSharedPreferences1, i, "failed");
        finish();
      }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.headset);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    Button localButton1 = (Button)findViewById(R.id.mic_bt_start);
    this.mRecord = localButton1;
    this.mRecord.setOnClickListener(this);
    this.mRecord.setEnabled(false);
    Button localButton2 = (Button)findViewById(R.id.bt_ok);
    this.mBtOk = localButton2;
	 this.mBtOk.setOnClickListener(this);
	 mBtOk.setEnabled(isHeadSet);
    Button localButton3 = (Button)findViewById(R.id.bt_failed);
    this.mBtFailed = localButton3;
    this.mBtFailed.setOnClickListener(this);
	this.mHeadSet=(TextView)findViewById(R.id.ear_headset);
    this.mHeadsetHookTextView=(TextView)findViewById(R.id.tv_headset_hook);
    VUMeter localVUMeter = (VUMeter)findViewById(R.id.uvMeter);
    this.mVUMeter = localVUMeter;
    IntentFilter localIntentFilter = new IntentFilter("android.intent.action.HEADSET_PLUG");
    localIntentFilter.setPriority(999);
    BroadcastReceiver localBroadcastReceiver = this.mReceiver;
    registerReceiver(localBroadcastReceiver, localIntentFilter);

    StringBuilder localStringBuilder = new StringBuilder();
    String str = null;
    File localFile = Environment.getExternalStorageDirectory();
    localStringBuilder.append(localFile).append(File.separator).append("test.amr");
    str = localStringBuilder.toString();
    recordpath = str;

  }
  
  public boolean onKeyDown(int keyCode, KeyEvent event)
  {
	if (KeyEvent.KEYCODE_HEADSETHOOK == keyCode) {
        mHeadsetHookTextView.setText(getResources().getString(R.string.HeadSet_hook_test_done));
        if(mBtOk!=null){
		    mBtOk.setEnabled(isHeadSet);
		}
    }
    return super.onKeyDown(keyCode, event);
  }

  @Override
  protected void onResume() {//add joyar start
	  super.onResume();
	  Log.i("bbbb", "HeadSet onResume");
	  VUMeter.canExit = false;
  }
  
  @Override
  protected void onStop() {
	  super.onStop();
	  Log.i("bbbb", "HeadSet onStop");
	  VUMeter.canExit = true;
  }//add joyar end
  
  protected void onDestroy()
  {
    super.onDestroy();
    new File(recordpath).delete();
    if (this.mPlayer != null)
      this.mPlayer.stop();
    if (this.mRecorder != null)
      this.mRecorder.stop();
    BroadcastReceiver localBroadcastReceiver = this.mReceiver;
    unregisterReceiver(localBroadcastReceiver);
    Handler localHandler = this.mHandler;
    Runnable localRunnable = this.mRunnable;
    localHandler.removeCallbacks(localRunnable);
  }
}