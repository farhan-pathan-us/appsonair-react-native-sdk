package com.rnappsonair;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.modules.core.DeviceEventManagerModule;

public class DetectorModule extends ReactContextBaseJavaModule implements ScreenshotDetectionListener {

    private ScreenshotDetectionDelegate screenshotDetectionDelegate;
    private boolean isDialogOpen = false;

    public DetectorModule(ReactApplicationContext reactContext) {
        super(reactContext);
        screenshotDetectionDelegate = new ScreenshotDetectionDelegate(reactContext, this);
    }

    @Override
    public String getName() {
        return "Detector";
    }

    @ReactMethod
    public void startScreenshotDetection() {
        screenshotDetectionDelegate.startScreenshotDetection();
    }

    @ReactMethod
    public void stopScreenshotDetection() {
        screenshotDetectionDelegate.stopScreenshotDetection();
    }

    @Override
    public void onScreenCaptured(final String path) {
        // Show native modal only if the dialog is not already open
        if (!isDialogOpen) {
            showNativeModal(path);
        }
    }

    @Override
    public void onScreenCapturedWithDeniedPermission() {
        // Todo: send user notification.
    }

    private void showNativeModal(final String path) {
        isDialogOpen = true;

        AlertDialog.Builder builder = new AlertDialog.Builder(getCurrentActivity());
        builder.setTitle("Screenshot Captured")
                .setMessage("Screenshot captured at path: " + path)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        isDialogOpen = false; // Reset the flag when the dialog is dismissed
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}