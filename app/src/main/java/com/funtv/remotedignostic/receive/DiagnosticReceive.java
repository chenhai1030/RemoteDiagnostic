package com.funtv.remotedignostic.receive;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.funtv.remotedignostic.service.DiagnosticService;


public class DiagnosticReceive extends BroadcastReceiver {
    public static final String TAG = DiagnosticService.TAG;


    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "MonitorReceiver onReceive=" + intent.getAction());
        String action = intent.getAction();
        if (action == null) return;
        if (action.equals(DiagnosticService.ACTION_REMOTE_DIGNOSTIC)) {
            Intent Service = new Intent(DiagnosticService.ACTION_REMOTE_DIGNOSTIC);
            Service.setClass(context, DiagnosticService.class);
            context.startService(Service);
        }else if (action.equals(DiagnosticService.ACTION_STOP_DIGNOSTIC)){
            Intent Service = new Intent(DiagnosticService.ACTION_REMOTE_DIGNOSTIC);
            Service.setClass(context, DiagnosticService.class);
            context.stopService(Service);
        }

    }

}