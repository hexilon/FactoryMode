package com.mediatek.factorymode.microphone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.media.AudioSystem;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.mediatek.factorymode.VUMeter;
import java.io.File;
import java.io.IOException;

public class MicRecordersub extends Activity
  implements View.OnClickListener
{
  Handler mHandler;
  private Button mBtMicFailed;
  private Button mBtMicOk;
  private Button mBtSpkFailed;
  private Button mBtSpkOk;
  boolean mMicClick = false;
  private MediaPlayer mPlayer;
  private Button mRecord;
  private MediaRecorder mRecorder;
  SharedPreferences mSp;
  boolean mSpkClick = false;
  VUMeter mVUMeter;
  Runnable mRunnable;
  private String recordpath;
  private boolean isMicSub =false;
  class MicRecorder1 implements Runnable
    {
      public void run()
      {
        if(mVUMeter!=null){
		  mVUMeter.invalidate();
		}
        mHandler.postDelayed(this, 100L);
      }
    }

  public MicRecordersub()
  {
    this.mHandler = new Handler();
    this.mRunnable = new MicRecorder1();
  }

  private void start()
  {
      this.mHandler.post(this.mRunnable);
	  AudioSystem.setParameters("SET_MIC_CHOOSE=2");
      if (this.mPlayer != null)
          this.mPlayer.stop();
      if (!Environment.getExternalStorageState().equals("mounted"))
          this.mRecord.setText(R.string.sdcard_tips_failed);
      try
      {
        this.mRecorder = new MediaRecorder();
        //this.mRecorder.setAudioSource(1);
        //this.mRecorder.setOutputFormat(1);
        //this.mRecorder.setAudioEncoder(3);
       
        this.mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        this.mVUMeter.setRecorder(this.mRecorder);
//        StringBuilder localStringBuilder = new StringBuilder();
//        String str = null;
//        File localFile = Environment.getDataDirectory();
//        localStringBuilder.append(localFile).append(File.separator).append("test.mp3");
//        str = localStringBuilder.toString();
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
        this.mRecord.setTag("ing");
      }

    /*Handler localHandler = this.mHandler;
    Runnable localRunnable = this.mRunnable;
    localHandler.post(localRunnable);
    if (this.mPlayer != null)
      this.mPlayer.stop();
    if (!Environment.getExternalStorageState().equals("mounted"))
      this.mRecord.setText(2131230866);
    while (true)
    {
      return;
      try
      {
        MediaRecorder localMediaRecorder1 = new MediaRecorder();
        this.mRecorder = localMediaRecorder1;
        this.mRecorder.setAudioSource(1);
        this.mRecorder.setOutputFormat(1);
        this.mRecorder.setAudioEncoder(3);
        VUMeter localVUMeter = this.mVUMeter;
        MediaRecorder localMediaRecorder2 = this.mRecorder;
        localVUMeter.setRecorder(localMediaRecorder2);
        StringBuilder localStringBuilder1 = new StringBuilder();
        File localFile = Environment.getExternalStorageDirectory();
        StringBuilder localStringBuilder2 = localStringBuilder1.append(localFile);
        String str1 = File.separator;
        String str2 = str1 + "test.mp3";
        if (!new File(str2).exists())
          new File(str2).createNewFile();
        this.mRecorder.setOutputFile(str2);
        this.mRecorder.prepare();
        this.mRecorder.start();
        label203: this.mRecord.setTag("ing");
        this.mRecord.setText(2131230803);
      }
      catch (Exception localException)
      {
        String str3 = localException.getMessage();
        Toast.makeText(this, str3, 0);
        break label203:
      }
    }*/
  }

  private void stopAndSave()
  {
    this.mHandler.removeCallbacks(this.mRunnable);
    this.mRecorder.stop();
    this.mRecorder.release();
    this.mRecorder = null;
    this.mRecord.setText(R.string.Mic_start);
    this.mRecord.setTag("");
    this.mVUMeter.SetCurrentAngle(0);
    try
    {

      MediaPlayer localMediaPlayer = new MediaPlayer();
      this.mPlayer = localMediaPlayer;
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

  public void isFinish()
  {
    if ((this.mMicClick != true) || (this.mSpkClick != true))
       return;
   
    AudioSystem.setParameters("SET_MIC_CHOOSE=0");
    finish();
  }

  public void onClick(View paramView)
  {
      SharedPreferences localSharedPreferences = this.mSp;
      String str1 = Environment.getExternalStorageState();
      String str2 = "mounted";

      if(paramView.getId() == this.mRecord.getId())
      {
    	if(str1.equals(str2))
    	{
          if ((this.mRecord.getTag() != null) && (this.mRecord.getTag().equals("ing"))){
              stopAndSave();
			  isMicSub =true;
          }else{
              start();
			  isMicSub =false;
		}
    	}
    	else
    	{
    		this.mRecord.setText(R.string.sdcard_tips_failed);
    	}
		mBtMicOk.setEnabled(isMicSub);
		mBtSpkOk.setEnabled(isMicSub);
      }

      if(paramView.getId() == this.mBtMicOk.getId())
      {
          this.mMicClick = true;
          this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.gray));
          this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.Green));
          Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_namesub, "success");
      }

      if(paramView.getId() == this.mBtMicFailed.getId())
      {
          this.mMicClick = true;
          this.mBtMicFailed.setBackgroundColor(getResources().getColor(R.color.Red));
          this.mBtMicOk.setBackgroundColor(getResources().getColor(R.color.gray));
          Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_namesub, "failed");
      }
      if(paramView.getId() == this.mBtSpkOk.getId())
      {
          this.mSpkClick = true;
          this.mBtSpkOk.setBackgroundColor(getResources().getColor(R.color.Green));
          this.mBtSpkFailed.setBackgroundColor(getResources().getColor(R.color.gray));
          Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_namesub, "success");
      }
      if(paramView.getId() == this.mBtSpkFailed.getId())
      {
          this.mSpkClick = true;
          this.mBtSpkFailed.setBackgroundColor(getResources().getColor(R.color.Red));
          this.mBtSpkOk.setBackgroundColor(getResources().getColor(R.color.gray));
          Utils.SetPreferences(this, localSharedPreferences, R.string.microphone_namesub, "failed");
      }
      isFinish();
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.micrecordersub);
    this.mSp = getSharedPreferences("FactoryMode", 0);
    this.mRecord = (Button)findViewById(R.id.mic_bt_start);
    this.mRecord.setOnClickListener(this);
    this.mBtMicOk = (Button)findViewById(R.id.mic_bt_ok);
    this.mBtMicOk.setOnClickListener(this);
	mBtMicOk.setEnabled(isMicSub);
    this.mBtMicFailed = (Button)findViewById(R.id.mic_bt_failed);
    this.mBtMicFailed.setOnClickListener(this);
    this.mBtSpkOk = (Button)findViewById(R.id.speaker_bt_ok);
    this.mBtSpkOk.setOnClickListener(this);
	mBtSpkOk.setEnabled(isMicSub);
    this.mBtSpkFailed = (Button)findViewById(R.id.speaker_bt_failed);
    this.mBtSpkFailed.setOnClickListener(this);
    this.mVUMeter = (VUMeter)findViewById(R.id.uvMeter);
    StringBuilder localStringBuilder = new StringBuilder();
    String str = null;
    File localFile = Environment.getExternalStorageDirectory();
    localStringBuilder.append(localFile).append(File.separator).append("test.mp3");
    str = localStringBuilder.toString();
    recordpath = str;
  }

  protected void onDestroy()
  {
    super.onDestroy();
    new File(recordpath).delete();
    if (this.mPlayer != null)
      this.mPlayer.stop();
    if (this.mRecorder != null)
        this.mRecorder.stop();
	AudioSystem.setParameters("SET_MIC_CHOOSE=0");
  }
}
