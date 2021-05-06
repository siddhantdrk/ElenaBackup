package com.my.elenabackup;

import android.app.Activity;
import android.os.Build;

import static com.my.elenabackup.Constants.READ_STORAGE_PERMISSION_REQUEST_CODE;
import static com.my.elenabackup.Constants.REQUEST_WRITE_PERMISSION;

public class PermissionManager {

    static void requestPermissionForWriteExternalStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        }
    }

    public static void requestPermissionForReadExternalStorage(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(new String[]{android.Manifest.permission.READ_EXTERNAL_STORAGE}, READ_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }
}
