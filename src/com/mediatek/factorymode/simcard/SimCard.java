package com.mediatek.factorymode.simcard;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.telephony.TelephonyManager;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import com.mediatek.factorymode.R;
import com.mediatek.factorymode.Utils;
import com.android.internal.telephony.TelephonyProperties;
import com.android.internal.telephony.PhoneConstants;
import android.os.SystemProperties;
//import com.mediatek.telephony.TelephonyManagerEx;
import android.util.Log;// lb add
import android.content.Context;// lb add
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import android.view.View;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;//add joyar
import java.util.List;//add joyar
import android.telephony.SubscriptionInfo;//add joyar

public class SimCard extends Activity implements View.OnClickListener {
  private boolean SimState = false;
  private boolean Sim1State = false;
  private boolean Sim2State = false;
  private int InsetStateSim1;
  private int InsetStateSim2;
  private String mSimStatus;
  private boolean isInsertedSIM1 = false;
  private boolean isInsertedSIM2 = false;
  SharedPreferences mSp;
  private TextView simcard_sim1_info,simcard_sim2_info;
  private Button mBtFailed;
  private Button mBtOk;
  //private int absent = TelephonyManager.SIM_STATE_ABSENT;
    private SubscriptionManager mSubscriptionManager;//add joyar
    private List<SubscriptionInfo> mSubInfoList = null;//add joyar
    private List<SubscriptionInfo> mAvailableSubInfos = null;//add joyar
    private int mNumSlots;//add joyar

