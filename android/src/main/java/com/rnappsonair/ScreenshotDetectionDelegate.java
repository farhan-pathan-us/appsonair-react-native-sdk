package com.rnappsonair;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class ScreenshotDetectionDelegate {

    private final Context context;
    private final ScreenshotDetectionListener listener;
    private ContentObserver contentObserver;

    private boolean isListening = false;
    private String previousPath = "";

    public ScreenshotDetectionDelegate(Context context, ScreenshotDetectionListener listener) {
        this.context = context;
        this.listener = listener;
    }

    public void startScreenshotDetection() {
        contentObserver = new ContentObserver(new Handler()) {
            @Override
            public void onChange(boolean selfChange, Uri uri) {
                super.onChange(selfChange, uri);
                if (isReadMediaPermissionGranted() && uri != null) {
                    String path = getFilePathFromContentResolver(context, uri);
                    if (path != null && isScreenshotPath(path)) {
                        previousPath = path;
                        onScreenCaptured(path);
                    }
                } else {
                    onScreenCapturedWithDeniedPermission();
                }
            }
        };

        context.getContentResolver().registerContentObserver(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                true,
                contentObserver
        );
        isListening = true;
    }

    public void stopScreenshotDetection() {
        context.getContentResolver().unregisterContentObserver(Objects.requireNonNull(contentObserver));
        isListening = false;
    }

    private void onScreenCaptured(String path) {
        listener.onScreenCaptured(path);
    }

    private void onScreenCapturedWithDeniedPermission() {
        listener.onScreenCapturedWithDeniedPermission();
    }

    private boolean isScreenshotPath(String path) {
        return path != null && path.toLowerCase().contains("screenshots") && !previousPath.equals(path);
    }

    private String getFilePathFromContentResolver(Context context, Uri uri) {
        try {
            String[] projection = {
                    MediaStore.Images.Media.DISPLAY_NAME,
                    MediaStore.Images.Media.DATA
            };
            android.database.Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
                return path;
            }
        } catch (Exception e) {
            // Handle the exception appropriately
        }
        return null;
    }

    private boolean isReadMediaPermissionGranted() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String readMediaPermission = "android.permission.READ_MEDIA_IMAGES";
            return ContextCompat.checkSelfPermission(context, readMediaPermission) == PackageManager.PERMISSION_GRANTED;
        } else {
            // For versions prior to Android 6, permissions are granted at install time
            return true;
        }
    }
}

interface ScreenshotDetectionListener {
    void onScreenCaptured(String path);
    void onScreenCapturedWithDeniedPermission();
}

