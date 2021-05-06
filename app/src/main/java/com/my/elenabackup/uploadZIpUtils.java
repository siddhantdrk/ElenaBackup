package com.my.elenabackup;

import android.app.Activity;
import android.content.Context;

import com.google.android.gms.tasks.Task;

import java.io.File;

public class uploadZIpUtils {
    public static void uploadZip(Context context, Activity activity, DriveServiceHelper mDriveServiceHelper) {
        try{
            Task<String> a = mDriveServiceHelper.createFile(context.getExternalFilesDir(null) + "/ELENA.zip");
            a.addOnSuccessListener(activity, s -> {
                LogUtils.logV(MainActivity.class.getSimpleName(), "File Upload to google drive successfully with id: " + s);
                File file = new File(context.getExternalFilesDir(null) + "/ELENA.zip");
                try {
                    boolean isDeleted = file.delete();
                    if (isDeleted) {
                        LogUtils.logV(MainActivity.class.getSimpleName(), "File deleted successfully from Internal Storage");

                    } else {
                        LogUtils.logE(MainActivity.class.getSimpleName(), "File not deleted");
                    }
                } catch (NullPointerException e) {
                    LogUtils.logE(MainActivity.class.getSimpleName(), e.getMessage());
                }
            });

            a.addOnFailureListener(activity, f -> LogUtils.logV(MainActivity.class.getSimpleName(), "File Upload failed: " + f));
        }
        catch (NullPointerException e){
            LogUtils.logE("uploadZip",e.getMessage());
        }
    }
}