  public void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.simcard);
	simcard_sim1_info = (TextView)findViewById(R.id.simcard_sim1_info);
	simcard_sim2_info = (TextView)findViewById(R.id.simcard_sim2_info);
    this.mSp = getSharedPreferences("FactoryMode", 0);
	
	TelephonyManager manager = TelephonyManager.getDefault();
	Sim1State = manager.hasIccCard(SubscriptionManager.getSlotIndex(PhoneConstants.SIM_ID_1));
	Sim2State = manager.hasIccCard(SubscriptionManager.getSlotIndex(PhoneConstants.SIM_ID_2));
	Log.e("bbbb", "#getDefaultSubscriptionId=" + SubscriptionManager.getDefaultSubscriptionId());
	
    // lb add start
    final TelephonyManager mTelephonyManager =
        (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
    final int countSlots = mTelephonyManager.getSimCount();
    final int indexSim1 = SubscriptionManager.getSlotIndex(PhoneConstants.SIM_ID_1);
    final int indexSim2 = SubscriptionManager.getSlotIndex(PhoneConstants.SIM_ID_2);
	
	Sim1State = mTelephonyManager.hasIccCard(indexSim1);
	Sim2State = mTelephonyManager.hasIccCard(indexSim2);
	
	InsetStateSim1 = mTelephonyManager.getSimState(indexSim1);
	InsetStateSim2 = mTelephonyManager.getSimState(indexSim2);
	
	Log.w("bbbb", "#countSlots=" + countSlots
		+ " #Sim1State=" + Sim1State
		+ " #Sim2State=" + Sim2State
		+ " #indexSim1=" + indexSim1
		+ " #indexSim2=" + indexSim2
		+ " #InsetStateSim1=" + InsetStateSim1
		+ " #InsetStateSim2=" + InsetStateSim2);
	
	/*if (Sim1State || TelephonyManager.SIM_STATE_READY == InsetStateSim1) {
		simcard_sim1_info.setText(getString(R.string.sim1_info_ok));
		isInsertedSIM1 = true;
	} else {
		simcard_sim1_info.setText(getString(R.string.sim1_info_failed));
		isInsertedSIM1 = false;
	}

	if (Sim2State || TelephonyManager.SIM_STATE_READY == InsetStateSim2) {
		simcard_sim2_info.setText(getString(R.string.sim2_info_ok));
		isInsertedSIM2 = true;
	} else {
		simcard_sim2_info.setText(getString(R.string.sim2_info_failed));
		isInsertedSIM2 = false;
	}*/

    mNumSlots = mTelephonyManager.getSimCount();//add joyar start
    mAvailableSubInfos = new ArrayList<SubscriptionInfo>(mNumSlots);
    mSubscriptionManager = SubscriptionManager.from(SimCard.this);
    mSubInfoList = mSubscriptionManager.getActiveSubscriptionInfoList();
    for (int i = 0; i < mNumSlots; ++i) {
        final SubscriptionInfo sir = mSubscriptionManager
                .getActiveSubscriptionInfoForSimSlotIndex(i);
        if (sir != null) {
			Log.i("bbbb", "#i=" + i);
			if (i == 0) {
				isInsertedSIM1 = true;
			} else if (i == 1) {
				isInsertedSIM2 = true;
			}
		}
	}
	Log.w("bbbb", " #isInsertedSIM1=" + isInsertedSIM1
		+ " #isInsertedSIM2=" + isInsertedSIM2);
	if (isInsertedSIM1) {
		simcard_sim1_info.setText(getString(R.string.sim1_info_ok));
	} else {
		simcard_sim1_info.setText(getString(R.string.sim1_info_failed));
	}

	if (isInsertedSIM2) {
		simcard_sim2_info.setText(getString(R.string.sim2_info_ok));
	} else {
		simcard_sim2_info.setText(getString(R.string.sim2_info_failed));
	}//add joyar end

	
	/*Object result_0 = null;
	Object result_1 = null;
	try{		       
		//Method method = TelephonyManager.class.getMethod("getSimStateGemini",new Class[] { int.class });
		//Method method = TelephonyManagerEx.class.getMethod("getSimState",new Class[] { int.class });
		//result_0 = method.invoke(TelephonyManagerEx.getDefault(), new Object[] { new Integer(0) });
		//result_1 = method.invoke(TelephonyManagerEx.getDefault(), new Object[] { new Integer(1) });
		}catch (Exception e) {
		// TODO: handle exception
		Log.w("FactoryTest", "simcardTest:execute getSimState failed");
	}
	//end	
			
    StringBuilder localStringBuilder1 = new StringBuilder();
	if (result_0 != null && result_1 != null) {
	    if(this.Sim1State || "5".equals(result_0.toString())){
	        //localStringBuilder1.append(getString(R.string.sim1_info_ok)).append("\n");
			simcard_sim1_info.setText(getString(R.string.sim1_info_ok));
			isSIM=true;
	    }else{
	        //localStringBuilder1.append(getString(R.string.sim1_info_failed)).append("\n");
			simcard_sim1_info.setText(getString(R.string.sim1_info_failed));
			isSIM=false;
	    }

	    if(this.Sim2State || "5".equals(result_1.toString())){
	        //localStringBuilder1.append(getString(R.string.sim2_info_ok)).append("\n");
			simcard_sim2_info.setText(getString(R.string.sim2_info_ok));
			isSIM=true;
	    }else{
	        //localStringBuilder1.append(getString(R.string.sim2_info_failed)).append("\n");
			simcard_sim2_info.setText(getString(R.string.sim2_info_failed));
			isSIM=false;
	    }
	}*/
	
    this.mBtOk = (Button)findViewById(R.id.simcard_bt_ok);
	if (isInsertedSIM1 && isInsertedSIM2) {
		mBtOk.setEnabled(true);
	} else {
		mBtOk.setEnabled(false);
	}
    this.mBtOk.setOnClickListener(this);
    this.mBtFailed = (Button)findViewById(R.id.simcard_bt_failed);
    this.mBtFailed.setOnClickListener(this);
	
    /*this.mSimStatus = localStringBuilder1.toString();
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
    builder.setTitle(R.string.SimCard)
    .setMessage(this.mSimStatus)
    .setPositiveButton(R.string.Success, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
			 if(isSIM){
             Utils.SetPreferences(SimCard.this, SimCard.this.mSp, R.string.sim_name, "success");
			 SimCard.this.finish();
		    }else{
              SimCard.this.finish();
			}
        }
    })
    .setNegativeButton(R.string.Failed, new DialogInterface.OnClickListener(){
        public void onClick(DialogInterface dialog, int which) {
            // TODO Auto-generated method stub
            Utils.SetPreferences(SimCard.this, SimCard.this.mSp, R.string.sim_name, "failed");
            SimCard.this.finish();
        }
    });
     AlertDialog d = builder.create();
	    d.show();
        d.setCanceledOnTouchOutside(false);
        d.setOnKeyListener(new OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
                      SimCard.this.finish();
                }
                return false;
            }
        });*/
		
  }
	public void onClick(View paramView) {
		SharedPreferences localSharedPreferences = this.mSp;
		if (paramView.getId() == this.mBtOk.getId() && isInsertedSIM1 && isInsertedSIM2) {
		    Utils.SetPreferences(this, localSharedPreferences, R.string.sim_name, "success");
		    finish();
		} else if (paramView.getId() == this.mBtFailed.getId()) {
		    Utils.SetPreferences(this, localSharedPreferences, R.string.sim_name, "failed");
		    finish();
		}
	}
}

