package com.reactnativemoneysendreactnative;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.upayments.moneysend.BioCaller;
import com.upayments.moneysend.model.MoneyData;


@ReactModule(name = MoneysendReactnativeModule.NAME)
public class MoneysendReactnativeModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static final String NAME = "MoneysendReactnative";
    private static final String TAG = "MONEY";

    private Callback callback;
    private Activity context;

    private final ReactApplicationContext reactContext;



    public MoneysendReactnativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
         this.reactContext = reactContext;
        // this.reactContext.addActivityEventListener(this);
        this.reactContext.addActivityEventListener(this);

    }

    @Override
    @NonNull
    public String getName() {
        return NAME;
    }



    private static final String ERROR_NO_ACTIVITY = "E_NO_ACTIVITY";
    private static final String ERROR_NO_ACTIVITY_MESSAGE = "Tried to do the something while not attached to an Activity";



    // Example method
    // See https://reactnative.dev/docs/native-modules-android
    @ReactMethod
    public void MoneySendUPAY(String senderid, String urlmoney, String token, Callback callback) {

        final Activity activity = getCurrentActivity();

        if (activity == null) {
            callBackError(ERROR_NO_ACTIVITY_MESSAGE);
        }

        this.callback = callback;
        try {



        BioCaller.MoneyCreateLink(activity, senderid, urlmoney, token, 2929);
        }catch (Exception e){
         Log.d(TAG, e.getMessage());
         callBackError(e.getMessage());
        }
    }

    private void callBackError(String sMensaje) {
        callback.invoke("ERROR", sMensaje);
    }

    private void callBack(String sMensaje) {
     callback.invoke(null,sMensaje);
    }



    @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "requestCode: " + requestCode);
    Log.d(TAG, "resultCode: " + resultCode);
    Log.d(TAG, "data: " + data);

    if(requestCode==2929){
        if(resultCode== Activity.RESULT_OK){
            MoneyData result = BioCaller.getMoneyResultData();
            Log.d("MoneySendLink", "onActivityResult: ");
            Log.d("MoneySendLink", result.trx);

            try {
                callBack(result.trx);
            } catch (Exception e){
                Log.e("MoneySendLink", e.toString());
               // callBackError("TRX ERROR");
            }


            }
    }


  }

    @Override
    public void onNewIntent(Intent intent) {

    }


}
