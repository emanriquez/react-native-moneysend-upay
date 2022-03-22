package com.reactnativemoneysendreactnative;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

import com.upayments.moneysend.BioCaller;
import com.upayments.moneysend.model.MoneyData;


@ReactModule(name = MoneysendReactnativeModule.NAME)
public class MoneysendReactnativeModule extends ReactContextBaseJavaModule {
    public static final String NAME = "MoneysendReactnative";

    private Callback callback;
    private Activity context;

    private final ReactApplicationContext reactContext;



    public MoneysendReactnativeModule(ReactApplicationContext reactContext) {
        super(reactContext);
         this.reactContext = reactContext;
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
        Log.d(TAG, "INIT MONEYSEND REACT");

        String senderid = senderid;
        String urlmoney = url;
        String apikey = token;
        BioCaller.MoneyCreateLink(activity, senderid, urlmoney, apikey, 2929);
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
  public void onNewIntent(Intent intent) {

  }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "requestCode2: " + requestCode);
    Log.d(TAG, "resultCode2: " + resultCode);
    Log.d(TAG, "data2: " + data);

    if(requestCode == 2929){
      if(resultCode == Activity.RESULT_OK){
        MoneyData resultada = BioCaller.getMoneyResultData();

       
        // Log.d(TAG, "data2: " + json);
        //Toast.makeText(this, "Flujo completado para " + faceIdResultData.scanDocumentData.getRut().toString() + " " + faceIdResultData.resultado, Toast.LENGTH_SHORT).show();
      } else {
        //Toast.makeText(this, "Flujo cancelado", Toast.LENGTH_SHORT).show();
      }
    }


  }

   @Override
  public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
    Log.d(TAG, "requestCode: " + requestCode);
    Log.d(TAG, "resultCode: " + resultCode);
    Log.d(TAG, "data: " + data);

     if(resultCode == 2929){
       MoneyData resultada = BioCaller.getMoneyResultData();
      try {
        callBack(resultada);
      } catch (Exception e){
        callBackError("Exception " + e.getMessage());
      }
    } else if(requestCode == 2930){
      MoneyData resultada = BioCaller.getFaceIdResultData();
      try {
        callBack(resultada);
      } catch (Exception e){
        callBackError("Exception " + e.getMessage());
      }
    }
  }




}
