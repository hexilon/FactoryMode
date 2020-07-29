package com.mediatek.factorymode.agingtest;

import android.app.Activity;
import android.content.ContextWrapper;
import android.content.Intent;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.view.View;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import com.mediatek.factorymode.R;
import android.util.Log;
import android.media.AudioSystem;

public class AgingTestMain extends Activity
{
  private final int CHANGE_COLOR = 0;
  private final int RECORD_START = 1;
  private final int RECORD_STOP = 2;
 	private static String TAG = "YAO";
  private AudioManager mAudioManager;
  private Camera mCamera = null;
  private int mNum = 0;
  private Camera.Parameters mParams;
  private MediaPlayer mPlayer = null;
  private MediaRecorder mRecorder;
  private TextView mTesting = null;
  private Vibrator mVibrator = null;
  private String recordpath;
  Handler myHandler = new Handler()
  {
    public void handleMessage(Message paramAnonymousMessage)
    {
      switch (paramAnonymousMessage.what)
      {
      
      case 0:
        //AgingTestMain.access$108(AgingTestMain.this);
        AgingTestMain.this.changeColor(AgingTestMain.this.mNum);
        mNum++;
        if(mNum == 4)
        mNum = 0; 
       
        return;
      case 1:
      Log.i(TAG,"yao--->here-->case 1!");
        AgingTestMain.this.startRecord();
        sendEmptyMessageDelayed(2, 6000L);
        return;
      case 2:
       Log.i(TAG,"yao--->here-->case 2!");
      AgingTestMain.this.stopAndDelete();
      sendEmptyMessageDelayed(1, 6000L);
      default:
        return;
      }
    }
  };
  private long[] pattern = { 0L, 1000L };
  private Timer timer = null;

  private void changeColor(int paramInt)
  {
  	Log.i(TAG,"yao-->changeColor(int paramInt)-->paramInt==? " + paramInt);
    switch (paramInt % 4)
    {
    case 0:
      this.mTesting.setBackgroundColor(-65536);
      return;
    case 1:
      this.mTesting.setBackgroundColor(-16711936);
      return;
    case 2:
      this.mTesting.setBackgroundColor(-16776961);
      return;
    case 3:
    	this.mTesting.setBackgroundColor(-1);
    	return;
    default:
      return;
      }
  }

/*
  private void initMediaPlayer()
  {
    this.mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tada);
    this.mPlayer.setLooping(true);
    try
    {
      this.mPlayer.prepare();
      this.mPlayer.start();
      return;
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        localIllegalStateException.printStackTrace();
    }
    catch (IOException localIOException)
    {
      while (true)
        localIOException.printStackTrace();
    }
  }
*/

