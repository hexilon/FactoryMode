package com.mediatek.factorymode.agingtest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.mediatek.factorymode.R;

public class AgingTestBegin extends Activity
{
  private Button mBegintest;
  private CheckBox mCheckBoxEarphone;
  private CheckBox mCheckBoxFlashlight;
  private CheckBox mCheckBoxLcd;
  private CheckBox mCheckBoxMic;
  private CheckBox mCheckBoxSpeaker;
  private CheckBox mCheckBoxVibrator;

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    setContentView(R.layout.agingtest_begin);
    this.mCheckBoxLcd = ((CheckBox)findViewById(R.id.checkBoxLcd));
    this.mCheckBoxSpeaker = ((CheckBox)findViewById(R.id.checkBoxSpeaker));
    this.mCheckBoxSpeaker.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          AgingTestBegin.this.mCheckBoxMic.setChecked(false);
      }
    });
    this.mCheckBoxVibrator = ((CheckBox)findViewById(R.id.checkBoxVibrator));
    this.mCheckBoxEarphone = ((CheckBox)findViewById(R.id.checkBoxEarphone));
    this.mCheckBoxEarphone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
          AgingTestBegin.this.mCheckBoxMic.setChecked(false);
      }
    });
    this.mCheckBoxMic = ((CheckBox)findViewById(R.id.checkBoxMic));
    this.mCheckBoxMic.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
    {
      public void onCheckedChanged(CompoundButton paramAnonymousCompoundButton, boolean paramAnonymousBoolean)
      {
        if (paramAnonymousBoolean)
        {
          AgingTestBegin.this.mCheckBoxEarphone.setChecked(false);
          AgingTestBegin.this.mCheckBoxSpeaker.setChecked(false);
        }
      }
    });
    this.mCheckBoxFlashlight = ((CheckBox)findViewById(R.id.checkBoxFlashlight));
    this.mBegintest = ((Button)findViewById(R.id.begintest));
    this.mBegintest.setOnClickListener(new View.OnClickListener()
    {
      public void onClick(View paramAnonymousView)
      {
        Intent localIntent = new Intent();
        if ((AgingTestBegin.this.mCheckBoxLcd.isChecked()) || (AgingTestBegin.this.mCheckBoxSpeaker.isChecked()) || (AgingTestBegin.this.mCheckBoxVibrator.isChecked()) || (AgingTestBegin.this.mCheckBoxEarphone.isChecked()) || (AgingTestBegin.this.mCheckBoxMic.isChecked()) || (AgingTestBegin.this.mCheckBoxFlashlight.isChecked()))
        {
          localIntent.putExtra("lcd", AgingTestBegin.this.mCheckBoxLcd.isChecked());
          localIntent.putExtra("speaker", AgingTestBegin.this.mCheckBoxSpeaker.isChecked());
          localIntent.putExtra("vibrator", AgingTestBegin.this.mCheckBoxVibrator.isChecked());
          localIntent.putExtra("mic", AgingTestBegin.this.mCheckBoxMic.isChecked());
          localIntent.putExtra("earphone", AgingTestBegin.this.mCheckBoxEarphone.isChecked());
          localIntent.putExtra("flashlight", AgingTestBegin.this.mCheckBoxFlashlight.isChecked());
          localIntent.setClassName("com.mediatek.factorymode", "com.mediatek.factorymode.agingtest.AgingTestMain");
          AgingTestBegin.this.startActivity(localIntent);
        }
      }
    });
  }
}
