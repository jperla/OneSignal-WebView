/**
 * Modified MIT License
 * 
 * Copyright 2015 OneSignal
 * 
 * Portions Copyright 2013 Google Inc.
 * This file includes portions from the Google GcmClient demo project
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * 1. The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * 2. All copies of substantial portions of the Software may only be used in connection
 * with services provided by OneSignal.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.gamethrive;

import com.onesignal.NotificationBundleProcessor;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;

/**
 * This {@code IntentService} does the actual handling of the GCM message.
 * {@code GcmBroadcastReceiver} (a {@code WakefulBroadcastReceiver}) holds a
 * partial wake lock for this service while the service does its work. When the
 * service is finished, it calls {@code completeWakefulIntent()} to release the
 * wake lock.
 */
public class GcmIntentService extends IntentService {
    
    private static final String GCM_RECEIVE = "com.google.android.c2dm.intent.RECEIVE";
    private static final String GCM_TYPE = "gcm";
    
    public GcmIntentService() {
        super("GcmIntentService");
    }
    
    private static boolean isGcmMessage(Intent intent) {
    	if (GCM_RECEIVE.equals(intent.getAction())) {
    		String messageType = intent.getStringExtra("message_type");
    		return (messageType == null || GCM_TYPE.equals(messageType));
    	}
    	return false;
    }
    
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        
    	if (isGcmMessage(intent))
    		NotificationBundleProcessor.Process(this, extras);
        
        // Release the wake lock provided by the WakefulBroadcastReceiver.
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}