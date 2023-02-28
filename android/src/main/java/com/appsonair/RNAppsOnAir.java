package com.appsonair;

import android.util.Log;

import androidx.annotation.NonNull;

import com.appsonair.AppsOnAirServices;
import com.appsonair.UpdateCallBack;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.module.annotations.ReactModule;

@ReactModule(name = RNAppsOnAir.NAME)
public class RNAppsOnAir extends ReactContextBaseJavaModule {
  public static final String NAME = "RNAppsOnAir";
  private ReactApplicationContext reactContext;

  public RNAppsOnAir(ReactApplicationContext reactContext) {
    this.reactContext = reactContext;
  }

  @Override
  @NonNull
  public String getName() {
    return NAME;
  }


  // Example method
  // See https://reactnative.dev/docs/native-modules-android
  @ReactMethod
  void setAppId(String appId, boolean showNativeUI) {
    AppsOnAirServices.setAppId(appId, showNativeUI);
    if(showNativeUI){
      checkForAppUpdate(null);
    }
  }


  @ReactMethod
  void checkForAppUpdate(Callback callback) {
    AppsOnAirServices.checkForAppUpdate(reactContext, new UpdateCallBack() {
      @Override
      public void onSuccess(String response) {
        if(callback != null){
          callback.invoke(response);
        }
      }

      @Override
      public void onFailure(String message) {
        if(callback != null){
          callback.invoke(message);
        }
      }
    });
  }
}
