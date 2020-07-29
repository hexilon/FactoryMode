package com.mediatek.factorymode.earphone;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import java.util.Timer;
import java.util.TimerTask;

import java.io.IOException;
import android.content.res.AssetFileDescriptor;
import android.content.Context;


public class Earphone extends Activity
  implements View.OnClickListener
{
  private AudioManager am;
  private Button mBtFailed;
  private Button mBtOk;
//  private MediaPlayer mPlayer;
  private SharedPreferences mSp;
  private MediaPlayer mp;
  
/*  private void initMediaPlayer()
  {
    MediaPlayer localMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.tada);
    this.mPlayer = localMediaPlayer;
    this.mPlayer.setLooping(true);
    this.mPlayer.start();
  }*/

  public void onClick(View paramView)
  {
    SharedPreferences localSharedPreferences = this.mSp;
    if(paramView.getId() == this.mBtOk.getId())
    {
        Utils.SetPreferences(this, localSharedPreferences,R.string.earphone_name, "success");
        finish();
    }
    else{
        Utils.SetPreferences(this, localSharedPreferences,R.string.earphone_name, "failed");
        finish();
    }

    /*int i = 2131230845;
    int j = paramView.getId();
    int k = this.mBtOk.getId();
    if (j == k);
    for (String str = "success"; ; str = "failed")
    {
      Utils.SetPreferences(this, localSharedPreferences, i, str);
      finish();
      return;
    }*/
  }

  public void onCreate(Bundle paramBundle)
  {
    
    super.onCreate(paramBundle);
    setContentView(R.layout.earphone);
    SharedPreferences localSharedPreferences = getSharedPreferences("FactoryMode", 0);
    this.mSp = localSharedPreferences;
    Button localButton1 = (Button)findViewById(R.id.ear_bt_ok);
    this.mBtOk = localButton1;
    this.mBtOk.setOnClickListener(this);
    Button localButton2 = (Button)findViewById(R.id.ear_bt_failed);
    this.mBtFailed = localButton2;
    this.mBtFailed.setOnClickListener(this);
    /*AudioManager localAudioManager1 = (AudioManager)getSystemService("audio");
    this.mAudioManager = localAudioManager1;
    this.mAudioManager.setRingerMode(2);
    AudioManager localAudioManager2 = this.mAudioManager;
    int i = this.mAudioManager.getStreamMaxVolume(0);
    localAudioManager2.setStreamVolume(0, i, 4);
    this.mAudioManager.setSpeakerphoneOn(false);
    setVolumeControlStream(0);
    this.mAudioManager.setMode(2);
    initMediaPlayer();*/
		am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
		am.setStreamVolume(AudioManager.STREAM_VOICE_CALL,
				am.getStreamMaxVolume(AudioManager.STREAM_VOICE_CALL), 0);
		setVolumeControlStream(AudioManager.STREAM_VOICE_CALL);
    am.setSpeakerphoneOn(false);
		am.setMode(AudioManager.MODE_IN_CALL);
		try {
			mp = new MediaPlayer();
			AssetFileDescriptor afd = this.getResources().openRawResourceFd(
					R.raw.hassium);
			if (afd == null) {
				return;
			}

			mp.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),
					afd.getLength());
			afd.close();
			mp.setAudioStreamType(AudioManager.STREAM_VOICE_CALL);

			mp.prepare();
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
		}
		mp.start();
    
  }
  protected void onPause()
  {
    super.onPause();
    if (this.mp != null)
      this.mp.stop();
  }
  protected void onDestroy()
  {
		mp.stop();
		mp.release();
		mp = null;
		am.setMode(AudioManager.MODE_NORMAL);
    super.onDestroy();
  }
  public boolean onKeyDown(int paramInt, KeyEvent paramKeyEvent)
  {
    if ((paramInt == 4) && (paramKeyEvent.getRepeatCount() == 0))
      finish();
    return true;
  }
  protected void onResume()
  {
    super.onResume();
    
  }
}