	 private void initMediaPlayer()
  {
    MediaPlayer localMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tada);
    this.mPlayer = localMediaPlayer;
    this.mPlayer.setLooping(true);
    this.mPlayer.start();
  }

  private void initView()
  {
    this.timer.schedule(new TimerTask()
    {
      public void run()
      {
        Message localMessage = new Message();
        localMessage.what = 0;
        AgingTestMain.this.myHandler.sendMessage(localMessage);
      }
    }
    , 1000L, 1000L);
  }

  private void startRecord()
  {
  	Log.i(TAG,"yao--->here-->startRecord!");
    try
    {
    		this.mRecorder = new MediaRecorder();
        this.mRecorder.setAudioSource(1);
        this.mRecorder.setOutputFormat(1);
        this.mRecorder.setAudioEncoder(3);
        //this.mVUMeter.setRecorder(this.mRecorder);
        /*
        StringBuilder localStringBuilder = new StringBuilder();
        String str = null;
        File localFile = Environment.getExternalStorageDirectory();
        localStringBuilder.append(localFile).append(File.separator).append("test.mp3");
        str = localStringBuilder.toString();
        if (!new File(str).exists())
          new File(str).createNewFile();
        this.mRecorder.setOutputFile(str);
        */
        
        if (!new File(recordpath).exists())
          new File(recordpath).createNewFile();
        this.mRecorder.setOutputFile(recordpath);
        
        this.mRecorder.prepare();
        this.mRecorder.start();
    	/*
      this.mRecorder = new MediaRecorder();
      this.mRecorder.setAudioSource(1);
      this.mRecorder.setOutputFormat(1);
      this.mRecorder.setAudioEncoder(3);
      String str = Environment.getExternalStorageDirectory() + File.separator + "test.mp3";
      if (!new File(str).exists())
        new File(str).createNewFile();
      this.mRecorder.setOutputFile(str);
      this.mRecorder.prepare();
      this.mRecorder.start();
      */
      return;
    }
    catch (Exception localException)
    {
      Toast.makeText(this, localException.getMessage(), 0);
    }
  }

  private void stopAndDelete()
  {/*
    this.mRecorder.stop();
    this.mRecorder.release();
    this.mRecorder = null;
    new File("/sdcard/test.mp3").delete();
    */
    //this.h.removeCallbacks(this.ra);
    this.mRecorder.stop();
    this.mRecorder.release();
    this.mRecorder = null;
    try
    {
      MediaPlayer localMediaPlayer = new MediaPlayer();
      this.mPlayer = localMediaPlayer;
      //this.mPlayer.setDataSource("/sdcard/test.mp3");
       this.mPlayer.setDataSource(recordpath);
      this.mPlayer.prepare();
      this.mPlayer.start();
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

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.agingtest_main);
    this.mTesting = ((TextView)findViewById(R.id.testing));
    //add begin
    StringBuilder localStringBuilder = new StringBuilder();
    String str = null;
    File localFile = Environment.getExternalStorageDirectory();
    localStringBuilder.append(localFile).append(File.separator).append("test.mp3");
    str = localStringBuilder.toString();
    recordpath = str;
    //add end
    Intent localIntent = getIntent();
    boolean bool1 = localIntent.getBooleanExtra("lcd", false);
    boolean bool2 = localIntent.getBooleanExtra("speaker", false);
    boolean bool3 = localIntent.getBooleanExtra("vibrator", false);
    boolean bool4 = localIntent.getBooleanExtra("mic", false);
    boolean bool5 = localIntent.getBooleanExtra("earphone", false);
    boolean bool6 = localIntent.getBooleanExtra("flashlight", false);
    if ((bool1) || (bool3)){
    	getWindow().addFlags(128);
    }
    if (bool1)
    {
      this.timer = new Timer();
      initView();
    }
    if (bool3)
    {
      	this.mVibrator = ((Vibrator)getSystemService("vibrator"));
      this.mVibrator.vibrate(this.pattern, 0);
    }
    if (bool4)
    {
    	this.myHandler.sendEmptyMessageDelayed(1, 1000L);
    }
    if (bool6)
    {
    	if (this.mCamera == null){
          this.mCamera = Camera.open();
          this.mParams = this.mCamera.getParameters();
          this.mParams.setFlashMode("torch");
          this.mCamera.setParameters(this.mParams);
        }
    }
    if ((bool2)&&(!bool5))
    {
    	this.mAudioManager = ((AudioManager)getSystemService("audio"));
      this.mAudioManager.setRingerMode(2);
      this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 4);
        initMediaPlayer();
    }
    if ((!bool2)&&(bool5))
    {
    	this.mAudioManager = ((AudioManager)getSystemService("audio"));
      this.mAudioManager.setRingerMode(2);
      this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 4);
      this.mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
    			this.mAudioManager.setSpeakerphoneOn(false);
    
    			int i = this.mAudioManager.getStreamMaxVolume(AudioSystem.STREAM_RING);
    			this.mAudioManager.setStreamVolume(AudioSystem.STREAM_RING, i, AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_ALLOW_RINGER_MODES);
    			initMediaPlayer();
    	
    }
    if ((bool2)&&(bool5))
    {
    	this.mAudioManager = ((AudioManager)getSystemService("audio"));
      this.mAudioManager.setRingerMode(2);
      this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 4);
      this.mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
    			this.mAudioManager.setSpeakerphoneOn(true);
    
    			int i = this.mAudioManager.getStreamMaxVolume(AudioSystem.STREAM_RING);
    			this.mAudioManager.setStreamVolume(AudioSystem.STREAM_RING, i, AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_ALLOW_RINGER_MODES);
    			initMediaPlayer();
    	
    }
    
    
    
    /*
    if (bool1)
    {
      this.timer = new Timer();
      initView();
    }else if(bool3){
    	this.mVibrator = ((Vibrator)getSystemService("vibrator"));
      this.mVibrator.vibrate(this.pattern, 0);
    }else if(bool4){
    	this.myHandler.sendEmptyMessageDelayed(1, 1000L);
    }else{
    	this.mAudioManager = ((AudioManager)getSystemService("audio"));
      this.mAudioManager.setRingerMode(2);
      this.mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 4);
      if (bool2){
        initMediaPlayer();
      }
      while (bool6){
      	if (this.mCamera == null){
          this.mCamera = Camera.open();
          this.mParams = this.mCamera.getParameters();
          this.mParams.setFlashMode("torch");
          this.mCamera.setParameters(this.mParams);
          return;
        }
       }
       if (bool5){
    			this.mAudioManager.setMode(AudioManager.MODE_IN_COMMUNICATION);
    			this.mAudioManager.setSpeakerphoneOn(false);
    
    			int i = this.mAudioManager.getStreamMaxVolume(AudioSystem.STREAM_RING);
    			this.mAudioManager.setStreamVolume(AudioSystem.STREAM_RING, i, AudioManager.FLAG_PLAY_SOUND|AudioManager.FLAG_ALLOW_RINGER_MODES);
    			initMediaPlayer();
       }
     
        {
          this.mAudioManager.setSpeakerphoneOn(false);
          setVolumeControlStream(0);
          this.mAudioManager.setMode(2);
          TimerTask local1 = new TimerTask()
          {
            public void run()
            {
              AgingTestMain.this.initMediaPlayer();
            }
          };
          super.onResume();
          new Timer().schedule(local1, 300L);
        }
       	
      
    }
     */
   
    
    
    
    	
    /*
    if ((bool1) || (bool3))
      getWindow().addFlags(128);
    if (bool1)
    {
      this.timer = new Timer();
      initView();
    }
    if (bool3)
    {
      this.mVibrator = ((Vibrator)getSystemService("vibrator"));
      this.mVibrator.vibrate(this.pattern, 0);
    }
    if (bool4)
      this.myHandler.sendEmptyMessageDelayed(1, 1000L);
    while (true)
    {
      return;
      mAudioManager = ((AudioManager)getSystemService("audio"));
      mAudioManager.setRingerMode(2);
      mAudioManager.setStreamVolume(3, this.mAudioManager.getStreamMaxVolume(3), 4);
      if (bool2)
        initMediaPlayer();
      while (bool6)
      {
        if (this.mCamera == null)
          this.mCamera = Camera.open();
        this.mParams = this.mCamera.getParameters();
        this.mParams.setFlashMode("torch");
        this.mCamera.setParameters(this.mParams);
        return;
        if (bool5)
        {
          this.mAudioManager.setSpeakerphoneOn(false);
          setVolumeControlStream(0);
          this.mAudioManager.setMode(2);
          TimerTask local1 = new TimerTask()
          {
            public void run()
            {
              AgingTestMain.this.initMediaPlayer();
            }
          };
          super.onResume();
          new Timer().schedule(local1, 300L);
        }
      }
    }
    */
  }
  
  public void onResume() {
  	super.onResume();
  	if (this.mVibrator != null)
      this.mVibrator.vibrate(this.pattern, 0);
  }
  

  protected void onDestroy()
  {
    super.onDestroy();
    if (this.timer != null)
      this.timer.cancel();
    if (this.mPlayer != null)
      this.mPlayer.stop();
    if (this.mAudioManager != null)
      this.mAudioManager.setMode(0);
    if (this.mVibrator != null)
      this.mVibrator.cancel();
    if (this.mRecorder != null)
      stopAndDelete();
    this.myHandler.removeMessages(2);
    this.myHandler.removeMessages(1);
    if (this.mCamera != null)
    {
      this.mParams = this.mCamera.getParameters();
      this.mParams.setFlashMode("off");
      this.mCamera.setParameters(this.mParams);
      this.mCamera.release();
      this.mCamera = null;
    }
  }
}

/* Location:           D:\classes_dex2jar.jar
 * Qualified Name:     com.mediatek.factorymode.agingtest.AgingTestMain
 * JD-Core Version:    0.6.2
 */