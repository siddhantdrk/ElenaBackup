package com.my.elenabackup;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class CreateDatabaseBackup {
    public static boolean makeAppDirectory(Context context) {
        File elena = new File(context.getExternalFilesDir(null) + "/ELENA/");
        try {
            if (!elena.exists()) {
                if (!elena.mkdir()) {
                    LogUtils.logE(CreateDatabaseBackup.class.getSimpleName(), "App Directory not created");
                    return false;
                } else {
                    LogUtils.logV(CreateDatabaseBackup.class.getSimpleName(), "App Directory successfully created");
                    return true;
                }
            } else {
                LogUtils.logV(CreateDatabaseBackup.class.getSimpleName(), "App Directory exists");
                return true;
            }
        } catch (NullPointerException e) {
            LogUtils.logE("makeAppDirectory", e.getMessage());
            return false;
        }
    }

    public static boolean createDatabaseFileBackup(Context context, String DATABASE_NAME) {
        File backup = new File(context.getExternalFilesDir(null) + "/ELENA/");
        try {
            if (!backup.exists()) {
                if (!backup.mkdir()) {
                    LogUtils.logE(CreateDatabaseBackup.class.getSimpleName(), "Backup Directory not created");
                } else {
                    LogUtils.logV(CreateDatabaseBackup.class.getSimpleName(), "Backup Directory created successfully");
                }
            } else {
                LogUtils.logV(CreateDatabaseBackup.class.getSimpleName(), "Backup Directory already exists");
            }
        } catch (NullPointerException e) {
            LogUtils.logE("createDatabaseFileBackup", e.getMessage());
        }

        try {
            File sd = context.getExternalFilesDir(null);
            if (sd.canWrite()) {
                String backupDBPath = String.format("%s.db", DATABASE_NAME);
                File currentDB = context.getDatabasePath(DATABASE_NAME);
                File backupDB = new File(backup, backupDBPath);
                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                LogUtils.logV(CreateDatabaseBackup.class.getSimpleName(), "Database File Backup Successful");
                return true;
            } else {
                LogUtils.logE(CreateDatabaseBackup.class.getSimpleName(), "Read and Write permissions not given");
                return false;
            }
        } catch (IOException | NullPointerException e) {
            LogUtils.logE("createDatabaseFileBackup", e.getMessage());
            return false;
        }
    }

    public static MyDbHelper addDataToDb(Context context) {
        MyDbHelper myDbHelper = new MyDbHelper(context);
        List<String> paths = new ArrayList<>();
        paths.add("1");
        paths.add("2");
        paths.add("3");
        myDbHelper.addPicturePath(paths);
        return myDbHelper;
    }
}
