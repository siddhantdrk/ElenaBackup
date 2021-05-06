package com.my.elenabackup;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static android.content.ContentValues.TAG;
import static com.my.elenabackup.DriveUtils.getPath;

public class UnZipAndBackup {

    public static boolean unpackZip(String zipFile, String directoryPath, String location) {
        try {
            File f = new File(location);
            if (!f.isDirectory()) {
                f.mkdirs();
            }
            try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipFile))) {
                ZipEntry ze;
                while ((ze = zin.getNextEntry()) != null) {
                    String path = location + File.separator + ze.getName();

                    if (ze.isDirectory()) {
                        File unzipFile = new File(path);
                        if (!unzipFile.isDirectory()) {
                            unzipFile.mkdirs();
                        }
                    } else {
                        File unzipFilePath = new File(directoryPath);
                        if (!unzipFilePath.isDirectory()) {
                            unzipFilePath.mkdirs();
                        }
                        File imagePath = new File(location + "/ELENA/ELENA_PICTURE");
                        if (!imagePath.isDirectory()) {
                            imagePath.mkdirs();
                        }

                        try (FileOutputStream fout = new FileOutputStream(path, false)) {
                            for (int c = zin.read(); c != -1; c = zin.read()) {
                                fout.write(c);
                            }
                            zin.closeEntry();
                        } catch (NullPointerException e) {
                            Log.e(TAG, "Unzip exception", e);
                        }
                    }
                }
            } catch (NullPointerException e) {
                Log.e(TAG, "Unzip exception", e);
            } finally {
                return true;
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.e(TAG, "Unzip exception", e);
            return false;
        }
    }

    public static MyDbHelper UnZipAndCopyBackup(Intent data, Context context) {
        try {
            Uri selectedImageUri = data.getData();
            String picturePath = getPath(context.getApplicationContext(), selectedImageUri);
            boolean unZipReturnValue = UnZipAndBackup.unpackZip(picturePath,
                    picturePath
                    , context.getExternalFilesDir(null).getAbsolutePath());
            if (unZipReturnValue) {
                LogUtils.logV(MainActivity.class.getSimpleName(), "File UnZipped Successfully");
                CopyDatabase copyDatabase = new CopyDatabase(context);
                boolean returnValue = copyDatabase.copyBackupDataBase(context.getExternalFilesDir(null) + "/ELENA/ELENA", context.getDatabasePath("ELENA"));
                if (returnValue) {
                    LogUtils.logV(MainActivity.class.getSimpleName(), "Database Copied Successfully");
                } else {
                    LogUtils.logE(MainActivity.class.getSimpleName(), "Backup file doesn't exist");
                }
                return new MyDbHelper(context);
            } else {
                LogUtils.logE(MainActivity.class.getSimpleName(), "File UnZipped Unsuccessfully");
            }
            return null;
        } catch (NullPointerException e) {
            Log.e("UnZipAndCopyBackup", e.getMessage());
            return null;
        }
    }
}