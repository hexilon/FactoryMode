/* Copyright Statement:
 *
 * This software/firmware and related documentation ("MediaTek Software") are
 * protected under relevant copyright laws. The information contained herein
 * is confidential and proprietary to MediaTek Inc. and/or its licensors.
 * Without the prior written permission of MediaTek inc. and/or its licensors,
 * any reproduction, modification, use or disclosure of MediaTek Software,
 * and information contained herein, in whole or in part, shall be strictly prohibited.
 */
/* MediaTek Inc. (C) 2010. All rights reserved.
 *
 * BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 * THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 * RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER ON
 * AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL WARRANTIES,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED WARRANTIES OF
 * MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR NONINFRINGEMENT.
 * NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH RESPECT TO THE
 * SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY, INCORPORATED IN, OR
 * SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES TO LOOK ONLY TO SUCH
 * THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO. RECEIVER EXPRESSLY ACKNOWLEDGES
 * THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES
 * CONTAINED IN MEDIATEK SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK
 * SOFTWARE RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 * STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S ENTIRE AND
 * CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE RELEASED HEREUNDER WILL BE,
 * AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE MEDIATEK SOFTWARE AT ISSUE,
 * OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE CHARGE PAID BY RECEIVER TO
 * MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 * The following software/firmware and/or related documentation ("MediaTek Software")
 * have been modified by MediaTek Inc. All revisions are subject to any receiver's
 * applicable license agreements with MediaTek Inc.
 */

package com.mediatek.factorymode;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;


public class FactoryModeReceiver extends BroadcastReceiver {

    private final String TAG = "FM/SECRET_CODE";
    // process *#*#66#*#*
    Uri AgingTest  =Uri.parse("android_secret_code://65");
    Uri engineerUri = Uri.parse("android_secret_code://66");
    Uri versionUri = Uri.parse("android_secret_code://67");
    Uri TeeInfoUri = Uri.parse("android_secret_code://07");
    Uri hardwareInfoUri = Uri.parse("android_secret_code://0412");
    Uri TestYgpsUri = Uri.parse("android_secret_code://0901");
    @Override
    public void onReceive(Context context, Intent intent) {
	    try {
	        if (intent.getAction().equals("android.provider.Telephony.SECRET_CODE")) {
				
	            Uri uri = intent.getData();
	            Log.i(TAG, "getIntent success in if");
	            Log.i(TAG, "bbbb #uri=" + uri);//add joyar
	            
	            if (uri.equals(AgingTest)) 
				{
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.factorymode",
	                                "com.mediatek.factorymode.agingtest.AgingTestBegin"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	} else if (uri.equals(engineerUri)) 
				{
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.factorymode",
	                                "com.mediatek.factorymode.FactoryModeEntry"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	} else if(uri.equals(versionUri))
		    	{
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.factorymode",
	                                "com.mediatek.factorymode.VersionPa"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	} else if(uri.equals(TeeInfoUri)) {
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.factorymode",
	                                "com.mediatek.factorymode.TeeInformation"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	} else if(uri.equals(hardwareInfoUri)) {
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.factorymode",
	                                "com.mediatek.factorymode.HardwareInformation"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	}else if(uri.equals(TestYgpsUri)) {
	                Intent i = new Intent(Intent.ACTION_MAIN);
	                i.setComponent(new ComponentName("com.mediatek.ygps",
	                                "com.mediatek.ygps.YgpsActivity"));
	                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	                context.startActivity(i);
		    	} else 
		    	{
	                Log.i(TAG,"No matched URI!");
		    	}
	        } else {
	            Log.i(TAG,"Not SECRET_CODE_ACTION!");
		}
      } catch(Exception e) {
			e.printStackTrace();
			Log.w(TAG, "Package exception.");
      }
    }
}
