package com.my.elenabackup;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import static com.my.elenabackup.DriveUtils.getPath;

public class FilePickerUtils {

    private static final String TAG = "FileUtils";

    public static boolean openFilePicker(int REQUEST_CODE_OPEN_DOCUMENT, Activity context, DriveServiceHelper mDriveServiceHelper) {
        if (mDriveServiceHelper != null) {
            Log.d(TAG, "Opening file picker.");
            Intent pickerIntent = mDriveServiceHelper.createFilePickerIntent();
            context.startActivityForResult(pickerIntent, REQUEST_CODE_OPEN_DOCUMENT);
            return true;
        } else {
            LogUtils.logV("mDriveServiceHelper", "null value");
            return false;
        }
    }

    public static MyDbHelper openFileFromFilePicker(Uri selectedImageUri, Context context) {
        try {
            Log.d("TAG", "Opening " + selectedImageUri.getPath());
            String picturePath = getPath(context.getApplicationContext(), selectedImageUri);
            boolean unZipReturnValue = UnZipAndBackup.unpackZip(picturePath,
                    picturePath
                    , context.getExternalFilesDir(null).getAbsolutePath());
            if (unZipReturnValue) {
                LogUtils.logV(MainActivity.class.getSimpleName(), "File UnZipped Successfully");
                CopyDatabase copyDatabase = new CopyDatabase(context);
                boolean returnValue = copyDatabase.copyBackupDataBase(context.getExternalFilesDir(null) + "/ELENA/ELENA", context.getDatabasePath("ELENA"));
                if (returnValue) {
                    LogUtils.logD(MainActivity.class.getSimpleName(), "Database Copied Successfully");
                } else {
                    LogUtils.logD(MainActivity.class.getSimpleName(), "Backup file doesn't exist");
                }
                return new MyDbHelper(context);
            }
            return null;
        } catch (NullPointerException e) {
            LogUtils.logE("openFileFromFilePicker", e.getMessage());
            return null;
        }
    }
}
