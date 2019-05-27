package com.funtv.remotedignostic.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.TextView;

import java.lang.reflect.Method;


public class DiagnosticService extends Service {
    public static final String TAG = DiagnosticService.class.getSimpleName();

    private static WindowManager wm = null;
    private static WindowManager.LayoutParams params;
    private static TextView lableView = null;

    public static final String ACTION_REMOTE_DIGNOSTIC = "funshion.intent.action.REMOTE_DIGNOSTIC";
    public static final String ACTION_STOP_DIGNOSTIC = "funshion.intent.action.STOP_DIGNOSTIC";

    private void createFloatView() {
        wm = (WindowManager)getApplicationContext().getSystemService(Context.WINDOW_SERVICE);
        params = new WindowManager.LayoutParams();
        lableView = new TextView(this);;

        Log.i(TAG, "createFloatView IN--");
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        params.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.gravity = Gravity.TOP | Gravity.LEFT;
        params.format = PixelFormat.TRANSPARENT;
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;

//        tView.setText(getResources().getString(R.string.label_remotedignostic_mode));
        lableView.setText("远程协助");
        lableView.setBackgroundColor(Color.parseColor("#FF22FF"));
        wm.addView(lableView, params);

        setProperty("service.fun.remotediagnostic", "1");
    }

    
    @Override
    public final IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        createFloatView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        wm.removeView(lableView);
        Log.d(TAG, "onDestroy");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null && intent.getAction() != null) {
            if (ACTION_REMOTE_DIGNOSTIC.equals(intent.getAction())) {
                Log.d(TAG, "onStartCommand ACTION_REMOTE_DIGNOSTIC");


                createFloatView();
            }
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void setProperty(String key, String value) {
        try {
            Class<?> c = Class.forName("android.os.SystemProperties");
            Method set = c.getMethod("set", String.class, String.class);
            set.invoke(c, key, value );
        } catch (Exception e) {
            Log.d(TAG, "setProperty====exception=");
            e.printStackTrace();
        }
    }
}
