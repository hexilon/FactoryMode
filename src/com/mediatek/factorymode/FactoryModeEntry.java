package com.mediatek.factorymode;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Process;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.content.ComponentName;
//import dalvik.annotation.Signature;
import java.util.ArrayList;
import java.util.List;
import java.io.File;
//import com.mediatek.common.featureoption.FeatureOption;
import android.widget.Toast;
import android.util.Log;

public class FactoryModeEntry extends Activity {  
    Button fmButton1,fmButton2,fmButton3,fmButton4;  
    
    protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
	{
		System.gc();
		Intent localIntent = new Intent();
		localIntent.setClassName(this, "com.mediatek.factorymode.Report");
		Log.d("wangshengyuan","FactoryMode-onActivityResult__________-");		
		startActivity(localIntent);
	}
    
    @Override
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState); 
        requestWindowFeature(1); 
        setContentView(R.layout.factoryentry);  
          
        fmButton1=(Button)findViewById(R.id.fmButton1);  
        fmButton2=(Button)findViewById(R.id.fmButton2); 
        fmButton3=(Button)findViewById(R.id.fmButton3);  
        fmButton4=(Button)findViewById(R.id.fmButton4); 
        
        /*
        fmButton1.setOnClickListener(new OnClickListener()  
        {         
            public void onClick(View v)  
            {  
                Toast.makeText(FactoryModeEntry.this, "click fmButton1",Toast.LENGTH_LONG).show();  
            }  
        });  
        
        fmButton2.setOnClickListener(new OnClickListener()  
        {         
            public void onClick(View v)  
            {  
                Toast.makeText(FactoryModeEntry.this, "click fmButton2",Toast.LENGTH_LONG).show();  
            }  
        });  

        fmButton3.setOnClickListener(new OnClickListener()  
        {         
            public void onClick(View v)  
            {  
                Toast.makeText(FactoryModeEntry.this, "click fmButton3",Toast.LENGTH_LONG).show();  
            }  
        });  
        
        fmButton4.setOnClickListener(new OnClickListener()  
        {         
            public void onClick(View v)  
            {  
                Toast.makeText(FactoryModeEntry.this, "click fmButton4",Toast.LENGTH_LONG).show();  
            }  
        }); 
        */  
        
        fmButton1.setOnClickListener(new ButtonClick());  
        fmButton2.setOnClickListener(new ButtonClick()); 
        fmButton3.setOnClickListener(new ButtonClick());  
        fmButton4.setOnClickListener(new ButtonClick());   
    }  
    
    //create a class to attack OnClickListener 
    class ButtonClick implements OnClickListener  
    {  
        public void onClick(View v)  
        {  
			Intent intent = new  Intent();
			//String str = "com.mediatek.factorymode";
            switch (v.getId()) {  
				case R.id.fmButton1:  
					//intent.setClassName("com.mediatek.factorymode", "com.mediatek.factorymode.AllTest");
					//intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					////intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					//startActivity(intent);		
					//Toast.makeText(FactoryModeEntry.this, "click fmButton1",Toast.LENGTH_LONG).show();
					
					intent.setClassName("com.mediatek.factorymode", "com.mediatek.factorymode.AllTest");
					FactoryModeEntry.this.startActivityForResult(intent, 8192);
					break;  
				case R.id.fmButton2:  
					intent.setClassName("com.mediatek.factorymode", "com.mediatek.factorymode.FactoryMode");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);	
					//Toast.makeText(FactoryModeEntry.this, "click fmButton2",Toast.LENGTH_LONG).show();
					break;
				case R.id.fmButton3:  
					intent.setClassName("com.mediatek.factorymode", "com.mediatek.agingtest.AgingTestEntry");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					//Toast.makeText(FactoryModeEntry.this, "click fmButton3",Toast.LENGTH_LONG).show();
					break;  
				case R.id.fmButton4:  
					intent.setClassName("com.mediatek.factorymode", "com.mediatek.factorymode.factoryreset.FactoryReset");
					intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					//intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT | Intent.FLAG_ACTIVITY_SINGLE_TOP);
					startActivity(intent);
					//Toast.makeText(FactoryModeEntry.this, "click fmButton4",Toast.LENGTH_LONG).show();
					break;	  
				default:  
					break;  
            }  
        }         
    }  
    
}  
